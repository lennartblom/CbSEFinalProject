/*
 * Copyright 2016 Blom, Brammer, Zeyn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controller;

import entities.Lending;
import entities.LendingObject;
import entities.Material;
import entities.Person;
import model.MaterialModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import model.LendingModel;
import model.LendingObjectModel;

/**
 * LendingSystem zur Verwaltung der Ausleihen, Materialien und Personen.
 *
 * @author Miriam
 */
@Dependent
public class LendingSystem implements Serializable {

    /**
     * LendingController zur Verwaltung der Ausleihen.
     */
    @Inject
    private LendingController lendingControl;
    /**
     * Veknüpfte Liste mit Material und AusleihObjekt.
     */
    private Map<Material, LendingObject> lendingArrangements;
    /**
     * MaterialModel für Eintragungen in Datenbank.
     */
    @Inject
    private MaterialModel material;
    /**
     * LendingModel für Eintragungen in Datenbank.
     */
    @Inject
    private LendingModel lending;
    /**
     * LendingObjectModel für Eintragungen in Datenbank.
     */
    @Inject
    private LendingObjectModel lendingObject;
    /**
     * PersonController zur Verwaltung der Personen.
     */
    @Inject
    private PersonController personControl;

    /**
     * Erstellt ein neues LendingSystem ohne Eigenschaften.
     */
    public LendingSystem() {

        System.out.println("### Konstruktor LendingSystem() ###");
        
        this.lendingControl = new LendingController();
        this.lending = new LendingModel();
        this.lendingArrangements = new HashMap<>();
        this.personControl = new PersonController();

        this.material = new MaterialModel();

    }

    /**
     * Fuegt eine Ausleihe hinzu.
     *
     * @param personName Name der Person
     * @param errors ErrorListe
     * @return ErrorCode
     */
    public int addLending(String personName, List<String> errors) {

        if(this.lendingArrangements.size() == 0){
            errors.add("Keine auszuleihenden Objekte ausgewählt. ");
            return 9;
        }
        boolean lendingProcessSuccessful = true;
        int errorCode = 0;

        Person tmpPerson = this.personControl.addPerson(personName);

        if (tmpPerson == null) {
            return 3;
        }

        Lending newLending = this.lendingControl.createNewLending();
        newLending.setPerson(tmpPerson);

        for (Map.Entry<Material, LendingObject> entry : this.lendingArrangements.entrySet()) {

            LendingObject tmp = entry.getValue();

            if (doesMaterialExist(entry.getKey().getId())
                    && isObjectInStock(tmp)) {

                if (tmp.getLendingQuantity() > 0) {
                    LendingObject lendingObjectToAdd = this.lendingControl.createLendingObject(tmp.getType(), tmp.getLendingQuantity(),
                            tmp.getStartLendDate(), tmp.getEndLendDate());
                    if (lendingObjectToAdd != null) {
                        newLending.addLendingObject(lendingObjectToAdd);
                    } else {
                        errors.add("Das Material " + tmp.getType().getName() + " konnte nicht hinzugefügt werden.");
                        lendingProcessSuccessful = false;
                    }
                }

            } else {
                lendingProcessSuccessful = false;
                if (tmp.getLendingQuantity() == 0) {
                    errors.add("Das Material " + tmp.getType().getName() + " konnte nicht hinzugefügt werden. "
                            + "In dem Zeitraum gibt es keine verfügbaren Objekte");
                } else if (tmp.getLendingQuantity() > 0) {
                    errors.add("Das Material " + tmp.getType().getName() + " konnte nicht hinzugefügt werden. "
                            + "In dem Zeitraum gibt es nicht genügend verfügbare Objekte. Die Anzahl wurde angepasst.");
                }

            }

        }

        if (!lendingProcessSuccessful) {
            errors.add("Der Ausleihvorgang war nicht erfolgreich.");
            newLending.setPerson(null);
            lending.deleteLending(newLending.getId());
            return 7;
        } else {
            errors.add("Der Ausleihvorgang war erfolgreich.");
            this.lending.saveChanges(newLending);
            this.lendingArrangements.clear();
            return 0;
        }
    }

    /**
     * Löscht eine Ausleihe.
     *
     * @param id Id der Ausleihe
     */
    public void deleteLending(long id) {
        this.lendingControl.deleteLending(id);
    }

    /**
     * Fuegt ein Material hinzu.
     *
     * @param name Name
     * @param description Beschreibung
     * @param quantity Menge
     */
    public void addMaterial(String name, String description, int quantity) {

        if (!doesMaterialExist(name)) {
            Material m = this.material.createMaterial(name, description, quantity);
        } else {
            Material tmpMaterial;
            for(Material m:this.material.getMaterials()){
                if(makeStringConform(m.getName()).equals(makeStringConform(name))){
                    m = this.material.updateMaterial(m.getId(), description, quantity);
                }
            }
        }

        
    }

    /**
     * Löscht ein Material.
     *
     * @param material zu loeschendes Material
     * @param errors ErrorListe
     */
    public void deleteMaterial(Material material, List<String> errors) {

        if (this.lendingObject.getLendingObjectsWithMaterialId(material.getId()).size() > 0) {
            errors.add("Material kann nicht gelöscht werden, da es innerhalb einer Ausleihe verwendet wird.");
        } else {
            this.material.deleteMaterial(material.getId());

        }

    }

    /**
     * Gibt das Material über den Namen zurueck.
     *
     * @param name Name des Materials
     * @return Material
     */
    public Material getMaterial(String name) {
        String nameConform = makeStringConform(name);
        for(Material m:this.material.getMaterials()){
                if(makeStringConform(m.getName()).equals(makeStringConform(name))){
                    return m;
                }
        }
        
        return null;
    }

    private boolean isObjectInStock(LendingObject lendingObject) {

        int quantity = lendingObject.getLendingQuantity();
        long materialId = lendingObject.getType().getId();
        Material tmp = this.material.findMaterial(materialId);

        if (tmp != null) {
            int currentLendingQuantity = this.lendingControl.lentObjects(materialId, lendingObject.getStartLendDate(), lendingObject.getEndLendDate());

            int inStock = tmp.getQuantity() - currentLendingQuantity;
            boolean result = quantity <= inStock;

            if (result) {
                return true;
            } else {
                if (inStock <= 0) {
                    lendingObject.setLendingQuantity(0);
                } else {
                    lendingObject.setLendingQuantity(inStock);
                }
                return false;
            }
        } else {

            //System.out.println("Material nicht gefunden.");

            return false;
        }

    }

    /**
     * Prueft ob ein Material, über den Namen, exisitiert.
     *
     * @param materialname Name des Materials
     * @return true, wenn es existiert. False wenn nicht
     */
    public boolean doesMaterialExist(String materialname) {
        
        for(Material m:this.material.getMaterials()){
            if(makeStringConform(m.getName()).equals(makeStringConform(materialname))){
                return true;
            }
        }
        
        return false;

    }

    /**
     * Prueft ob ein Material exisitert, ueber die Id.
     *
     * @param materialId MaterialId
     * @return true, wenn Material exisitert. False wenn nicht.
     */
    public boolean doesMaterialExist(long materialId) {

        return this.material.findMaterial(materialId) != null;
    }

    /**
     * Fuegt eine Person hinzu.
     *
     * @param name Name der Person
     */
    public void addPerson(String name) {
        this.personControl.addPerson(name);
    }

    /**
     * Konvertiert den String zu einem konformen String.
     *
     * @param string zu konvertierenden String
     * @return konformer String
     */
    public static String makeStringConform(String string) {

        String stringConform = string.replace("ä", "ae");
        stringConform = stringConform.replace("ü", "ue");
        stringConform = stringConform.replace("ö", "oe");
        stringConform = stringConform.replace("ß", "ss");
        stringConform = stringConform.toLowerCase();

        return stringConform;
    }

    /**
     * Fuegt Beispieldaten im System hinzu.
     */
    public void insertDummyData() {
        String name = "";
        String description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";
        int quantity = 0;
        Random rn = new Random();

        for (int i = 0; i < 4; i++) {

            switch (i) {
                case 0:
                    name = "Stativ";
                    break;
                case 1:
                    name = "Canon 50mm f/1.4";
                    break;
                case 2:
                    name = "Canon EOS 1D";
                    break;
                case 3:
                    name = "Canon 70-200mm f/2.8";
                    break;

            }

            quantity = rn.nextInt(10 - 1 + 1) + 1;

            this.addMaterial(name, description, quantity);

        }

    }

    /**
     * Speichert aktuelles Material.
     *
     * @param material Material
     */
    public void saveCurrentMaterial(Material material) {

        this.material.saveChanges(material);

    }

    /**
     * Speichert Daten für aktuelle Ausleihe.
     *
     * @param material Material
     * @param quantity Menge
     * @return true, wenns gespeichert werden konnte, false wenn nicht
     */
    public boolean saveForCurrentLendings(Material material, int quantity) {

        for (Map.Entry<Material, LendingObject> entry : this.lendingArrangements.entrySet()) {
            if (entry.getKey().getId() == material.getId()) {
                entry.setValue(new LendingObject());
                entry.getValue().setType(entry.getKey());
                entry.getValue().setLendingQuantity(quantity);
                this.outputLendingArrangements();

                return true;
            }
        }

        this.lendingArrangements.put(material, new LendingObject(material, quantity, null, null));

        this.outputLendingArrangements();

        return true;
    }

    /**
     * Gibt Lending-Daten aus.
     */
    public void outputLendingArrangements() {

        for (Map.Entry<Material, LendingObject> entry : this.lendingArrangements.entrySet()) {
            System.out.println(entry.getKey().getName() + " :: " + entry.getValue().getLendingQuantity());
        }
    }

    

    /**
     * Gibt aktuelle Ausleihen zurueck.
     *
     * @return Liste der Ausleihen
     */
    public List<LendingObject> getCurrentLendingArrangements() {

        List<LendingObject> result = new ArrayList<>();

        for (Map.Entry<Material, LendingObject> entry : this.lendingArrangements.entrySet()) {
            LendingObject tmp = entry.getValue();
            result.add(tmp);
        }

        return result;
    }

    /**
     * Setzt die Lending-Liste zurueck.
     */
    public void clearLendingArrangements() {
        this.lendingArrangements.clear();
    }

    /**
     * Löscht Objekt aus der Lending-Liste.
     *
     * @param materialId Material Id
     * @return true, wenn Objekt gelöscht wurde, false wenn nicht.
     */
    private boolean removeObjectFromLendingArranagements(long materialId) {

        for (Map.Entry<Material, LendingObject> entry : this.lendingArrangements.entrySet()) {
            Material tmp = entry.getKey();

            if (tmp.getId() == materialId) {

                this.lendingArrangements.remove(tmp);
                return true;
            }

        }


        return false;

    }

}
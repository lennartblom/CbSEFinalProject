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
package viewmodel;

import api.Navigation;
import controller.LendingSystem;
import entities.LendingObject;
import entities.Material;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * LendingSystemViewModel repräsentiert das ViewModel der LendingSystem-Klasse.
 *
 * @author Fabian
 */
@Named("lendingSystem")
@SessionScoped
public class LendingSystemViewModel implements Serializable {

    /**
     * LendingSystem zur Verwaltung.
     */
    @Inject
    private LendingSystem lendingSystem;

    /**
     * Aktuelles Material.
     */
    private Material currentMaterial;

    private boolean hasErrors;
    private List<String> errors;

    /**
     * Erstellt ein LendingSystemViewModel ohne Eigenschaften.
     */
    public LendingSystemViewModel() {
        this.lendingSystem = new LendingSystem();
        this.hasErrors = false;
        this.errors = new ArrayList<String>();
    }

    /**
     * Fuegt Beispieldaten im System ein.
     */
    public void insertDummyData() {
        this.lendingSystem.insertDummyData();
    }

    /**
     * Fuegt eine neue Ausleihe hinzu.
     *
     * @param person ausleihende Person
     * @return Weiterleitung zur Indexseite
     */
    public String addLending(PersonViewModel person) {
        this.clearErrors();
        int errorCode = this.lendingSystem.addLending(person.getName(), this.errors);

        if (errorCode != 0) {
            this.hasErrors = true;
        }

        //System.out.println("ErrorCode = " + errorCode);
        switch (errorCode) {

            case 0:
                //System.out.println("0  -   Lending wurde erfolgreich hinzugefügt.");

                person.setName("");
                this.clearErrors();
                this.setHasErrors(false);
                return Navigation.goToSuccessPage();
            case 1:
                //System.out.println("1   -   Material nicht verfügbar oder Material existiert noch nicht.");
                break;
            case 2:
                //System.out.println("2   -   Lending wurde hinzugefügt, aber nicht aus den LendingArragement gelöscht");
                break;
            case 3:
                //System.out.println("3   -   Person wurde nicht hinzugefügt. Vorgang wurde abgebrochen");

                break;
            case 4:
                //System.out.println("4   -   Material wurde nicht gefunden. Vorgang wurde abgebrochen");
                break;
            case 7:
                //System.out.println("4   -   Es Probleme bei der Ausleihe einer oder mehrer Materialien. "
                //        + "Die Anzahl wurde ggf. abgeändert um den vorgang erneut durchzuführen.");
                break;
            case -1:
                //System.out.println("-1   -   Lending konnte nicht hinzugefügt werden.");

                return Navigation.goToLendingConfirmation();
            default:
                //System.out.println("Unbekannter Fehlercode.");
                break;
        }

        return Navigation.goToLendingConfirmation();
    }

    /**
     * Fuegt ein neues Material hinzu.
     *
     * @param material neues Material
     */
    public void addMaterial(MaterialViewModel material) {

        if (lendingSystem != null) {
            lendingSystem.addMaterial(material.getName(), material.getDescription(), material.getQuantity());
        } else {
            System.err.println("LendingSystem wurde noch nicht gesetzt...");
        }

        material.setName("");
        material.setDescription("");
        material.setQuantity(0);
    }

    /**
     * Enfernt ein Material.
     *
     * @param material zu entfernendes Material
     */
    public void removeMaterial(Material material) {

        this.clearErrors();
        this.lendingSystem.deleteMaterial(material, this.errors);

    }

    /**
     * Bearbeitet ein Material.
     *
     * @param material zu bearbeitendes Material
     * @return Weiterleitung zur Bearbeitungsseite
     */
    public String editMaterial(Material material) {

        this.currentMaterial = material;

        return Navigation.goToEditMaterial();
    }

    /**
     * Speichert aktuelles Material
     *
     * @return Weiterleitung zur Materialhinzufügen Seite
     */
    public String saveCurrentMaterial() {

        this.lendingSystem.saveCurrentMaterial(currentMaterial);

        return Navigation.goToAddMaterial();
    }

    /**
     * Gibt aktuelles Material zurueck.
     *
     * @return aktuelles Material
     */
    public Material getCurrentMaterial() {
        return currentMaterial;
    }

    /**
     * Speichert aktuelle Ausleihen
     *
     * @param material Material
     * @param quantity Menge
     */
    public void saveForCurrentLendings(Material material, int quantity) {
        if (quantity > 0) {
            if (material != null) {
                //System.out.println("Testausgabe");
            }
            this.lendingSystem.saveForCurrentLendings(material, quantity);
        }
    }

    /**
     * Gibt eine Liste mit den Ausleihen zurueck.
     *
     * @return Liste der Ausleihen
     */
    public List<LendingObject> getCurrentLendingArrangements() {
        return this.lendingSystem.getCurrentLendingArrangements();
    }

    /**
     * Beendet die Ausleihe.
     *
     * @return Weiterleitung zur Indexseite
     */
    public String checkOutWithLendings() {
        this.lendingSystem.clearLendingArrangements();
        this.clearErrors();
        this.setHasErrors(false);
        return Navigation.goToIndex();
    }

    /**
     * Setzt die Errors.
     *
     * @param hasErrors Error
     */
    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    /**
     * Gibt zurueck, ob Errors bestehen.
     *
     * @return true, wenn es Errors gibt, false wenn nicht.
     */
    public boolean isHasErrors() {
        return hasErrors;
    }

    /**
     * Setzt Errors zurueck.
     */
    public void clearErrors() {
        this.errors.clear();
    }

    /**
     * Gibt die Liste der Errors zurueck.
     *
     * @return Liste der Errors
     */
    public List<String> getErrors() {
        return this.errors;
    }

}
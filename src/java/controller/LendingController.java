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
import model.LendingModel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import model.LendingObjectModel;

/**
 * Die Klasse LendingController verwaltet die momentanen Ausleihen.
 *
 * @author Miriam
 */
@Dependent
public class LendingController implements Serializable {

    /**
     * LendingModel für Eintragungen in der Datenbank.
     */
    @Inject
    private LendingModel lending;
    /**
     * LendingObjektModel für Eintragungen in der Datenbank.
     */
    @Inject
    private LendingObjectModel lendingObject;


    /**
     * Erstellt einen neuen LendingController.
     */
    public LendingController() {
        this.lending = new LendingModel();
        this.lendingObject = new LendingObjectModel();
    }


    /**
     * Erzeugt eine neue Ausleihe.
     *
     * @return Ausleihe
     */
    public Lending createNewLending() {

        Lending tmp = this.lending.createEmptyLending();

        return tmp;
    }

    /**
     * Erzeugt ein neues Ausleihobjekt.
     *
     * @param material Material
     * @param quantity Menge
     * @param startLendDate Startdatum
     * @param endLendDate Enddatum
     * @return Ausleihobjekt
     */
    public LendingObject createLendingObject(Material material, int quantity,
            Date startLendDate, Date endLendDate) {

        return this.lendingObject.createNewLendingObject(material, quantity, startLendDate, endLendDate);
    }

    /**
     * Fuegt eine neue Ausleihe zur Liste hinzu.
     *
     * @param person
     * @param material Auszuleihendes Material
     * @param quantity Anzahl der auszuleihenden Gegenstaende
     * @param begin Startdatum
     * @param end Enddatum
     * @return true, wenn Ausleihe hinzugefuegt wurde, false wenn nicht
     */
    public boolean addLending(Person person, Material material, int quantity, Date begin, Date end) {

        if (this.lending != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Löscht eine Ausleihe anhand der Id.
     *
     * @param id Id der zu loeschenden Ausleihe
     */
    public void deleteLending(long id) {
        
        this.lending.deleteLending(id);
    }


    /**
     * Gibt die Liste der aktuellen Ausleihen zurueck.
     *
     * @return Liste der aktuellen Ausleihen
     */
    public List<Lending> getCurrentLendings() {
        return this.lending.getLendings();
    }


    /**
     * Gibt die Anzahl der ausgeliehnenen Gegenstaende des Materials in dem
     * uebergebenen Zeitraum zurueck.
     *
     * @param materialId Material ID
     * @param begin Startdatum
     * @param end Enddatum
     * @return Anzahl der ausgeliehenen Gegenstaende
     */
    public int lentObjects(long materialId, Date begin, Date end) {
        int quantity = 0;


        for (LendingObject lo : lendingObject.getLendingObjects()) {
            if (lo.getType().getId() == materialId) {
                
                if (lo.getStartLendDate().after(begin) && lo.getStartLendDate().before(end)
                        || lo.getEndLendDate().after(begin) && lo.getEndLendDate().before(end)
                        || lo.getStartLendDate().before(begin) && lo.getEndLendDate().after(end)
                        || lo.getStartLendDate().equals(begin) && lo.getEndLendDate().equals(end)) {

                    quantity += lo.getLendingQuantity();
                }
            }

        }

        return quantity;
    }

}

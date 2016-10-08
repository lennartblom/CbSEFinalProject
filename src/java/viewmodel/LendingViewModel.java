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

import controller.LendingController;
import entities.Lending;
import entities.LendingObject;
import entities.Person;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.LendingModel;

/**
 * LendingViewModel repräsentiert das ViewModel der Lending-Klasse.
 *
 * @author Fabian
 */
@Named("lending")
@RequestScoped
public class LendingViewModel implements Serializable {

    /**
     * Id des LendingViewModels.
     */
    private long id;

    /**
     * Person des LendingViewModels.
     */
    private Person person;

    /**
     * Listte von ausgeliehenen Medien.
     */
    private List<LendingObject> lendings;

    /**
     * Starttag der Ausleihe.
     */
    private String startLendDateString;

    /**
     * Endtag der Aussleihe.
     */
    private String endLendDateString;

    /**
     * LendingController zur Verwaltung.
     */
    @Inject
    private LendingController lendingController;

    /**
     * LendingModel für die Eintragungen in der Datenbank.
     */
    @Inject
    private LendingModel lending;

    /**
     * Erstellt ein LendingViewModel ohne Eigenschaften.
     */
    public LendingViewModel() {
        this.person = null;
        this.lending = new LendingModel();
        this.lendingController = new LendingController();
    }

    /**
     * Gibt die aktuellen Ausleihen in einer Liste zurueck.
     *
     * @return aktuelle Ausleihen
     */
    public List<Lending> getCurrentLendings() {
        return this.lendingController.getCurrentLendings();
    }

    /**
     * Gibt die Menge der LendingObjects anhand der Id zurueck.
     *
     * @param lendingId Id der Ausleihe
     * @return Menge der LendingObjects
     */
    public int getLendingObjectsQuantityWithLendingId(long lendingId) {
        return this.lending.getLendingObjectsQuantityWithLendingId(lendingId);
    }


    /**
     * Gibt die Ausleihen in einer Liste zurueck.
     *
     * @return Liste der Ausleihen
     */
    public List<Lending> getLendings() {
        return this.lending.getLendings();
    }

    

    /**
     * Gibt die Id des LendingViewModels zurueck.
     *
     * @return Id
     */
    public long getId() {
        return id;
    }

    /**
     * Gibt die Person des LendingViewModels zurueck.
     *
     * @return Person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Setzt die Person des LendingViewModels neu.
     *
     * @param person neue Person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Gibt den LendingController zurueck.
     *
     * @return LendingController
     */
    public LendingController getLendingController() {
        return lendingController;
    }

    /**
     * Setzt den LendingController neu.
     *
     * @param lending LendingController
     */
    public void setLending(LendingController lending) {
        this.lendingController = lending;
    }

    /**
     * Setzt das Startdatum der Ausleihe.
     *
     * @param startLendDateString Startdatum
     */
    public void setStartLendDateString(String startLendDateString) {
        this.startLendDateString = startLendDateString;
    }

    /**
     * Setzt das Enddatum der Ausleihe.
     *
     * @param endLendDateString Enddatum
     */
    public void setEndLendDateString(String endLendDateString) {
        this.endLendDateString = endLendDateString;
    }

    /**
     * Gibt das Startdatum der Ausleihe zurueck.
     *
     * @return Startdatum
     */
    public String getStartLendDateString() {
        return startLendDateString;
    }

    /**
     * Gibt das früheste Datum der Ausleihe zurueck.
     *
     * @param lendingId Id der Ausleihe
     * @return frühestes Datum
     */
    public java.sql.Date getMinDateFromLendingId(long lendingId) {
        return this.lending.getMinDateFromLendingId(lendingId);
    }

    /**
     * Gibt das späteste Datum der Ausleihe zurueck.
     *
     * @param lendingId Id der Ausleihe
     * @return spätestes Datum
     */
    public java.sql.Date getMaxDateFromLendingId(long lendingId) {
        return this.lending.getMaxDateFromLendingId(lendingId);
    }

    /**
     * Gibt das Enddatum der Ausleihe zurueck.
     *
     * @return Enddatum
     */
    public String getEndLendDateString() {
        return endLendDateString;
    }

}

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
package model;

import database.Persistierer;
import entities.Lending;
import entities.Material;
import entities.Person;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Die Klasse LendingModel stellt abstrahierte Funktionen zur Erstellung und
 * Loeschen von Ausleihen.
 *
 * @author Fabian
 */
@Dependent
public class LendingModel implements Serializable {

    /**
     * Persistierer für den Zugriff auf die Datenbank.
     */
    @Inject
    private Persistierer db;
    /**
     * PersonModel für Eintragungen in der Datenbank.
     */
    @Inject
    private PersonModel person;
    /**
     * MaterialModel für Eintragungen in der Datenbank.
     */
    @Inject
    private MaterialModel material;

    /**
     * Erstellt ein neues LendingModel ohne Eigenschaften.
     */
    public LendingModel() {
        this.person = new PersonModel();
        this.material = new MaterialModel();
        this.db = new Persistierer();
    }

    /**
     * Erzeugt eine neue Ausleihe mit Gegenstandtyp, Anzahl der auszuleihenden
     * Gegenstaende, die ausleihende Person und Start- und Enddatum. Diese wird
     * persistiert und zurueckgeben
     *
     * @param lendingQuantity Anzahl der Gegenstaende
     * @param person Ausleihende Person
     * @param material Material
     * @param startLendDate Startdatum
     * @param endLendDate Enddatum
     * @return Persistierte Ausleihe
     */
    public boolean createLending(Person person, int lendingQuantity,
            Material material, Date startLendDate, Date endLendDate) {

       
        if (material != null && person != null) {
           
            Lending tmp = new Lending(material, lendingQuantity, person,
                    startLendDate, endLendDate);
            db.persist(tmp);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Erstellt eine neue leere Ausleihe.
     *
     * @return Ausleihe
     */
    public Lending createEmptyLending() {
        Lending tmp = new Lending();

        db.persist(tmp);

        return tmp;

    }

    /**
     * Speichert Änderungen in der Ausleihe.
     *
     * @param lending Ausleihe
     */
    public void saveChanges(Lending lending) {
        this.db.merge(lending);
    }

    /**
     * Gibt alle Ausleihen zurück.
     *
     * @return Liste der Ausleihen
     */
    public List<Lending> getLendings() {
        return db.findAllLendings();
    }

    /**
     * Löscht eine Ausleihe anhand der Id.
     *
     * @param id Id der zu loeschenden Ausleihe
     */
    public void deleteLending(long id) {
        db.removeLending(id);
    }

    /**
     * Gibt Menge der Ausleihobjekte über die Ausleihid zurück.
     *
     * @param lendingId Ausleihid
     * @return Menge der Ausleihobjekte
     */
    public int getLendingObjectsQuantityWithLendingId(long lendingId) {
        return db.getLendingObjectsQuantityWithLendingId(lendingId);
    }


    /**
     * Gibt das früheste Datum der Ausleihe zurück.
     *
     * @param lendingId Ausleihe
     * @return frühestes Datum
     */
    public java.sql.Date getMinDateFromLendingId(long lendingId) {
        return db.getMinDateFromLendingId(lendingId);
    }

    /**
     * Gibt das spätestes Datum der Ausleihe zurück.
     *
     * @param lendingId Ausleihe
     * @return spätestes Datum
     */
    public java.sql.Date getMaxDateFromLendingId(long lendingId) {
        return db.getMaxDateFromLendingId(lendingId);
    }

}

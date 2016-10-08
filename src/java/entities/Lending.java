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
package entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Die Klasse Lending stellt eine Ausleihe mit dem Gegenstandstyp, Anzahl der
 * Gegenstaende, der ausleihenden Person und Start- und Enddatum dar.
 * Gleichzeitig dient sie als Datenbank-Entity.
 *
 * @author Miriam
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Lending.getLendingObjectsQuantity",
            query = "SELECT SUM(lo.lendingQuantity) AS quantity "
            + "FROM LendingObject lo, Lending l "
            + "WHERE l.id = :lendingId "
            + "AND lo MEMBER OF l.lendings"),

    @NamedQuery(name = "Lending.getLendingObjectsFromLending",
            query = "SELECT lo FROM LendingObject lo, Lending l "
            + "WHERE l.id = :lendingId "
            + "AND lo MEMBER OF l.lendings"),

    @NamedQuery(name = "Lending.getLendingsFromFromPersonId",
            query = "SELECT l FROM Lending l, Person p "
            + "WHERE l.person = p "
            + "AND p.id = :personId"),

    @NamedQuery(name = "Lending.getMinDate",
            query = "SELECT lo.startLendDate "
            + "FROM LendingObject lo, Lending l, Person p "
            + "WHERE l.person = p AND l.id = :lendingId "
            + "ORDER BY lo.startLendDate ASC"),

    @NamedQuery(name = "Lending.getMaxDate",
            query = "SELECT lo.endLendDate "
            + "FROM LendingObject lo, Lending l, Person p "
            + "WHERE l.person = p AND l.id = :lendingId "
            + "ORDER BY lo.endLendDate DESC"),})

public class Lending implements Serializable {

    /**
     * Id der Ausleihe. Wird automatisch erzeugt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Typ der auszuleihenden Gegenstaende.
     */
    /**
     * Ausleihende Person.
     */
    private Person person;

    @OneToMany(orphanRemoval = true)
    private List<LendingObject> lendings;

    /**
     * Erstellt ein Objekt vom Typ Lending ohne Eigenschaften.
     */
    public Lending() {
        this.lendings = new ArrayList<>();
    }

    /**
     * Erstellt ein Objekt vom Typ Lending mit Gegenstandtyp, Anzahl der
     * ausgeliehenen Gegenstaende, die ausleihende Person, Start- und Enddatum.
     *
     * @param type Gegenstandtyp
     * @param lendingQuantity Anzahl der ausgeliehenen Gegenstaende
     * @param person Ausleihende Person
     * @param startLendDate Startdatum
     * @param endLendDate Enddatum
     */
    public Lending(Material type, int lendingQuantity, Person person,
            Date startLendDate, Date endLendDate) {

        this.person = person;
    }

    /**
     * Gibt die Id der Ausleihe zurueck.
     *
     * @return Id der Ausleihe
     */
    public long getId() {
        return id;
    }

    /**
     * Gibt die ausleihende Person zurueck.
     *
     * @return Ausleihende Person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Setzt die ausleihende Person neu.
     *
     * @param person Ausleihende Person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Fuegt ein neues LendingObject hinzu.
     *
     * @param lendingObject LendingObject
     */
    public void addLendingObject(LendingObject lendingObject) {
        this.lendings.add(lendingObject);
    }

    /**
     * Entfernt ein LendingObject.
     *
     * @param lendingObject LendingObject
     */
    public void removeLendingOBject(LendingObject lendingObject) {
        this.lendings.remove(lendingObject);
    }

    /**
     * Gibt alle Ausleihobjekte in einer Liste zurueck.
     *
     * @return alle Ausleihobjekte
     */
    public List<LendingObject> getLendings() {
        return lendings;
    }
}

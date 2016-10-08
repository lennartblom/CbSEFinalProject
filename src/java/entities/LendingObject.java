
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
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import validation.annotation.LendingCheck;

/**
 * Die Klasse Lending stellt eine Ausleihe mit dem Gegenstandstyp, Anzahl der
 * Gegenstaende, der ausleihenden Person und Start- und Enddatum dar.
 * Gleichzeitig dient sie als Datenbank-Entity.
 *
 * @author Miriam
 */

@NamedQueries({
    @NamedQuery(name = "LendingObject.getLenderName",
            query = "SELECT p.name FROM Person p, Lending l, LendingObject lo where p = l.person AND lo MEMBER OF l.lendings AND lo.id = :lendingObjectId"),
    @NamedQuery(name = "LendingObject.getLendingsWithMaterial",
            query = "SELECT lo from LendingObject lo where lo.type.id = :materialId")
})
@Entity
@LendingCheck
public class LendingObject implements Serializable {

    /**
     * Id der Ausleihe. Wird automatisch erzeugt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Typ der auszuleihenden Gegenstaende.
     */
    private Material type;
    /**
     * Anzahl der auszuleihenden Gegenstaende.
     */
    private int lendingQuantity;
    /**
     * Startdatum der Ausleihe.
     */
    private java.sql.Date startLendDate;
    /**
     * Enddatum der Ausleihe.
     */
    private java.sql.Date endLendDate;

    /**
     * Erstellt ein Objekt vom Typ Lending ohne Eigenschaften.
     */
    public LendingObject() {

    }

    /**
     * Erstellt ein Objekt vom Typ Lending mit Gegenstandtyp, Anzahl der
     * ausgeliehenen Gegenstaende, die ausleihende Person, Start- und Enddatum.
     *
     * @param type Gegenstandtyp
     * @param lendingQuantity Anzahl der ausgeliehenen Gegenstaende
     * @param startLendDate Startdatum
     * @param endLendDate Enddatum
     */
    public LendingObject(Material type, int lendingQuantity,
            Date startLendDate, Date endLendDate) {

        this.type = type;
        this.lendingQuantity = lendingQuantity;
        if (endLendDate != null && startLendDate != null) {
            this.startLendDate = new java.sql.Date(startLendDate.getTime());
            this.endLendDate = new java.sql.Date(endLendDate.getTime());
        }
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
     * Gibt das Startdatum der Ausleihe zurueck.
     *
     * @return Startdatum der Ausleihe
     */
    public Date getStartLendDate() {
        return startLendDate;
    }

    /**
     * Setzt das Startdatum der Ausleihe neu.
     *
     * @param startLendDate Startdatum der Ausleihe
     */
    public void setStartLendDate(Date startLendDate) {
        this.startLendDate = new java.sql.Date(startLendDate.getTime());
    }

    /**
     * Gibt das Enddatum der Ausleihe zurueck.
     *
     * @return Enddatum der Ausleihe
     */
    public Date getEndLendDate() {
        return endLendDate;
    }

    /**
     * Setzt das Enddatum der Ausleihe neu.
     *
     * @param endLendDate Enddatum der Ausleihe
     */
    public void setEndLendDate(Date endLendDate) {
        this.endLendDate = new java.sql.Date(endLendDate.getTime());

    }

    /**
     * Gibt den Typ des Ausleihgegenstandes zurueck.
     *
     * @return Typ des Ausleihgegenstandes
     */
    public Material getType() {
        return type;
    }

    /**
     * Setzt den Typ des Ausleihgegenstandes neu.
     *
     * @param type Typ des Ausleihgegenstandes
     */
    public void setType(Material type) {
        this.type = type;
    }

    /**
     * Gibt die Anzahl der auszuleihenden Gegenstaende zurueck.
     *
     * @return Anzahl ausgeliehene Gegenstaende
     */
    public int getLendingQuantity() {
        return lendingQuantity;
    }

    /**
     * Setzt die Anzahl der auszuleihenden Gegenstaende neu.
     *
     * @param lendingQuantity Anzahl ausgeliehene Gegenstaende
     */
    public void setLendingQuantity(int lendingQuantity) {
        this.lendingQuantity = lendingQuantity;
    }
}

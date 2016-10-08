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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Die Klasse Material stellt einen Materialtypen mit Id, eingespeister Anzahl,
 * Name und Beschreibung dar. Gleichzeitig dient sie als Datenbank-Entity.
 *
 * @author Miriam
 */
@Entity
//@MaterialCheck
public class Material implements Serializable {

    /**
     * Id des erstellten Materials. Wird automatisch erstellt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    /**
     * Menge des erstellten Materials.
     */

    private int quantity;
    /**
     * Name des erstellten Materials.
     */    

    private String name;
    /**
     * Beschreibung des erstellten Materials.
     */
    private String description;

    /**
     * Erstellt ein neues Objekt vom Typ Material ohne Attribute.
     */
    public Material() {

    }

    /**
     * Erstellt ein neues Objekt vom Typ Material mit Namen, Beschreibung und
     * Menge.
     *
     * @param name Name des Materials
     * @param description Beschreibung des Materials
     * @param quantity Menge des Materials
     */
    public Material(String name, String description, int quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;

    }

    /**
     * Erhöht die Menge des Materials um die angegebene Anzahl.
     *
     * @param count Anzahl, um die erhoeht werden soll
     */
    public void increaseQuantity(int count) {

        this.quantity = this.getQuantity() + count;

    }

    /**
     * Verringert die Menge des Materials um die angegebene Anzahl.
     *
     * @param count Anzahl, um die verringert werden soll
     */
    public void decreaseQuantity(int count) {

        this.quantity = this.getQuantity() - count;

        if (this.quantity < 0) {
            this.quantity = 0;
        }

    }

    /**
     * Gibt die Id des Materials zurueck.
     *
     * @return Id des Materials
     */
    public long getId() {
        return id;
    }

    /**
     * Gibt die Menge des Materials zurueck.
     *
     * @return Menge des Materials
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setzt die Menge des Materials neu.
     *
     * @param quantity Menge des Materials
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gibt den Namen des Materials zurueck.
     *
     * @return Name des Materials
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Materials neu.
     *
     * @param name Name des Materials
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Beschreibung des Materials zurueck.
     *
     * @return Beschreibung des Materials
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung des Materials neu.
     *
     * @param description Beschreibung des Materials
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

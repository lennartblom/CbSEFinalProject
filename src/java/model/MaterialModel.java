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
import entities.Material;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Die Klasse MaterialModel stellt abstrahierte Funktionen zur Erstellung,
 * Veränderung und zum Loeschen von Materialien.
 *
 * @author Miriam
 */
@Dependent
public class MaterialModel implements Serializable {

    /**
     * Persistierer für den Zugriff auf die Datenbank.
     */
    @Inject
    private Persistierer db;
    /**
     * Id des Materials.
     */
    private long id;
    /**
     * Menge der hinzuzufuegenden Gegenstaende vom Materialtyp
     */
    private int quantity;
    /**
     * Name des Materials.
     */
    private String name;
    /**
     * Beschreibung des Materials.
     */
    private String description;

    /**
     * Erstellt ein neues MaterialModel.
     */
    public MaterialModel() {
        db = new Persistierer();
    }

    /**
     * Erzeugt ein neues Material, persistiert es und gibt es wieder zurueck.
     *
     * @param name Name des Materials
     * @param description Beschreibung des Materials
     * @param quantity Menge der Gegenstaende des Materials
     * @return Erzeugtes Material
     */
    public Material createMaterial(String name, String description, int quantity) {

        Material tmp = new Material(name, description, quantity);

        if (db != null && tmp != null) {

            db.persist(tmp);
        }

        return tmp;
    }

    /**
     * Gibt die Liste aller Materialien zurueck.
     *
     * @return Liste von Materialien
     */
    public List<Material> getMaterials() {
        return db.findAllMaterials();
    }

    /**
     *
     * @param pId
     */
    public void deleteMaterial(long pId) {

        db.removeMaterial(pId);
    }

    @Override
    public String toString() {

        return "Material [ id=" + id + ", Name=" + name + " ]";
    }

    /**
     * Gibt die Id des Material Models zurueck.
     *
     * @return Id des Material Models
     */
    public long getId() {
        return id;
    }

    /**
     * Gibt den Namen des Material Models zurueck.
     *
     * @return Name des Material Models
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt die Id des Material Models neu.
     *
     * @param id Id des Material Models
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setzt den Namen des Materials Models neu.
     *
     * @param name Name des Materials
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Speichert das uebergebene Material.
     *
     * @param material Zu speicherndes Material
     */
    public void saveChanges(Material material) {
        db.merge(material);
    }

    /**
     * Verringert die Menge der Gegenstaende um die uebergebene Anzahl.
     *
     * @param material Zu verringernendes Material
     * @param count Anzahl, um die verringert werden soll
     */
    public void increaseQuantity(Material material, int count) {

        material.increaseQuantity(count);
        db.merge(material);
    }

    /**
     * Erhoeht die Menge der Gegenstaende um die uebergebene Anzahl.
     *
     * @param material Zu erhoendes Material
     * @param count Anzahl, um die erhoeht werden soll
     */
    public void decreaseQuantity(Material material, int count) {

        material.decreaseQuantity(count);
        db.merge(material);
    }

    /**
     * Setzt die Menge der Gegenstaende neu.
     *
     * @param quantity Menge der Gegenstaende
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Setzt die Beschreibung des Materials neu.
     *
     * @param description Beschreibung des Materials
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gibt die Menge der Gegenstaende des Materials zurueck.
     *
     * @return Menge der Gegenstaende
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sucht ein Material über die Id.
     *
     * @param id Id
     * @return Material
     */
    public Material findMaterial(long id) {
        return this.db.findMaterial(id);
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
     * Updatet ein Material.
     *
     * @param id Id
     * @param description Beschreibung
     * @param quantity Menge
     * @return Material
     */
    public Material updateMaterial(long id, String description, int quantity) {

        Material tmp = findMaterial(id);
        
        tmp.increaseQuantity(quantity);
        if (tmp.getDescription().equals("")) {
            tmp.setDescription(description);
        }
        
        db.merge(tmp);
        return tmp;
    }

}

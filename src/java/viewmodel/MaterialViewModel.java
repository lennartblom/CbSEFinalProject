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

import model.MaterialModel;
import entities.Material;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import validation.annotation.MaterialCheck;

/**
 * MaterialViewModel repräsentiert das ViewModel der Material-Klasse.
 *
 * @author Miriam
 */
@Named("material")
@RequestScoped
@MaterialCheck
public class MaterialViewModel implements Serializable {

    /**
     * Id des MaterialViewModels
     */
    private long id;

    /**
     * Name des MaterialViewModels
     */
    @NotNull(message = "Name muss angegeben werden")
    @Size(min = 1, message = "Name darf nicht leer sein")
    private String name;

    /**
     * Anzahl des MaterialViewModels
     */
    @Min(value = 0, message = "Die Anzahl kann nicht negativ sein")
    private int quantity;

    /**
     * Beschreibung des MaterialViewModels
     */
    private String description;

    /**
     * MaterialModel zur Verwaltung
     */
    @Inject
    private MaterialModel material;

    /**
     * Erstellt ein MaterialViewModel ohne Eigenschaften.
     */
    public MaterialViewModel() {
        this.name = "";
        this.quantity = 0;
        this.material = new MaterialModel();
    }

    /**
     * Erstellt ein neues Material.
     */
    public void createMaterial() {


        this.material.createMaterial(this.name, this.description, this.quantity);

        this.description = "";
        this.name = "";
        this.quantity = 0;
    }

    /**
     * Gibt die ID des MaterialViewModels zurueck.
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Gibt die Liste der Materialien zurueck.
     *
     * @return Liste der Materialien
     */
    public List<Material> getMaterials() {
        return this.material.getMaterials();
    }

    /**
     * Löscht ein Material über die ID.
     *
     * @param id zu löschendes Material
     */
    public void deleteMaterial(long id) {
        this.material.deleteMaterial(id);
    }

    /**
     * Erhöht die Anzahl des Materials um die übergebene Zahl.
     *
     * @param material zu erhöhendes Material
     * @param count erhöhende Menge
     */
    public void increaseQuantity(Material material, int count) {

        this.material.increaseQuantity(material, count);

    }

    /**
     * Verringert die Anzahl des Materials um die übergebene Zahl.
     *
     * @param material zu verringerndes Material
     * @param count verringernde Anzahl
     */
    public void decreaseQuantity(Material material, int count) {

        this.material.decreaseQuantity(material, count);

    }

    /**
     * Gibt den Namen des MaterialViewModels zurueck.
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des MaterialViewModels neu.
     *
     * @param name neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Anzahl des MaterialViewModels zureuck.
     *
     * @return Anzahl
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setzt die Anzahl des MaterialViewModels neu.
     *
     * @param quantity Anzahl
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gibt das Material des MaterialViewModels zureuck.
     *
     * @return Material
     */
    public MaterialModel getMaterial() {
        return material;
    }

    /**
     * Setzt das Material des MaterialViewModels neu.
     *
     * @param material Material
     */
    public void setMaterial(MaterialModel material) {
        this.material = material;
    }

    /**
     * Gibt die Beschreibung des MaterialViewModels zurueck.
     *
     * @return Beschreibung
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung des MaterialViewModels neu.
     *
     * @param description neue Beschreibung
     */
    public void setDescription(String description) {
        this.description = description;
    }

}

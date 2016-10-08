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
import entities.Material;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.LendingObjectModel;

/**
 * LendingObjectViewModel repräsentiert das ViewModel der LendingObject-Klasse.
 *
 * @author Fabian
 */
@Named("lendingObject")
@RequestScoped
public class LendingObjectViewModel implements Serializable {

    /**
     * Id des LendingObjects.
     */
    private long id;

    /**
     * Material des LendingObjects.
     */
    private Material type;

    /**
     * Menge des LendingObjects.
     */
    int lendingQuantity;

    /**
     * Startdatum des LendingObjects.
     */
    private Date startLendDate;

    /**
     * Enddatum des LendingObjects.
     */
    private Date endLendDate;

    /**
     * Startdatum des LendingObjects als String.
     */
    private String startLendDateString;

    /**
     * Enddatum des LendingObjects als String.
     */
    private String endLendDateString;

    /**
     * LendingController zur Verwaltung.
     */
    @Inject
    private LendingController lendingController;

    /**
     * LendingObjectModel zur Verwaltung
     */
    @Inject
    private LendingObjectModel lendingObject;

    /**
     * Erstellt ein LendingObjectViewModel ohne Eigenschaften.
     */
    public LendingObjectViewModel() {
        this.type = null;
        this.lendingQuantity = 0;
        this.startLendDate = null;
        this.endLendDate = null;
        this.lendingObject = new LendingObjectModel();
        this.lendingController = new LendingController();
    }

    /**
     * Gibt eine Liste mit den aktuellen Ausleihen zurueck.
     *
     * @return Liste der aktuellen Ausleihen
     */
    public List<Lending> getCurrentLendings() {
        return this.lendingController.getCurrentLendings();
    }

    /**
     * Gibt eine Liste mit den aktuellen ausgeliehenen Objekten zurueck.
     *
     * @return Liste der aktuellen ausgeliehenen Objekte
     */
    public List<LendingObject> getLendingObjects() {
        return this.lendingObject.getLendingObjects();
    }

    

    /**
     * Gibt die Id des LendingObjectViewModels zurueck.
     *
     * @return Id
     */
    public long getId() {
        return id;
    }

    /**
     * Gibt das Material des LendingObjectViewModels zurueck.
     *
     * @return Material
     */
    public Material getType() {
        return type;
    }

    /**
     * Setzt den Materialtyp des LendingObjectViewModels.
     *
     * @param type Material
     */
    public void setType(Material type) {
        this.type = type;
    }

    /**
     * Gibt die Ausleihmenge des LendingObjectViewModels zurueck.
     *
     * @return Ausleihmenge
     */
    public int getLendingQuantity() {
        return lendingQuantity;
    }

    /**
     * Setzt die Ausleihmenge des LendingObjectViewModels neu.
     *
     * @param lendingQuantity Ausleihmenge
     */
    public void setLendingQuantity(int lendingQuantity) {
        this.lendingQuantity = lendingQuantity;
    }

    /**
     * Gibt das Startdatum des LendingObjectViewModels zurueck.
     *
     * @return Startdatum
     */
    public Date getStartLendDate() {
        return startLendDate;
    }

    /**
     * Setzt das Startdatum des LendingObjectViewModels neu.
     *
     * @param startLendDate Startdatum
     */
    public void setStartLendDate(Date startLendDate) {
        this.startLendDate = startLendDate;
    }

    /**
     * Gibt das Enddatum des LendingObjectViewModels zurueck.
     *
     * @return Enddatum
     */
    public Date getEndLendDate() {
        return endLendDate;
    }

    /**
     * Setzt das Enddatum des LendingObjectViewModels neu.
     *
     * @param endLendDate Enddatum
     */
    public void setEndLendDate(Date endLendDate) {
        this.endLendDate = endLendDate;
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
     * Setzt das String-Startdatum des LendingObjectViewModels neu.
     *
     * @param startLendDateString Startdatum als String
     */
    public void setStartLendDateString(String startLendDateString) {
        this.startLendDateString = startLendDateString;
    }

    /**
     * Setzt das String-Enddatum des LendingObjectViewModels neu.
     *
     * @param endLendDateString Enddatum als String
     */
    public void setEndLendDateString(String endLendDateString) {
        this.endLendDateString = endLendDateString;
    }

    /**
     * Gibt das String-Startdatum des LendingObjectViewModels zurueck.
     *
     * @return Startdatum als String
     */
    public String getStartLendDateString() {
        return startLendDateString;
    }

    /**
     * Gibt das String-Enddatum des LendingObjectViewModels zurueck.
     *
     * @return Enddatum als String
     */
    public String getEndLendDateString() {
        return endLendDateString;
    }

    /**
     * Gibt den Namen der Ausleihperson über die LendingObjectId zurueck.
     *
     * @param lendingObjectId Id des Ausleihobjekts
     * @return Name der Ausleihperson
     */
    public String getLenderNameWithId(long lendingObjectId) {
        return this.lendingObject.getLenderNameWithId(lendingObjectId);
    }

}

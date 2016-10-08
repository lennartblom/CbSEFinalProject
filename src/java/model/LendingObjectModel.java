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
import entities.LendingObject;
import entities.Material;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Die Klasse LendingModel stellt abstrahierte Funktionen zur Erstellung und
 * Loeschen von Ausleihen.
 *
 * @author Fabian
 */
@Dependent
public class LendingObjectModel implements Serializable {

    /**
     * Persistierer für den Zugriff auf die Datenbank.
     */
    @Inject
    private Persistierer db;
    /**
     * MaterialModel zur Eintragungen in der Datenbank.
     */
    @Inject
    private MaterialModel material;

    /**
     * Erstellt ein neues LendingObjectModel ohne Eigenschaften.
     */
    public LendingObjectModel() {
        this.material = new MaterialModel();
        this.db = new Persistierer();
    }

    /**
     * Erstellt ein neues Ausleihobjekt.
     *
     * @param material Material
     * @param quantity Menge
     * @param startLendDate Startdatum
     * @param endLendDate Enddatum
     * @return Ausleihobjekt
     */
    public LendingObject createNewLendingObject(Material material,
            int quantity, Date startLendDate, Date endLendDate) {
        LendingObject tmp = null;
        try {
            tmp = new LendingObject(material, quantity,
                    startLendDate, endLendDate);

            if (tmp != null) {
                db.persist(tmp);
            }

        } catch (Exception e) {
            Throwable t = e;

            while (t != null) {
                if (t instanceof ConstraintViolationException) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) t).getConstraintViolations();
                    for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                        FacesMessage facesMessage = new FacesMessage(constraintViolation.getMessage());
                        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                        context.addMessage(null, facesMessage);
                    }
                }
                t = t.getCause();
            }
            db.remove(tmp);
            return null;
        }

        return tmp;

    }

    /**
     * Gibt alle Ausleihobjekte zurück.
     *
     * @return Liste von Ausleihobjekten
     */
    public List<LendingObject> getLendingObjects() {
        return db.findAllLendingObjects();
    }

    /**
     * Gibt alle Ausleihobjekte über die Materialid zurück.
     *
     * @param materialId Materialid
     * @return Liste der Ausleihobjekte
     */
    public List<LendingObject> getLendingObjectsWithMaterialId(long materialId) {
        return db.getLendingObjectsWithMaterialId(materialId);
    }

    /**
     * Gibt Namen der ausleihenden Person zuruück.
     *
     * @param lendingObjectId Ausleihobjektid
     * @return Namen der Person
     */
    public String getLenderNameWithId(long lendingObjectId) {
        return db.getLenderNameWithId(lendingObjectId);
    }

    /**
     * Löscht eine Ausleihe anhand der Id.
     *
     * @param id Id der zu loeschenden Ausleihe
     */
    public void deleteLending(long id) {
        db.removeLending(id);
    }

}

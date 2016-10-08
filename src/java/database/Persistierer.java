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
package database;

import entities.Lending;
import entities.LendingObject;
import entities.Material;
import entities.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author lennartblom
 */
@Stateless
public class Persistierer {

    @Inject
    private EntityManager em;

    /**
     * Persistiert übergebenes Objekt.
     *
     * @param object zu persistierendes Objekt
     */
    public void persist(Object object) {
        //System.out.println("public void persist()");
        if (this.em != null) {
            //System.out.println("Objekt persistieren.");
            this.em.persist(object); //Exception weiterreichen
        } else {
            //System.out.println("EntityManager noch nicht richtig initialisiert");
        }
    }

    /**
     * Löscht übergebenes Objekt.
     *
     * @param object zu löschendes Objekt
     */
    public void remove(Object object) {
        em.remove(object);
    }

    /**
     * Gibt Material über die MaterialId zurück.
     *
     * @param id gesuchtes Material
     * @return Material
     */
    public Material findMaterial(long id) {
        return em.find(Material.class, id);
    }

    /**
     * Gibt Person über die PersonId zurück.
     *
     * @param id gesuchte Person
     * @return Person
     */
    public Person findPerson(long id) {
        return (Person) em.find(Person.class, id);
    }

    /**
     * Löscht Material über die Id.
     *
     * @param pId zu löschendes Material
     */
    public void removeMaterial(long pId) {
        Material tmp = (Material) em.find(Material.class, pId);
        em.remove(tmp);
    }

    /**
     * Löscht Person über die Id.
     *
     * @param id zu löschende Person
     */
    public void removePerson(long id) {
        Person tmp = (Person) em.find(Person.class, id);
        em.merge(tmp);
    }

    /**
     * Überprüft ob das übergebene Objekt existiert.
     *
     * @param object prüfendes Objekt
     * @return true, wenn es existiert. False wenn nicht.
     */
    public boolean contains(Object object) {

        if (this.findMaterial(((Material) object).getId()) != null) {

            return true;

        } else {

            return false;
        }
    }

    /**
     * Mergt übergebenes Objekt.
     *
     * @param object Objekt.
     * @return Objekt
     */
    public Object merge(Object object) {
        return em.merge(object); //Exception weiterreichen
    }

    /**
     * Gibt eine Liste von allen Materialien zurück.
     *
     * @return Liste der Materialien
     */
    public List<Material> findAllMaterials() {
        return em.createQuery("SELECT m FROM Material m", Material.class)
                .getResultList();
    }

    /**
     * Gibt eine Liste von allen Ausleihen zurück.
     *
     * @return Liste der Ausleihen
     */
    public List<Lending> findAllLendings() {
        return em.createQuery("SELECT m FROM Lending m", Lending.class)
                .getResultList();
    }

    /**
     * Gibt eine Liste von allen Personen zurück.
     *
     * @return Liste der Personen
     */
    public List<Person> findAllPersons() {
        return em.createQuery("SELECT m FROM Person m", Person.class)
                .getResultList();
    }

    /**
     * Gibt die Menge der Ausleihobjekte über die Ausleihid zurück.
     *
     * @param lendingId Ausleihid
     * @return Menge der Ausleihobjekte
     */
    public int getLendingObjectsQuantityWithLendingId(long lendingId) {
        return ((Number) em.createNamedQuery("Lending.getLendingObjectsQuantity")
                .setParameter("lendingId", lendingId).getSingleResult()).intValue();
    }

    /**
     * Gibt die Menge der Ausleihobjekte über die Personid zurück.
     *
     * @param personId PersonId.
     * @return Menge der Ausleihobjekte
     */
    public int getLendingObjectsQuantityWithPersonId(long personId) {
        int result = 0;

        for (Lending l : this.getLendingsFromPersonId(personId)) {
            result += this.getLendingObjectsQuantityWithLendingId(l.getId());
        }

        return result;
    }

    /**
     * Gibt die ausleihende Person über die AusleihobjektId zurück.
     *
     * @param lendingObjectId Id des Ausleihobjekts
     * @return Name der Person
     */
    public String getLenderNameWithId(long lendingObjectId) {
        return ((String) em.createNamedQuery("LendingObject.getLenderName")
                .setParameter("lendingObjectId", lendingObjectId).getSingleResult()).toString();
    }

    /**
     * Gibt eine Liste von Ausleihobjekten zurück über die Ausleihid.
     *
     * @param lendingId AusleihId
     * @return Liste von Ausleihobjekten
     */
    public List<LendingObject> getLendingObjectsFromLendingId(long lendingId) {
        return em.createNamedQuery("Lending.getLendingObjectsFromLending", LendingObject.class)
                .setParameter("lendingId", lendingId).getResultList();
    }

    /**
     * Gibt alle Ausleihobjekte zurück.
     *
     * @return Liste von Ausleihobjekten
     */
    public List<LendingObject> findAllLendingObjects() {
        return em.createQuery("SELECT m FROM LendingObject m", LendingObject.class)
                .getResultList();
    }

    /**
     * Gibt alle Ausleihen von einer Person zurück.
     *
     * @param personId Person
     * @return Liste von Ausleihen
     */
    public List<Lending> getLendingsFromPersonId(long personId) {
        return em.createNamedQuery("Lending.getLendingsFromFromPersonId", Lending.class)
                .setParameter("personId", personId).getResultList();
    }

    /**
     * Gibt das früheste Datum einer Ausleihe zurück.
     *
     * @param lendingId Ausleihe
     * @return Frühestes Datum
     */
    public java.sql.Date getMinDateFromLendingId(long lendingId) {
        return em.createNamedQuery("Lending.getMinDate", java.sql.Date.class)
                .setParameter("lendingId", lendingId).setMaxResults(1).getSingleResult();
    }

    /**
     * Gibt das späteste Datum einer Ausleihe zurück.
     *
     * @param lendingId Ausleihe
     * @return Spätestes Datum
     */
    public java.sql.Date getMaxDateFromLendingId(long lendingId) {
        return em.createNamedQuery("Lending.getMaxDate", java.sql.Date.class)
                .setParameter("lendingId", lendingId).setMaxResults(1).getSingleResult();
    }

    /**
     * Gibt eine Liste von Ausleihobjekten zurück, die einem Material angehören.
     *
     * @param materialId Material
     * @return Liste von Ausleihobjekten
     */
    public List<LendingObject> getLendingObjectsWithMaterialId(long materialId) {
        return em.createNamedQuery("LendingObject.getLendingsWithMaterial", LendingObject.class)
                .setParameter("materialId", materialId).getResultList();
    }

    /**
     * Löscht eine Ausleihe über die ID.
     *
     * @param id Id der Ausleihe
     */
    public void removeLending(long id) {

        Lending tmp = this.em.find(Lending.class, id);

        if (tmp.getLendings().size() > 0) {
            for (LendingObject lo : tmp.getLendings()) {
                this.em.remove(lo);
            }

            Lending toDelete = this.em.merge(tmp);

            this.em.remove(toDelete);
        } else {
            this.em.remove(tmp);
        }
    }

}

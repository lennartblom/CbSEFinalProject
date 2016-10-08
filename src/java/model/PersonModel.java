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

import controller.LendingSystem;
import database.Persistierer;
import entities.Person;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Die Klasse PersonModel stellt abstrahierte Funktionen zur Erstellung,
 * Veränderung und zum Loeschen von Personen.
 *
 * @author Fabian
 */
@Dependent
public class PersonModel implements Serializable {

    /**
     * Persistierer für den Zugriff auf die Datenbank.
     */
    @Inject
    private Persistierer db;

    /**
     * Erstellt ein neues PersonModel ohne Eigenschaften.
     */
    public PersonModel() {
        this.db = new Persistierer();
    }

    /**
     * Erstellt eine neue Person mit dem uebergebenen Namen und gibt diese nach
     * der Persistierung in der Datenbank zurueck.
     *
     * @param name Name der Person
     * @return Erstellte Person
     */
    public Person createPerson(String name) {

        Person tmp = checkIfPersonNotAlreadyExists(name);

        if (tmp != null) {
            return tmp;
        } else {
            tmp = new Person(name);
        }

        if (db != null && tmp != null) 
            db.persist(tmp);
        

        return tmp;
    }

    /**
     * Überpüft, ob die Person schon exisitiert.
     *
     * @param name Name der zu prüfenden Person
     * @return gefundene Person
     */
    public Person checkIfPersonNotAlreadyExists(String name) {

        List<Person> tmpPersons = this.db.findAllPersons();
        String nameConform = LendingSystem.makeStringConform(name);

        for (Person p : tmpPersons) {
            String tmpPersonnameStringConform = LendingSystem.makeStringConform(p.getName());
            if (tmpPersonnameStringConform.equals(nameConform)) {
                return p;
            }
        }

        return null;
    }

    /**
     * Gibt alle Personen zurück.
     *
     * @return Liste der Personen
     */
    public List<Person> getPersons() {
        return this.db.findAllPersons();
    }

    /**
     * Sucht eine Person über die übergebene Id.
     *
     * @param id Id
     * @return Person
     */
    public Person findPerson(long id) {
        return this.db.findPerson(id);
    }

    /**
     * Gibt die Anzahl der Ausleihobjekte über die PersonId zurück.
     *
     * @param personId Id
     * @return Anzahl der Ausleihobjekte
     */
    public int getLendingObjectsQuantityWithPersonId(long personId) {
        return this.db.getLendingObjectsQuantityWithPersonId(personId);
    }

    /**
     * Löscht eine Person anhand der Id in der Datenbank.
     *
     * @param id Id der zu loeschenden Person
     */
    public void deletePerson(long id) {
        db.removePerson(id);
    }

}

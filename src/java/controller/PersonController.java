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
package controller;

import entities.Person;
import model.PersonModel;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Die Klasse PersonController dient zur Verwaltung aller Personen.
 *
 * @author Miriam
 */
@Dependent
public class PersonController implements Serializable {

    /**
     * PersonModel für Eingabe in der Datenbank.
     */
    @Inject
    private PersonModel person;
    /**
     * Liste aller Personen.
     */
    private Map<Long, Person> persons;

    /**
     * Erstellt eine neue PersonControl.
     */
    public PersonController() {
        this.persons = new HashMap<>();
        this.person = new PersonModel();

    }

    /**
     * Fügt eine neue Person hinzu.
     *
     * @param name Name der Person
     * @return Person
     */
    public Person addPerson(String name) {
        Person p = this.person.createPerson(name);
        
        return this.findPerson(p.getId());

    }

    /**
     * Löscht eine Person.
     *
     * @param id Id der Person
     */
    public void deletePerson(long id) {
        this.persons.remove(id);
        this.person.deletePerson(id);
    }

    /**
     * Überprüft anhand der Id, ob eien Person bereits existiert.
     *
     * @param id Id er Person
     * @return false, wenn Person nicht exisitert true, wenn Person exisitert
     */
    public boolean doesPersonExist(long id) {
        return this.persons.containsKey(id);
    }

    /**
     * Sucht eine Person in der Map.
     *
     * @param id Id der Person
     * @return Gefundene Person
     */
    public Person findPerson(long id) {
        return this.person.findPerson(id);
    }

    /**
     * Gibt eine Liste von allen Personen zurueck.
     *
     * @return Liste von Personen
     */
    public List<Person> getPersons() {
        return this.person.getPersons();
    }

    /**
     * Gibt alle ausgeliehenen Objekte über die Personid zurueck.
     *
     * @param personId Person ID
     * @return ausgeliehene Objekte
     */
    public int getLendingObjects(long personId) {
        return this.person.getLendingObjectsQuantityWithPersonId(personId);
    }

}

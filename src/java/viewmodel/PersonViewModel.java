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

import controller.PersonController;
import entities.Person;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * PersonViewModel repräsentiert das ViewModel der Person-Klasse.
 *
 * @author Fabian
 */
@Named("person")
@RequestScoped
public class PersonViewModel implements Serializable {

    /**
     * Id des PersonViewModels.
     */
    private long id;

    /**
     * Name des PersonViewModels.
     */
    private String name;
    /**
     * PersonController zur Verwaltung der Person.
     */
    @Inject
    private PersonController person;

    /**
     * Erstellt ein PersonViewModel ohne Eigenschaften.
     */
    public PersonViewModel() {
        this.name = "";
        person = new PersonController();
    }

    /**
     * Gibt eine Liste aller Personen zurueck.
     *
     * @return Liste aller Personen
     */
    public List<Person> getPersons() {
        return this.person.getPersons();
    }

    /**
     * Gibt Anzahl der LendingObjects zurueck.
     *
     * @param personId Id der ausleihenden Person
     * @return Anzahl der LendingObjects
     */
    public int getLendingObjects(long personId) {
        return this.person.getLendingObjects(personId);
    }

    /**
     * Löscht eine Person im Controller über die ID.
     *
     * @param id ID der zu löschenden Person
     */
    public void deletePerson(long id) {
        this.person.deletePerson(id);
    }

    /**
     * Gibt die ID des PersonViewModels zurueck.
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Gibt den Namen des PersonViewModels zurueck.
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des PersonViewModels.
     *
     * @param name neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

}

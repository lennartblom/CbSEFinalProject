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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Die Klasse Person stellt eine Person mit einem Namen und einer Id dar.
 * Gleichzeitig dient sie als Datenbank-Entity.
 *
 * @author Miriam
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Person.getLendingObjectQuantity",
            query = "SELECT SUM(lo.lendingQuantity) AS quantity FROM LendingObject lo, Lending l, Person p WHERE p.id = :personId AND l.person = p AND lo MEMBER OF l.lendings"),})
public class Person implements Serializable {

    /**
     * Id der Person. Wird automatisch erstellt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Name der Person.
     */
    private String name;

    /**
     * Erstellt eine Person ohne Eigenschaften.
     */
    public Person() {

    }

    /**
     * Erstellt eine Person mit einem Namen.
     *
     * @param name Name der Person
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * Gibt die Id der Person zurueck.
     *
     * @return Id der Person
     */
    public long getId() {
        return id;
    }

    /**
     * Gibt den Namen der Person zurueck.
     *
     * @return Name der Person
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Person neu.
     *
     * @param name Name der Person
     */
    public void setName(String name) {
        this.name = name;
    }

}

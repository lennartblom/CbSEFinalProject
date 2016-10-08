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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miriam
 */
public class LendingSystemTest {

    static LendingSystem ls = new LendingSystem();

    public LendingSystemTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        //ls = new LendingSystem();
     //   ls.addMaterial("Material", "test", 5);
        //ls.addMaterial("Material", "Test", 5);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Testen der addMaterial-Methode.
     */
    @Test
    public void testAddMaterial() {

        assertTrue("False, obwohl Material vorhanden", ls.doesMaterialExist("Material"));
        assertFalse("True, obwohl Material nicht vorhanden ist", ls.doesMaterialExist("Materials"));
    }

    /**
     * Testen der testDoesMaterialExist_String-Methode.
     */
    @Test
    public void testDoesMaterialExist_String() {

        assertTrue("False, obwohl Material vorhanden", ls.doesMaterialExist("Material"));
        assertTrue("False, obwohl Material vorhanden", ls.doesMaterialExist("material"));
        assertFalse("True, obwohl Material nicht vorhanden ist", ls.doesMaterialExist("Materials"));

    }

    /**
     * Testen der testMakeStringConform-Methode.
     */
    @Test
    public void testMakeStringConform() {
        String s1 = LendingSystem.makeStringConform("Test");
        String s2 = "test";

        String s3 = LendingSystem.makeStringConform("muß");
        String s4 = "muss";

        String s5 = LendingSystem.makeStringConform("müss");
        String s6 = "muess";

        String s7 = LendingSystem.makeStringConform("mäss");
        String s8 = "maess";

        String s9 = LendingSystem.makeStringConform("möss");
        String s10 = "moess";

        String s11 = LendingSystem.makeStringConform("müss");
        String s12 = "muess";

        String s13 = LendingSystem.makeStringConform("test");

        assertEquals("Ergebnis ist ungleich, obwohl gleich.", s1, s2);
        assertEquals("Ergebnis ist ungleich, obwohl gleich.", s3, s4);
        assertEquals("Ergebnis ist ungleich, obwohl gleich.", s5, s6);
        assertEquals("Ergebnis ist ungleich, obwohl gleich.", s7, s8);
        assertEquals("Ergebnis ist ungleich, obwohl gleich.", s9, s10);
        assertEquals("Ergebnis ist ungleich, obwohl gleich.", s11, s12);
        assertEquals("Ergebnis ist ungleich, obwohl gleich.", s13, s2);

    }

}
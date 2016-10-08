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
package validation.validator;

import entities.LendingObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import validation.annotation.LendingCheck;

/**
 *
 * @author lennartblom
 */
public class LendingValidator implements ConstraintValidator<LendingCheck, LendingObject> {

    /**
     * wird nicht benötigt
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(LendingCheck constraintAnnotation) {
        // Wird nicht benötigt 
    }

    /**
     * Gibt zurueck ob die uebergebenen Daten gültig sind.
     *
     * @param value Ausleihobjekt
     * @param context Kontext
     * @return true, wenn gültig. False wenn nicht
     */
    @Override
    public boolean isValid(LendingObject value, ConstraintValidatorContext context) {

        if (value.getStartLendDate() == null || value.getEndLendDate() == null) {
            context.disableDefaultConstraintViolation();
            //System.out.println("Startdautm und Enddatum m\u00fcssen angegeben werden");
            context.buildConstraintViolationWithTemplate("Startdatum und Enddatum m\u00fcssen angegeben werden").addConstraintViolation();
            return false;
        }
        if (value.getStartLendDate().after(value.getEndLendDate())) {
            context.disableDefaultConstraintViolation();
            //System.out.println("Enddatum darf nicht vor Startdatum liegen");
            context.buildConstraintViolationWithTemplate("Enddatum darf nicht vor Startdatum liegen").addConstraintViolation();
            return false;
        }

        return true;
    }

}

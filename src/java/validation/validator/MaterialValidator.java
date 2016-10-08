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

import entities.Material;
import validation.annotation.MaterialCheck;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * MaterialValidator ist zur Validierung der Materialien noetig.
 *
 * @author lennartblom
 *
 */
public class MaterialValidator implements ConstraintValidator<MaterialCheck, Material> {

    /**
     * Initialisierung der Materialueberpruefung.
     *
     * @param constraintAnnotation Annotation
     */
    @Override
    public void initialize(MaterialCheck constraintAnnotation) {

    }

    /**
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Material value, ConstraintValidatorContext context) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

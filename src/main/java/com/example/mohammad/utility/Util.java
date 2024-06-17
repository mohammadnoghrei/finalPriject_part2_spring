package com.example.mohammad.utility;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Objects;
import java.util.Set;

public class Util {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();


    public boolean validate(Objects entity) {

        Set<ConstraintViolation<Objects>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Objects> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }
}

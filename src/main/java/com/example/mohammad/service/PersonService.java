package com.example.mohammad.service;

import com.example.mohammad.model.Person;
import com.example.mohammad.repository.PersonRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class PersonService  {
    private final PersonRepository personRepository;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();


    public boolean validate(Person entity) {

        Set<ConstraintViolation<Person>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Person> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }
}

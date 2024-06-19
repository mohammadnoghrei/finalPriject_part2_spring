package com.example.mohammad.service;

import com.example.mohammad.model.Admin;
import com.example.mohammad.repository.AdminRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AdminService  {
    private final AdminRepository adminRepository;

     ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
   Validator validator = validatorFactory.getValidator();


    public boolean validate(Admin entity) {

        Set<ConstraintViolation<Admin>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Admin> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }


}

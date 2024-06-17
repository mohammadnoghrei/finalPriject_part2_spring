package com.example.mohammad.service;

import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.model.Customer;
import com.example.mohammad.model.Services;
import com.example.mohammad.repository.ServicesRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@RequiredArgsConstructor
@Service
public class ServicesService  {
    private final ServicesRepository servicesRepository;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();


    public boolean validate(Services entity) {

        Set<ConstraintViolation<Services>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Services> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }
    public Services findByNameServices(String name){
        return servicesRepository.findByServiceName(name).orElseThrow(()-> new NotFoundException(String.format("the entity with %s not found",name)));
    }
    public Services findById(Long id) {
        return servicesRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("the entity with %s not found", id)));
    }

    public void deleteById(Long id) {
        servicesRepository.delete(findById(id));
    }

    public void deleteByServiceName(String name) {
        servicesRepository.delete(findByNameServices(name));
    }

}
package com.example.mohammad.service;


import com.example.mohammad.exception.DuplicateInformationException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.model.SubServiceExpert;
import com.example.mohammad.model.SubServices;
import com.example.mohammad.repository.SubServiceExpertRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubServiceExpertService {
    private final SubServicesService subservicesService;
    private final ExpertService expertService;

    private final SubServiceExpertRepository subServiceExpertRepository;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();


    public boolean validate(SubServiceExpert entity) {

        Set<ConstraintViolation<SubServiceExpert>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<SubServiceExpert> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }

    public SubServiceExpert findBySubServiceAndExpert(String subServiceName, String expertUsername) {
        return subServiceExpertRepository.findBySubServicesAndExpert(subservicesService.findBySubServiceName(subServiceName), expertService.findByUsername(expertUsername)).orElseThrow(() -> new NotFoundException(String.format("the entity with %s & %snot found", subServiceName, expertUsername)));
    }

    public SubServiceExpert saveSubServiceExpert(String subServiceName, String expertUsername) {
        if (subServiceExpertRepository.findBySubServicesAndExpert(subservicesService.findBySubServiceName(subServiceName), expertService.findByUsername(expertUsername)).isPresent())
            throw new DuplicateInformationException(String.format("the entity with %s & %s is duplicate", expertUsername, subServiceName));
        else {
            SubServiceExpert subServiceExpert=SubServiceExpert.builder().expert(expertService.findByUsername(expertUsername)).subServices(subservicesService.findBySubServiceName(subServiceName)).registerDate(LocalDate.now()).build();
            return subServiceExpertRepository.save(subServiceExpert);
        }

    }
}

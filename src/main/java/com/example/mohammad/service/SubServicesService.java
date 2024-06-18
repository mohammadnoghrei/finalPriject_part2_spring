package com.example.mohammad.service;

import com.example.mohammad.exception.InvalidEntityException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.exception.NullListException;
import com.example.mohammad.model.Customer;
import com.example.mohammad.model.Services;
import com.example.mohammad.model.SubServices;
import com.example.mohammad.repository.SubServicesRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class SubServicesService  {
    private final SubServicesRepository subServicesRepository;
    private final ServicesService servicesService;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();


    public boolean validate(SubServices entity) {

        Set<ConstraintViolation<SubServices>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<SubServices> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }
    public List<SubServices> findAllSubServiceOfService(Services services){
        if (subServicesRepository.findAllByServices(services)==null)
            throw new NullListException(String.format("not found any sub service for %s service ",services.getServiceName()));
        return subServicesRepository.findAllByServices(services);
    }
    public SubServices saveSubService(SubServices subServices , String serviceName){
        subServices.setServices(servicesService.findByNameServices(serviceName));
        if (!validate(subServices))
            throw new InvalidEntityException(String.format("the customer with %s have invalid variable", subServices.getName()));
        return subServicesRepository.save(subServices);
    }
    public SubServices findById(Long id) {
        return subServicesRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("the entity with %s not found", id)));
    }

    public SubServices findBySubServiceName(String name) {
        return subServicesRepository.findByName(name).orElseThrow(() -> new NotFoundException(String.format("the entity with %s not found", name)));
    }
    public void deleteById(Long id) {
        subServicesRepository.delete(findById(id));
    }

    public void deleteByUsername(String name) {
        subServicesRepository.delete(findBySubServiceName(name));
    }

    public void updateDescription(String description,String name){
        if (subServicesRepository.findByName(name).isEmpty())
            throw new NullListException(String.format("not found any sub service with %s  ",name));
       else subServicesRepository.updateDescription(description,name);
    }
    public void updateBasePrice(double basePrice ,String name){
        if (subServicesRepository.findByName(name).isEmpty())
            throw new NullListException(String.format("not found any sub service with %s  ",name));
        else subServicesRepository.updateBasePrice(basePrice,name);
    }

}

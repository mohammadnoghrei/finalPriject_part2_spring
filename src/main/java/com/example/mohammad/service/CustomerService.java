package com.example.mohammad.service;

import com.example.mohammad.exception.DuplicateInformationException;
import com.example.mohammad.exception.InvalidEntityException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.exception.NotValidPasswordException;
import com.example.mohammad.model.Customer;
import com.example.mohammad.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
     Validator validator = validatorFactory.getValidator();


    public boolean validate(Customer entity) {

        Set<ConstraintViolation<Customer>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Customer> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("the entity with %s not found", id)));
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String.format("the entity with %s not found", username)));
    }

    public Customer registerCustomer(Customer customer) {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent())
            throw new DuplicateInformationException(String.format("the customer with %s is duplicate", customer.getUsername()));
        else if (!validate(customer)) {
            throw new InvalidEntityException(String.format("the customer with %s have invalid variable", customer.getUsername()));
        } else return customerRepository.save(customer);
    }


    public void deleteByUsername(String username) {
        customerRepository.delete(findByUsername(username));
    }

    @Transactional
    public void updatePassword(String username, String oldPassword, String newPassword, String finalNewPassword) {
        String passRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        if (customerRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException(String.format("the entity with %s not found", username));
        } else if (!newPassword.matches(passRegex) || !oldPassword.matches(passRegex) || !finalNewPassword.matches(passRegex)) {
            throw new NotValidPasswordException("this password not valid");
        } else if (!findByUsername(username).getPassword().equals( oldPassword )|| !newPassword.equals(finalNewPassword)) {
            throw new NotValidPasswordException("this password not valid");
        } else {
            customerRepository.updatePassword(finalNewPassword, username);
        }
    }
}

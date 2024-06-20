package com.example.mohammad.service;

import com.example.mohammad.exception.*;
import com.example.mohammad.model.Expert;
import com.example.mohammad.model.ExpertStatus;
import com.example.mohammad.repository.ExpertRepository;
import com.example.mohammad.utility.Util;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;

     ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
  Validator validator = validatorFactory.getValidator();


    public boolean validate(Expert entity) {

        Set<ConstraintViolation<Expert>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Expert> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }
    public List<Expert> findAllExpertByStatus(ExpertStatus expertStatus){
        return expertRepository.findAllByExpertStatus(expertStatus);
    }
    public Expert registerExpert(Expert expert,String imagePath){
        expert.setImage(Util.saveImage(imagePath));
        if (expertRepository.findByUsername(expert.getUsername()).isPresent())
            throw new DuplicateInformationException(String.format("the customer with %s is duplicate",expert.getUsername()));
        else if (!validate(expert)) {
            throw new InvalidEntityException(String.format("the customer with %s have invalid variable",expert.getUsername()));
        }else return expertRepository.save(expert);
    }

    public Expert findById(Long id){
        return expertRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("the entity with %s not found",id)));
    }

    public Expert findByUsername(String username){
        return expertRepository.findByUsername(username).orElseThrow(()-> new NotFoundException(String.format("the entity with %s not found",username)));
    }


    public void deleteById(Long id){
        expertRepository.delete(findById(id));
    }

    public void deleteByUsername(String username){
        expertRepository.delete(findByUsername(username));
    }
    @Transactional
    public void updatePassword(String username, String oldPassword, String newPassword, String finalNewPassword) {
        String passRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        if (expertRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException(String.format("the entity with %s not found", username));
        } else if (!newPassword.matches(passRegex) || !oldPassword.matches(passRegex) || !finalNewPassword.matches(passRegex)) {
            throw new NotValidPasswordException("this password not valid");
        } else if (!findByUsername(username).getPassword().equals(oldPassword) || !newPassword.equals(finalNewPassword))
            throw new NotValidPasswordException("this password not valid");
        else {
            expertRepository.updatePassword(finalNewPassword, username);
        }
    }
    @Transactional
    public void confirmExpert(String username){
        if (expertRepository.findByUsername(username).isEmpty())
            throw new NotFoundException(String.format("the entity with %s username not found",username));
        else if (findByUsername(username).getExpertStatus().equals(ExpertStatus.CONFIRMED)) {
            throw new ConfirmationException(String.format("the entity with %s username was confirmed before your confirmation ",username));
        }
        expertRepository.confirmExpert(ExpertStatus.CONFIRMED,username);
    }

    @Transactional
    public void updateScore(double score, String username){
        if (expertRepository.findByUsername(username).isEmpty())
            throw new NotFoundException(String.format("the entity with %s username not found",username));
        if (score<0)
            throw new InvalidEntityException(String.format("the Expert with %s have invalid variable",username));
        expertRepository.updateScore(score,username);
    }

}

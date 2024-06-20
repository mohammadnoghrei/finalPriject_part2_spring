package com.example.mohammad.service;


import com.example.mohammad.exception.DuplicateInformationException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.model.SubServiceExpert;
import com.example.mohammad.repository.SubServiceExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubServiceExpertService {
    private final SubServicesService subservicesService;
    private final ExpertService expertService;
    private final SubServiceExpertRepository subServiceExpertRepository;


    public SubServiceExpert findBySubServiceAndExpert(String subServiceName, String expertUsername) {
        return subServiceExpertRepository.findBySubServicesAndExpert(subservicesService.findBySubServiceName(subServiceName), expertService.findByUsername(expertUsername)).orElseThrow(() -> new NotFoundException(String.format("the entity with %s & %snot found", subServiceName, expertUsername)));
    }

    public SubServiceExpert saveSubServiceExpert(String subServiceName, String expertUsername) {
        if (subServiceExpertRepository.findBySubServicesAndExpert(subservicesService.findBySubServiceName(subServiceName), expertService.findByUsername(expertUsername)).isPresent())
            throw new DuplicateInformationException(String.format("the entity with %s & %s is duplicate", expertUsername, subServiceName));
        else {
            SubServiceExpert subServiceExpert=SubServiceExpert.builder()
                    .expert(expertService.findByUsername(expertUsername))
                    .subServices(subservicesService.findBySubServiceName(subServiceName))
                    .registerDate(LocalDate.now()).build();
            return subServiceExpertRepository.save(subServiceExpert);
        }
    }
    public void deleteBySubServiceAndExpert(String subServiceName, String expertUsername){
        subServiceExpertRepository.delete(findBySubServiceAndExpert(subServiceName,expertUsername));
    }
}

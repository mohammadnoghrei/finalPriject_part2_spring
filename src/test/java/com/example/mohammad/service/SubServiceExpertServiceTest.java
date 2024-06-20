package com.example.mohammad.service;

import com.example.mohammad.exception.DuplicateInformationException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.model.SubServiceExpert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubServiceExpertServiceTest {

    @Autowired
    private SubServiceExpertService subServiceExpertService;

    @Test
    @Order(1)
    void ThrowsNotFoundExceptionFindByUsernameTest() {
        assertThrows(NotFoundException.class, () -> subServiceExpertService.findBySubServiceAndExpert("nonexistentSubService","nonexistentExpert"));
    }

    @Test
    @Order(2)
    void saveCorrect() {
        SubServiceExpert subServiceExpert=subServiceExpertService.saveSubServiceExpert("carWash","jadexpert123");
        assertEquals(subServiceExpert.getExpert().getUsername(),"jadexpert123");
    }



    @Test
    @Order(3)
    void duplicateInformationExceptionSaveCorrect() {
        assertThrows(DuplicateInformationException.class, () -> subServiceExpertService.saveSubServiceExpert("carWash","jadexpert123"));
    }

    @Test
    @Order(4)
    void deleteTest(){
        subServiceExpertService.deleteBySubServiceAndExpert("carWash","jadexpert123");
        assertThrows(NotFoundException.class, () -> subServiceExpertService.findBySubServiceAndExpert("carWash","jadexpert123"));
    }
}

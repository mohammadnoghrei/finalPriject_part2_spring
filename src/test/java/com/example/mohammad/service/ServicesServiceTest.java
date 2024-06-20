package com.example.mohammad.service;

import com.example.mohammad.exception.DuplicateInformationException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.model.Services;
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
public class ServicesServiceTest {

    @Autowired
    private ServicesService servicesService;
    @Test
    @Order(1)
    void ThrowsNotFoundExceptionFindByUsernameTest() {
        assertThrows(NotFoundException.class, () -> servicesService.findByNameServices("nonexistentUser"));
    }
    @Test
    @Order(2)
    void ThrowsNotFoundExceptionFindByIdTest() {
        assertThrows(NotFoundException.class, () -> servicesService.findById(20800l));
    }

    @Test
    @Order(3)
    void saveCorrect() {
        Services services =servicesService.saveServices("vehicle");
        assertEquals(services.getServiceName(),servicesService.findByNameServices("vehicle").getServiceName());
    }

    @Test
    @Order(4)
    void duplicateInformationExceptionSaveCorrect() {
        assertThrows(DuplicateInformationException.class, () -> servicesService.saveServices("vehicle"));
    }
    @Test
    @Order(5)
    void findByIdTest() {
        Services services=servicesService.findById(4l);
        assertEquals("vehicle", services.getServiceName());
    }

    @Test
    @Order(6)
    void findByNameTest() {
        Services services =servicesService.findByNameServices("vehicle");
        assertEquals("vehicle", services.getServiceName());
    }

    @Test
    @Order(7)
    void deleteByNameTest(){
       servicesService.deleteByServiceName("vehicle");
        assertThrows(NotFoundException.class, () -> servicesService.findByNameServices("vehicle"));
    }
}

package com.example.mohammad.service;

import com.example.mohammad.exception.DuplicateInformationException;
import com.example.mohammad.exception.InvalidEntityException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.model.SubServices;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubServicesServiceTest {

    @Autowired
    private SubServicesService subServicesService;

    public static SubServices currentSubServices;
    public static SubServices duplicateSubServices;
    public static SubServices notFoundSubServices;
    public static SubServices notValidSubServices;

    @BeforeAll
    public static void createCustomer(){
        currentSubServices=SubServices.builder()
                .name("joloBandi").basePrice(100).description("jolo bandi mashin")
                .build();
        duplicateSubServices=SubServices.builder().name("joloBandi").basePrice(100).description("jolo bandi mashin")
                .build();
        notFoundSubServices=SubServices.builder().name("notfound").basePrice(0).description("xxx")
                .build();
        notValidSubServices=SubServices.builder().name("notValid").basePrice(-10).description("xxx")
                .build();
    }
    @Test
    @Order(1)
    void ThrowsNotFoundExceptionFindByUsernameTest() {
        assertThrows(NotFoundException.class, () -> subServicesService.findBySubServiceName("nonexistentUser"));
    }
    @Test
    @Order(2)
    void ThrowsNotFoundExceptionFindByIdTest() {
        assertThrows(NotFoundException.class, () -> subServicesService.findById(20800L));
    }

    @Test
    @Order(3)
    void saveCorrect() {
        SubServices subServices=subServicesService.saveSubService(currentSubServices,"vehicle");
        assertEquals(subServices.getName(),subServicesService.findBySubServiceName("joloBandi").getName());
    }
    @Test
    @Order(4)
    void invalidEntityExceptionSaveCorrect() {
        assertThrows(InvalidEntityException.class, () -> subServicesService.saveSubService(notValidSubServices,"vehicle"));
    }

    @Test
    @Order(5)
    void duplicateInformationExceptionSaveCorrect() {
        assertThrows(DuplicateInformationException.class, () -> subServicesService.saveSubService(duplicateSubServices,"vehicle"));
    }
    @Test
    @Order(6)
    void findAllSubServiceOfServiceTest(){
        List<SubServices> vehicle = subServicesService.findAllSubServiceOfService("vehicle");
        assertTrue(vehicle.size()>2);
    }

    @Test
    @Order(7)
    void findByIdTest() {
        SubServices subServices=subServicesService.findById(4l);
        assertEquals("carWash", subServices.getName());
    }

    @Test
    @Order(8)
    void findByNameTest() {
        SubServices subServices=subServicesService.findBySubServiceName("carWash");
        assertEquals("carWash", subServices.getName());
    }

    @Test
    @Order(9)
    void notFoundExceptionUpdateDescriptionTest(){
        assertThrows(NotFoundException.class, () -> subServicesService.updateDescription("xxx","notFound"));
    }

    @Test
    @Order(10)
    void updateDescriptionTest(){
        subServicesService.updateDescription("updated description","joloBandi");
        assertEquals("updated description", subServicesService.findBySubServiceName("joloBandi").getDescription());}
    @Test
    @Order(11)
    void notFoundExceptionUpdateBasePriceTest(){
        assertThrows(NotFoundException.class, () -> subServicesService.updateBasePrice(50,"notFound"));
    }
    @Test
    @Order(12)
    void invalidEntityExceptionUpdateBasePriceTest(){
        assertThrows(InvalidEntityException.class, () -> subServicesService.updateBasePrice(-50,"joloBandi"));
    }

    @Test
    @Order(13)
    void updateBasePriceTest(){
        subServicesService.updateBasePrice(Double.parseDouble("509"),"joloBandi");
        assertEquals(509, subServicesService.findBySubServiceName("joloBandi").getBasePrice());}

    @Test
    @Order(14)
    void deleteByNameTest(){
        subServicesService.deleteByName("carWash");
    }
}

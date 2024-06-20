package com.example.mohammad.service;

import com.example.mohammad.exception.*;
import com.example.mohammad.model.Customer;
import com.example.mohammad.model.Expert;
import com.example.mohammad.model.ExpertStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpertServiceTest {

    @Autowired
    private ExpertService expertService;

    public static Expert currentExpert;
    public static Expert duplicateExpert;
    public static Expert notFoundExpert;
    public static Expert notValidExpert;

    @BeforeAll
    public static void createExpert(){
        currentExpert=Expert.builder()
                .firstname("expert")
                .lastname("expertian")
                .username("expert123")
                .password("SDFfg@123")
                .cardBalance(25)
                .email("mohammad@gmail.com")
                .nationCode("3920066200")
                .expertStatus(ExpertStatus.WAITING_FOR_CONFIRMATION)
                .registerDate(LocalDate.now())
                .build();
        duplicateExpert=Expert.builder()
                .firstname("mohammad")
                .lastname("mohammadi")
                .username("mohammad123")
                .password("SDFfg@123")
                .cardBalance(25)
                .email("mohammad@gmail.com")
                .nationCode("3920066200")
                .registerDate(LocalDate.now())
                .expertStatus(ExpertStatus.WAITING_FOR_CONFIRMATION)
                .build();
        notFoundExpert=Expert.builder()
                .firstname("notFound")
                .lastname("notfound")
                .username("notfound")
                .password("SDFfg@123")
                .cardBalance(25)
                .email("notfound@gmail.com")
                .nationCode("3920066200")
                .registerDate(LocalDate.now())
                .expertStatus(ExpertStatus.WAITING_FOR_CONFIRMATION)
                .build();
        notValidExpert=Expert.builder()
                .firstname("notValid")
                .lastname("notValid")
                .username("notValid")
                .password("SDF3")
                .cardBalance(25)
                .email("gmail.com")
                .nationCode("66200")
                .registerDate(LocalDate.now())
                .expertStatus(ExpertStatus.WAITING_FOR_CONFIRMATION)
                .build();
    }
    @Test
    @Order(1)
    void ThrowsNotFoundExceptionFindByUsernameTest() {
        assertThrows(NotFoundException.class, () -> expertService.findByUsername("nonexistentUser"));
    }
    @Test
    @Order(2)
    void ThrowsNotFoundExceptionFindByIdTest() {
        assertThrows(NotFoundException.class, () -> expertService.findById(20800l));
    }
    @Test
    @Order(3)
    void signUpCorrect() {
        Expert expert=expertService.registerExpert(currentExpert,"C:\\Users\\Surena\\Downloads\\Documents\\file_example_JPG_100kB.jpg");
        assertEquals(expert.getEmail(), expertService.findById(expert.getId()).getEmail());
    }
    @Test
    @Order(4)
    void NotFoundExceptionConfirmExpertTest(){
        assertThrows(NotFoundException.class, () -> expertService.confirmExpert("notFound"));
    }
    @Test
    @Order(4)
    void confirmExpertTest(){
        expertService.confirmExpert("expert123");
        assertEquals(expertService.findByUsername("expert123").getExpertStatus(), ExpertStatus.CONFIRMED);
    }
    @Test
    @Order(4)
    void ConfirmationExceptionConfirmExpertTest(){
        assertThrows(ConfirmationException.class, () -> expertService.confirmExpert("expert123"));
    }

    ////////

    @Test
    @Order(4)
    void NotFoundExceptionUpdateScoreTest(){
        assertThrows(NotFoundException.class, () -> expertService.updateScore(4.0,"notFound"));
    }
    @Test
    @Order(4)
    void updateScoreTest(){
        expertService.updateScore(4.0,"expert123");
        assertEquals(expertService.findByUsername("expert123").getExpertStatus(), ExpertStatus.CONFIRMED);
    }
    @Test
    @Order(4)
    void InvalidEntityExceptionUpdateScoreTest(){
        assertThrows(InvalidEntityException.class, () -> expertService.updateScore(-1,"expert123"));
    }

    @Test
    @Order(4)
    void DuplicateInformationExceptionSignUp(){
        assertThrows(DuplicateInformationException.class, ()-> expertService.registerExpert(currentExpert,"C:\\Users\\Surena\\Downloads\\Documents\\file_example_JPG_100kB.jpg"));
    }
    @Test
    @Order(5)
    void InvalidEntityExceptionSignUp(){
        assertThrows(InvalidEntityException.class, ()-> expertService.registerExpert(notValidExpert,"C:\\Users\\Surena\\Downloads\\Documents\\file_example_JPG_100kB.jpg"));
    }

    @Test
    @Order(6)
    void findByIdTest() {
        Expert expert =expertService.findById(20l);
        assertEquals("expert123", expert.getUsername());
    }

    @Test
    @Order(7)
    void findByUsernameTest() {
        Expert expert=expertService.findByUsername("expert123");
        assertEquals(20l, expert.getId());
    }

    @Test
    @Order(8)
    void deleteByIdTest(){
        expertService.deleteById(20l);
        assertThrows(NotFoundException.class, () -> expertService.findById(20l));
    }

    @Test
    @Order(9)
    void signUpCorrect2() {
        Expert expert=expertService.registerExpert(currentExpert,"C:\\Users\\Surena\\Downloads\\Documents\\file_example_JPG_100kB.jpg");
        assertEquals(expert.getEmail(), expertService.findById(expert.getId()).getEmail());
    }

    @Order(10)
    @Test
    void updatePasswordTest(){
        expertService.updatePassword("expert123","SDFfg@123","SDFfg@125","SDFfg@125");
        assertEquals("SDFfg@125",expertService.findByUsername("expert123").getPassword());
    }

    @Order(11)
    @Test
    void NotValidPasswordExceptionUpdatePasswordTest(){
        assertThrows(NotValidPasswordException.class, () -> expertService.updatePassword("expert123","SDFf124","SDFfg@1285","SDFfg@125"));
    }
    @Test
    @Order(12)
    void deleteByUsernameTest(){
        expertService.deleteByUsername("expert123");
        assertThrows(NotFoundException.class, () -> expertService.findByUsername("expert123"));
    }


}

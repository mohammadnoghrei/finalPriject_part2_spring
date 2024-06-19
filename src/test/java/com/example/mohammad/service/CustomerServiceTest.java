package com.example.mohammad.service;


import com.example.mohammad.exception.DuplicateInformationException;
import com.example.mohammad.exception.InvalidEntityException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.exception.NotValidPasswordException;
import com.example.mohammad.model.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceTest{

    @Autowired
    private CustomerService customerService;

    public static Customer currentCustomer;
    public static Customer duplicateCustomer;
    public static Customer notFoundCustomer;
    public static Customer notValidCustomer;

    @BeforeAll
    public static void createCustomer(){
        currentCustomer=Customer.builder()
                .firstname("mohammad")
                .lastname("mohammadi")
                .username("mohammad123")
                .password("SDFfg@123")
                .cardBalance(25)
                .email("mohammad@gmail.com")
                .nationCode("3920066200")
                .registerDate(LocalDate.now())
                .build();
        duplicateCustomer=Customer.builder()
                .firstname("mohammad")
                .lastname("mohammadi")
                .username("mohammad123")
                .password("SDFfg@123")
                .cardBalance(25)
                .email("mohammad@gmail.com")
                .nationCode("3920066200")
                .registerDate(LocalDate.now())
                .build();
        notFoundCustomer=Customer.builder()
                .firstname("notFound")
                .lastname("notfound")
                .username("notfound")
                .password("SDFfg@123")
                .cardBalance(25)
                .email("notfound@gmail.com")
                .nationCode("3920066200")
                .registerDate(LocalDate.now())
                .build();
        notValidCustomer=Customer.builder()
                .firstname("notValid")
                .lastname("notValid")
                .username("notValid")
                .password("SDF3")
                .cardBalance(25)
                .email("gmail.com")
                .nationCode("66200")
                .registerDate(LocalDate.now())
                .build();
    }
    @Test
    @Order(1)
    void ThrowsNotFoundExceptionFindByUsernameTest() {
         assertThrows(NotFoundException.class, () -> customerService.findByUsername("nonexistentUser"));
    }
    @Test
    @Order(2)
    void ThrowsNotFoundExceptionFindByIdTest() {
        assertThrows(NotFoundException.class, () -> customerService.findById(20800l));
    }
    @Test
    @Order(3)
    void signUpCorrect() {
        Customer customer=customerService.registerCustomer(currentCustomer);
        assertEquals(customer.getEmail(), customerService.findById(customer.getId()).getEmail());
    }
    @Test
    @Order(4)
    void DuplicateInformationExceptionSignUp(){
        assertThrows(DuplicateInformationException.class, ()-> customerService.registerCustomer(currentCustomer));
    }
    @Test
    @Order(5)
    void InvalidEntityExceptionSignUp(){
        assertThrows(InvalidEntityException.class, ()-> customerService.registerCustomer(notValidCustomer));
    }

    @Test
    @Order(6)
    void findByIdTest() {
        Customer customer=customerService.findById(12l);
        assertEquals("mohammad123", customer.getUsername());
    }

    @Test
    @Order(7)
    void findByUsernameTest() {
        Customer customer=customerService.findByUsername("mohammad123");
        assertEquals(12l, customer.getId());
    }

    @Test
    @Order(8)
    void deleteByIdTest(){
        customerService.deleteById(12l);
        assertThrows(NotFoundException.class, () -> customerService.findById(10l));
    }

    @Test
    @Order(9)
    void signUpCorrect2() {
        Customer customer=customerService.registerCustomer(currentCustomer);
        assertEquals(customer.getEmail(), customerService.findById(customer.getId()).getEmail());
    }


    @Order(10)
    @Test
    void updatePasswordTest(){
        customerService.updatePassword("mohammad123","SDFfg@123","SDFfg@125","SDFfg@125");
        assertEquals("SDFfg@125",customerService.findByUsername("mohammad123").getPassword());
    }

    @Order(11)
    @Test
    void NotValidPasswordExceptionUpdatePasswordTest(){
        assertThrows(NotValidPasswordException.class, () -> customerService.updatePassword("mohammad123","SDFf124","SDFfg@1285","SDFfg@125"));
    }
    @Test
    @Order(12)
    void deleteByUsernameTest(){
        customerService.deleteByUsername("mohammad123");
        assertThrows(NotFoundException.class, () -> customerService.findByUsername("mohammad123"));
    }

}

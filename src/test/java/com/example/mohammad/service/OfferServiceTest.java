package com.example.mohammad.service;

import com.example.mohammad.exception.InvalidEntityException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.model.Customer;
import com.example.mohammad.model.Offer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OfferServiceTest {

    @Autowired
    private OfferService offerService;
    public static Offer currentOffer;
    public static Offer invalidOffer;

    @BeforeAll
    public static void createOffer() {
        currentOffer = Offer.builder()
                .sendOfferDate(LocalDate.now()).startOfferDate(LocalDate.of(2025, 06, 07)).price(1000).endOfferDate(LocalDate.of(2025, 06, 10)).build();
        invalidOffer = Offer.builder()
                .sendOfferDate(LocalDate.now()).startOfferDate(LocalDate.of(2020, 06, 07)).price(1000).endOfferDate(LocalDate.of(2025, 06, 10)).build();

    }

    @Test
    @Order(1)
    void throwsNotFoundExceptionFindByIdTest() {
        assertThrows(NotFoundException.class, () -> offerService.findById(20800L));
    }

    @Test
    @Order(2)
    void invalidEntityExceptionSaveTest2() {
        assertThrows(InvalidEntityException.class, () -> offerService.saveOffer(invalidOffer, 2L, "madexpert123"));
    }

    @Test
    @Order(3)
    void saveOffer() {
    Offer offer = offerService.saveOffer(currentOffer,2L,"madexpert123");
    assertTrue(offer.getId()>0);
}

    @Test
    @Order(3)
    void confirmOffer() {
        offerService.confirmOffer(1L);
        assertTrue(offerService.findById(1L).isConfirmed());}
    @Test
    @Order(4)
    void invalidEntityExceptionConfirmOffer() {
        assertThrows(InvalidEntityException.class, () -> offerService.confirmOffer(1L));

    }
}

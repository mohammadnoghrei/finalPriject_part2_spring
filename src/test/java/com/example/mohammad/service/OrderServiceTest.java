package com.example.mohammad.service;

import com.example.mohammad.exception.InvalidEntityException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.exception.StatusException;
import com.example.mohammad.model.OrderStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    @Order(1)
    void throwsNotFoundExceptionFindByIdTest() {
        assertThrows(NotFoundException.class, () -> orderService.findById(20800L));
    }
    @Test
    @Order(2)
    void invalidEntityExceptionSaveTest() {
        assertThrows(InvalidEntityException.class, () -> orderService.saveOrder("madCustomer123",10, LocalDate.of(2025,06,04),"langrood","carWash"));
    }
    @Test
    @Order(3)
    void invalidEntityExceptionSaveTest2() {
        assertThrows(InvalidEntityException.class, () -> orderService.saveOrder("madCustomer123",1000, LocalDate.of(2022,06,04),"langrood","carWash"));
    }
    @Test
    @Order(4)
    void saveTest() {
       com.example.mohammad.model.Order order= orderService.saveOrder("madCustomer123",1000, LocalDate.of(2025,06,04),"langrood","carWash");
        assertTrue(order.getId()>0);

    }

    @Test
    @Order(5)
    void notFoundExceptionUpdateOrderStatusToStart(){
        assertThrows(NotFoundException.class, () -> orderService.updateOrderStatusToStart(20800L));
    }

    @Test
    @Order(6)
    void statusExceptionUpdateOrderStatusToStart(){
        assertThrows(StatusException.class, () -> orderService.updateOrderStatusToStart(3L));
    }
    @Test
    @Order(7)
   void updateOrderStatusToStart(){
         orderService.updateOrderStatusToStart(2L);
        assertEquals(OrderStatus.START_SERVICE,orderService.findById(2L).getOrderStatus());
    }

    @Test
    @Order(8)
    void notFoundExceptionUpdateOrderStatusToWaitingForChooseExpert(){
        assertThrows(NotFoundException.class, () -> orderService.updateOrderStatusToWaitingForChooseExpert(20800L));
    }

    @Test
    @Order(9)
    void statusExceptionUpdateOrderStatusToWaitingForChooseExpert(){
        assertThrows(StatusException.class, () -> orderService.updateOrderStatusToWaitingForChooseExpert(3L));
    }

    @Test
    @Order(10)
    void updateOrderStatusToWaitingForChooseExpert(){
        orderService.updateOrderStatusToWaitingForChooseExpert(2L);
        assertEquals(OrderStatus.WAITING_FOR_CHOOSE_EXPERT,orderService.findById(2L).getOrderStatus());
    }
    @Test
    @Order(11)
    void statusExceptionUpdateOrderStatusToDuneAndSaveRateAndDescription(){
        assertThrows(StatusException.class, () -> orderService.updateOrderStatusToDuneAndSaveRateAndDescription(3L,"good ",5));
    }
    @Test
    @Order(12)
    void   invalidEntityExceptionUpdateOrderStatusToDuneAndSaveRateAndDescription(){
        assertThrows(InvalidEntityException.class, () -> orderService.updateOrderStatusToDuneAndSaveRateAndDescription(3L,"good ",7));
    }
    @Test
    @Order(13)
    void   updateOrderStatusToDuneAndSaveRateAndDescription(){
        orderService.updateOrderStatusToDuneAndSaveRateAndDescription(3L,"good ",4);
        assertEquals(OrderStatus.DONE_SERVICE,orderService.findById(3L).getOrderStatus());

    }



}

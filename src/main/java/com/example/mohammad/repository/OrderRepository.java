package com.example.mohammad.repository;

import com.example.mohammad.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {

    List<Order> findAllByOrderStatusOrOrderStatusAndSubServices(OrderStatus orderStatus1, OrderStatus orderStatus2, SubServices subServices);
    List<Order> findAllByExpertAndOrderStatus(Expert expert, OrderStatus orderStatus);
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query("UPDATE Order c SET c.orderStatus =  :status WHERE c.id = :id")
    void updateOrderStatus(OrderStatus status, long id);
}

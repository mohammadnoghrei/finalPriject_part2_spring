package com.example.mohammad.repository;


import com.example.mohammad.model.Offer;

import com.example.mohammad.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    List<Offer> findAllByOrder(Order order);
}

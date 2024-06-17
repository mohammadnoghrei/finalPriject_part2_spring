package com.example.mohammad.repository;


import com.example.mohammad.model.Expert;
import com.example.mohammad.model.SubServiceExpert;
import com.example.mohammad.model.SubServices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SubServiceExpertRepository extends JpaRepository<SubServiceExpert,Long> {
    Optional<SubServiceExpert> findBySubServicesAndExpert(SubServices subServices, Expert expert);



}

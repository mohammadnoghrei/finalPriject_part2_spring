package com.example.mohammad.repository;


import com.example.mohammad.model.Services;
import com.example.mohammad.model.SubServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Services,Long> {

    Optional<Services> findByServiceName(String name);

}

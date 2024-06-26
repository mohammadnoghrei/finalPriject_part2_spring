package com.example.mohammad.repository;


import com.example.mohammad.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PersonRepository  extends JpaRepository<Person,Long> {
    Optional<Person> findByUsername(String username);
}

package com.example.mohammad.repository;


import com.example.mohammad.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface WalletRepository extends JpaRepository<Wallet,Long> {
}

package com.example.mohammad.service;

import com.example.mohammad.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class WalletService  {
    private final WalletRepository walletRepository;
}

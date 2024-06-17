package com.example.mohammad.service;

import com.example.mohammad.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService  {
    private final CommentRepository commentRepository;
}

package com.example.demo.controller;

import com.example.demo.exception.Forbidden;
import com.example.demo.exception.NotFound;
import com.example.demo.exception.Unauthorized;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({NotFound.class})
    public ResponseEntity<Void> notFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({Unauthorized.class})
    public ResponseEntity<Void> unauthorized() {
        return ResponseEntity.status(401).build();
    }

    @ExceptionHandler({Forbidden.class})
    public ResponseEntity<Void> forbidden() {
        return ResponseEntity.status(403).build();
    }
}

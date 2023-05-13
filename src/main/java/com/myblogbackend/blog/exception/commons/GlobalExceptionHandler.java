package com.myblogbackend.blog.exception.commons;


import com.myblogbackend.blog.response.ResponseEntityBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BlogRuntimeException.class)
    public ResponseEntity<?> handler(BlogRuntimeException e, HttpServletRequest request) {
        return ResponseEntityBuilder
                .getBuilder()
                .setCode(Integer.parseInt(e.getCode()))
                .setMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handler(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return ResponseEntityBuilder
                .getBuilder()
                .setCode(e.hashCode())
                .setMessage(e.getMessage())
                .build();
    }
}
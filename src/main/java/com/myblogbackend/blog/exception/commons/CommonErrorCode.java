package com.myblogbackend.blog.exception.commons;

import org.springframework.http.HttpStatus;

public interface CommonErrorCode {
    String code();

    HttpStatus status();

    String message();
}
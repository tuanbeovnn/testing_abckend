package com.myblogbackend.blog.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public final class BlogExceptionResponse {
    private final int errorCode;
    private final String errorMessage;
    @JsonIgnore
    private final HttpStatus httpStatus;

    public BlogExceptionResponse(final int errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = HttpStatus.OK;
    }

    public int getCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public BlogExceptionResponse(final int code, final String message, final HttpStatus httpStatus) {
        this.errorCode = code;
        this.errorMessage = message;
        this.httpStatus = httpStatus;
    }
}

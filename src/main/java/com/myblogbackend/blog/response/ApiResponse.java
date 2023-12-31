package com.myblogbackend.blog.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;

    public ApiResponse(final boolean success, final String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

}
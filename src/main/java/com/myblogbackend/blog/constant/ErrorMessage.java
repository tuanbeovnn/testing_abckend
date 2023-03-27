package com.myblogbackend.blog.constant;


import com.myblogbackend.blog.exception.BlogExceptionResponse;

public enum ErrorMessage {
    FILE_IS_EMPTY(400, "error.file-empty"),
    FILE_IS_NOT_IMAGE_TYPE(402, "error.file-is-not-image"),
    ACTION_IS_NULL(405, "error.action-not-null"),
    SAVE_FAIL(406, "error.save-fail"),
    MAX_QUANTITY_UPLOAD(407, "error.max-file-request"),
    NOT_FOUND(404, "error.not_found"),
    USER_NOT_AUTHORIZATION(401, "error.not_authorization");

    private final BlogExceptionResponse resultCode;

    ErrorMessage(final int code, final String message) {
        this.resultCode = new BlogExceptionResponse(code, message);
    }

    public BlogExceptionResponse getResultCode() {
        return resultCode;
    }

    public int getCode() {
        return resultCode.getCode();
    }

    public String getMessage() {
        return resultCode.getMessage();
    }
}

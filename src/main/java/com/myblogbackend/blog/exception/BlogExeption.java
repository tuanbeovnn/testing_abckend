package com.myblogbackend.blog.exception;

public class BlogExeption extends RuntimeException {
    private BlogExceptionResponse resultCode;

    public BlogExeption(final String message, final BlogExceptionResponse resultCode) {
        super(message);
        this.resultCode = resultCode;
    }

    public BlogExeption(final BlogExceptionResponse resultCode) {
        super(resultCode.toString());
        this.resultCode = resultCode;
    }

    public BlogExeption(final BlogExceptionResponse resultCode, final Throwable throwable) {
        super(throwable);
        this.resultCode = resultCode;
    }

    public String getMessage() {
        String var10000 = this.resultCode.toString();
        return "BlogExceptionResponse is " + var10000 + " \n " + super.getMessage();
    }

    public BlogExceptionResponse getPezzieExceptionResponse() {
        return this.resultCode;
    }

    public void setPezzieExceptionResponse(final BlogExceptionResponse resultCode) {
        this.resultCode = resultCode;
    }
}

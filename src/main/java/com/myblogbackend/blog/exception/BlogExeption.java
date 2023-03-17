package com.myblogbackend.blog.exception;

public class BlogExeption extends RuntimeException {
    protected BlogExceptionResponse resultCode;

    public BlogExeption(String message) {
        super(message);
    }

    public BlogExeption(BlogExceptionResponse resultCode) {
        super(resultCode.toString());
        this.resultCode = resultCode;
    }

    public BlogExeption(BlogExceptionResponse resultCode, Throwable throwable) {
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

    public void setPezzieExceptionResponse(BlogExceptionResponse resultCode) {
        this.resultCode = resultCode;
    }
}

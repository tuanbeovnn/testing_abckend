package com.myblogbackend.blog.exception;

public class LangException extends BlogExeption {
    private String lang;
    private Object[] translateArgs;

    public LangException(final BlogExceptionResponse resultCode, final Object... translateArgs) {
        super(resultCode);
        this.translateArgs = translateArgs;
    }

    public LangException(final BlogExceptionResponse resultCode, final Throwable throwable, final Object... translateArgs) {
        super(resultCode, throwable);
        this.translateArgs = translateArgs;
    }

    public LangException(final String lang, final BlogExceptionResponse resultCode) {
        super(resultCode);
        this.lang = lang;
    }

    public LangException(final String lang, final BlogExceptionResponse resultCode, final Throwable throwable) {
        super(resultCode, throwable);
        this.lang = lang;
    }

    public LangException(final BlogExceptionResponse resultCode, final Throwable throwable) {
        super(resultCode, throwable);
    }

    public LangException(final String lang, final BlogExceptionResponse resultCode, final Object... translateArgs) {
        super(resultCode);
        this.lang = lang;
        this.translateArgs = translateArgs;
    }

    public LangException(final String lang, final BlogExceptionResponse resultCode, final Throwable throwable, final Object... translateArgs) {
        super(resultCode, throwable);
        this.lang = lang;
        this.translateArgs = translateArgs;
    }

    public String getMessage() {
        String var10000 = super.getMessage();
        return var10000 + ", instance: " + this.toString();
    }

    public void setTranslateArgs(final Object... translateArgs) {
        this.translateArgs = translateArgs;
    }

    public String getLang() {
        return this.lang;
    }

    public Object[] getTranslateArgs() {
        return this.translateArgs;
    }
}

package com.myblogbackend.blog.exception;


import com.myblogbackend.blog.constant.ErrorMessage;

public class BlogLangException extends LangException {

    public BlogLangException(final BlogExceptionResponse resultCode, final Object... args) {
        super(resultCode);
        Object[] newArgs = new Object[args.length + 1];
        System.arraycopy(args, 0, newArgs, 1, args.length);
        newArgs[0] = resultCode.getCode() + "";
        setTranslateArgs(newArgs);
    }

    public BlogLangException(final ErrorMessage resultCodes, final Object... args) {
        this(resultCodes.getResultCode(), args);
    }
}

package com.studioyunseul.noticeduri.exception;

public class MemberNotFound extends RuntimeException {
    public MemberNotFound() {
    }

    public MemberNotFound(String message) {
        super(message);
    }

    public MemberNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotFound(Throwable cause) {
        super(cause);
    }

    public MemberNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

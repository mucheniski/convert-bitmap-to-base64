package com.example.convertbitmaptobase64.application.exception;

public class GeneralApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    GeneralApiException(String message) {
        super(message);
    }

    public GeneralApiException(String message, Throwable cause) {
        super(message, cause);
    }

}

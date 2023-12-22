package org.kosta.starducks.commons;

import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

public class CommonException extends RuntimeException {
    protected static ResourceBundle bundleError;
    protected HttpStatus httpStatus;


    static {
        bundleError = ResourceBundle.getBundle("messages.errors");
    }

    public CommonException(String message) {
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CommonException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

}

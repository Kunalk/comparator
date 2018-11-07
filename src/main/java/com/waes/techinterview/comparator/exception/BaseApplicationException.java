package com.waes.techinterview.comparator.exception;

/**
 * Created by Kunal on 07-11-2018.
 */
public class BaseApplicationException extends RuntimeException {

    String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

package com.stream.app.exceptions;

import com.stream.app.enums.ErrorKey;

public class AppFileCannotCreateException extends AppException {

    public AppFileCannotCreateException(String message, ErrorKey errorKey) {
        super(message, errorKey);
    }
}

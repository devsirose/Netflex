package com.stream.app.exceptions;

import com.stream.app.enums.ErrorKey;


public class AppFileNotFoundException extends AppException{

    public AppFileNotFoundException(String message, ErrorKey errorKey) {
        super(message, errorKey);
    }
}

package com.stream.app.exceptions;

import com.stream.app.enums.ErrorKey;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class AppException extends RuntimeException {
    protected String message;
    protected ErrorKey errorKey;

    public String getErrorKey() {
        return errorKey.getCode();
    }
}

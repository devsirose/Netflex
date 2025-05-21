package com.stream.app.enums;

public enum ErrorKey {
    VIDEO_NOT_FOUND("1001"),
    FILE_CAN_NOT_CREATE("1002");


    private final String code;

    ErrorKey(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

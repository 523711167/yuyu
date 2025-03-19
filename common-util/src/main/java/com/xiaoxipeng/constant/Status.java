package com.xiaoxipeng.constant;

public enum Status {

    SUCCESS(0000, "success"),

    UNKNOWN_ERROR(1000, "An error occurred");

    private final Integer code;

    private final String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

package com.xiaoxipeng.common.constant;

public enum Status {

    SUCCESS(200, "success"),

    USER_ERROR(2000, "用户相关的错误"),

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

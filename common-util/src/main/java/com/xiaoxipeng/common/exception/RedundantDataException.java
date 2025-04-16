package com.xiaoxipeng.common.exception;

public class RedundantDataException extends YuyuException {

    public RedundantDataException(String message) {
        super(message, null);
    }

    public RedundantDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedundantDataException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}

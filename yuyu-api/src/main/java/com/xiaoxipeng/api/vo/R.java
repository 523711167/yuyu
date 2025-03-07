package com.xiaoxipeng.api.vo;

import com.xiaoxipeng.api.constant.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class R<T> {

    private String requestId;

    private Integer code;

    private String msg;

    private T data;

    public static <T> R<T> fromS(S<T> data) {
        R<T> tr = new R<>();
        tr.setCode(data.getCode());
        tr.setMsg(data.getError());
        tr.setData(data.getData());
        return tr;
    }

    public static <T> R<T> success() {
        R<T> tr = new R<>();
        tr.setCode(Status.SUCCESS.getCode());
        tr.setMsg(Status.SUCCESS.getMessage());
        tr.setData(null);
        return tr;
    }

    public static <T> R<T> success(T data) {
        R<T> tr = new R<>();
        tr.setCode(Status.SUCCESS.getCode());
        tr.setMsg(Status.SUCCESS.getMessage());
        tr.setData(data);
        return tr;
    }

    public static <T> R<T> fail() {
        R<T> tr = new R<>();
        tr.setCode(Status.UNKNOWN_ERROR.getCode());
        tr.setMsg(Status.UNKNOWN_ERROR.getMessage());
        return tr;
    }
}

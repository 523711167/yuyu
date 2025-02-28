package com.xiaoxipeng.yuyu.vo;

import lombok.Getter;
import lombok.Setter;

import static com.xiaoxipeng.yuyu.constant.Status.SUCCESS;
import static com.xiaoxipeng.yuyu.constant.Status.UNKNOWN_ERROR;

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
        tr.setCode(SUCCESS.getCode());
        tr.setMsg(SUCCESS.getMessage());
        tr.setData(null);
        return tr;
    }

    public static <T> R<T> success(T data) {
        R<T> tr = new R<>();
        tr.setCode(SUCCESS.getCode());
        tr.setMsg(SUCCESS.getMessage());
        tr.setData(data);
        return tr;
    }

    public static <T> R<T> fail() {
        R<T> tr = new R<>();
        tr.setCode(UNKNOWN_ERROR.getCode());
        tr.setMsg(UNKNOWN_ERROR.getMessage());
        return tr;
    }
}

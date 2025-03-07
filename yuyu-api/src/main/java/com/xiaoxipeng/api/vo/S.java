package com.xiaoxipeng.api.vo;

import com.xiaoxipeng.api.constant.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class S<T> {


    private Boolean isError;

    private String error;

    private Integer code;

    private T data;

    public static <T> S<T> success() {
        return new Builder<T>().isError(false).code(0).build();
    }

    public static <T> S<T> success(T data) {
        return new Builder<T>().isError(false).code(0).data(data).build();
    }

    public static <T> S<T> fail(Status status) {
        return new Builder<T>().isError(true).code(status.getCode()).error(status.getMessage()).build();
    }


    public static class Builder<T> {
        private Boolean isError;
        private String error;
        private Integer code;
        private T data;

        public Builder<T> isError(Boolean isError) {
            this.isError = isError;
            return this;
        }

        public Builder<T> error(String error) {
            this.error = error;
            return this;
        }

        public Builder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public S<T> build() {
            S<T> s = new S<>();
            s.setIsError(this.isError);
            s.setError(this.error);
            s.setCode(this.code);
            s.setData(this.data);
            return s;
        }
    }

}

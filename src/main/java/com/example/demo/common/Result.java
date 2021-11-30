package com.example.demo.common;

import lombok.Data;

@Data
public class Result<T> {
    private boolean flag;
    private Integer code;
    private String message;
    private T body;

    public Result(boolean flag, Integer code, String message, T body) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.body = body;
    }
}

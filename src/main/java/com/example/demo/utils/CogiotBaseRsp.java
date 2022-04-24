package com.example.demo.utils;

import lombok.Data;

@Data
public class CogiotBaseRsp<R> {
    protected int rtnCode;
    protected String message;
    protected R data;

    public CogiotBaseRsp() {
        this.rtnCode = ErrorCode.SERVICE_OK.getCode();
        this.message = ErrorCode.SERVICE_OK.getMsg();
    }

    public CogiotBaseRsp(ErrorCode errorCode) {
        this.rtnCode = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public CogiotBaseRsp(Integer errorCode, String errorMsg) {
        this.rtnCode = errorCode;
        this.message = errorMsg;
    }

    public CogiotBaseRsp(R data) {
        this.message = ErrorCode.SERVICE_OK.getMsg();
        this.rtnCode = ErrorCode.SERVICE_OK.getCode();
        this.message = ErrorCode.SERVICE_OK.getMsg();
        this.data = data;
    }

    public CogiotBaseRsp(byte success, String message, R data) {
        this.rtnCode = success;
        this.message = message;
        this.data = data;
    }

    public static <R> CogiotBaseRsp<R> success(R data) {
        return new CogiotBaseRsp<R>(data);
    }


    public static <R> CogiotBaseRsp<R> success() {
        return new CogiotBaseRsp<R>();
    }

    public static <R> CogiotBaseRsp<R> failure(ErrorCode errorCode) {
        return new CogiotBaseRsp<>(errorCode);
    }

    public static <R> CogiotBaseRsp<R> failure(Integer errorCode, String errorMsg) {
        return new CogiotBaseRsp<>(errorCode, errorMsg);
    }

    public static <R> CogiotBaseRsp<R> failure(Exception e) {
        return new CogiotBaseRsp<>(ErrorCode.SYSTEM_ERROR);
    }


}

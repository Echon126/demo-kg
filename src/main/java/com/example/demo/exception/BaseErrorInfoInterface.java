package com.example.demo.exception;

/**
 * @author zhangwenjie
 */
public interface BaseErrorInfoInterface {
    /** 错误码*/
    int getResultCode();

    /** 错误描述*/
    String getResultMsg();
}

package com.example.demo.exception;

/**
 * @author zhangwenjie
 */
public interface BaseErrorInfoInterface {
    /** 错误码*/
    String getResultCode();

    /** 错误描述*/
    String getResultMsg();
}

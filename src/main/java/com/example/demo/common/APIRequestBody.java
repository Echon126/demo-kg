package com.example.demo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangwenjie
 */
@Data
public class APIRequestBody<T> implements Serializable {
    private static final long serialVersionUID = 9197172154390907805L;

    private String client;

    private String timeStamp;

    private T data;
}

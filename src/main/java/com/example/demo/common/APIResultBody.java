package com.example.demo.common;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.exception.BaseErrorInfoInterface;
import com.example.demo.exception.CommonEnum;


/**
 * @author zhangwenjie
 */
public class APIResultBody<T> {
    /**
     * 响应代码
     */
    private int rtnCode;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private T data;

    public APIResultBody() {
    }

    public APIResultBody(BaseErrorInfoInterface errorInfo) {
        this.rtnCode = errorInfo.getResultCode();
        this.message = errorInfo.getResultMsg();
    }

    public int getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(int rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功
     *
     * @return
     */
    public static <T> APIResultBody<T> success() {
        return success(null);
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static <T> APIResultBody<T> success(T data) {
        APIResultBody<T> rb = new APIResultBody<>();
        rb.setRtnCode(CommonEnum.SUCCESS.getResultCode());
        rb.setMessage(CommonEnum.SUCCESS.getResultMsg());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static <T> APIResultBody<T> error(BaseErrorInfoInterface errorInfo) {
        APIResultBody<T> rb = new APIResultBody<>();
        rb.setRtnCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static <T> APIResultBody<T> error(int code, String message) {
        APIResultBody<T> rb = new APIResultBody<>();
        rb.setRtnCode(code);
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static <T> APIResultBody<T> error(String message) {
        APIResultBody<T> rb = new APIResultBody<>();
        rb.setRtnCode(-1);
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

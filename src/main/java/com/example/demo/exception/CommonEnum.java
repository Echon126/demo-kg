package com.example.demo.exception;


/**
 * @author zhangwenjie
 */

public enum CommonEnum implements BaseErrorInfoInterface {
    /**
     * status desc ,code
     */
    SUCCESS(200, "成功!"),
    BODY_NOT_EMPTY(300, "提交参数不能为空!"),
    BODY_NOT_MATCH(400, "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401, "请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),
    DAT_TRUNCATION(603, "数据字段过长!"),

    /**
     * 业务错误代号及描述
     */
    ;

    /**
     * 错误码
     */
    private final int resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    CommonEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}

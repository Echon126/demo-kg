package com.example.demo.rabbitmq;

import com.example.demo.common.APIResultBody;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangxing
 * @version 2020/6/25 18:19 Administrator
 */
public interface OmsPortalOrderService {
    /**
     * 根据提交信息生成订单
     */
    @Transactional
    APIResultBody generateOrder(Long orderParam);
    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);

}
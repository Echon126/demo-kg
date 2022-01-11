package com.example.demo.rabbitmq;

import com.example.demo.common.APIResultBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangxing
 * @version 2020/6/25 18:25 Administrator
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    private static Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);
    @Autowired
    private CancelOrderSender cancelOrderSender;

    /**
     * 根据提交信息生成订单
     *
     * @param orderParam
     */
    @Override
    public APIResultBody generateOrder(Long orderParam) {
        //todo 执行一系类下单操作，具体参考mall项目
        LOGGER.info("process generateOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(orderParam);
        return APIResultBody.success("下单成功");
    }

    /**
     * 取消单个超时订单
     *
     * @param orderId
     */
    @Override
    public void cancelOrder(Long orderId) {
        //todo 执行一系类取消订单操作，具体参考mall项目
        LOGGER.info("process cancelOrder orderId:{}", orderId);
    }

    /**
     * 订单超时时间，假设为10分钟
     * 10 * 60 * 1000
     */
    private Long DELAY_TIME = 600000L;

    private void sendDelayMessageCancelOrder(Long orderId) {
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, orderId);
    }
}
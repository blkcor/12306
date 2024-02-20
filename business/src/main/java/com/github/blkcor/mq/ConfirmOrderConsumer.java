package com.github.blkcor.mq;

import com.alibaba.fastjson.JSON;
import com.github.blkcor.dto.ConfirmOrderDto;
import com.github.blkcor.req.ConfirmOrderDoReq;
import com.github.blkcor.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RocketMQMessageListener(topic = "CONFIRM_ORDER", consumerGroup = "default")
public class ConfirmOrderConsumer implements RocketMQListener<MessageExt> {
    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderConsumer.class);

    @Resource
    private ConfirmOrderService confirmOrderService;

    @Override
    public void onMessage(MessageExt messageExt) {
        LOG.info("接收到确认订单消息:{}", new String(messageExt.getBody()));
        //parse to ConfirmOrderDoReq
        ConfirmOrderDto confirmOrderDto = JSON.parseObject(messageExt.getBody(), ConfirmOrderDto.class);
        confirmOrderService.doConfirmOrder(confirmOrderDto);
    }
}

package com.github.blkcor.service.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.github.blkcor.context.LoginMemberContext;
import com.github.blkcor.dto.ConfirmOrderDto;
import com.github.blkcor.entity.ConfirmOrder;
import com.github.blkcor.enums.ConfirmOrderStatusEnum;
import com.github.blkcor.enums.RocketMQTopicEnum;
import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.exception.BusinessExceptionEnum;
import com.github.blkcor.mapper.ConfirmOrderMapper;
import com.github.blkcor.req.ConfirmOrderDoReq;
import com.github.blkcor.service.BeforeConfirmOrderService;
import com.github.blkcor.service.SkTokenService;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class BeforeConfirmOrderServiceImpl implements BeforeConfirmOrderService {
    private final static Logger LOG = LoggerFactory.getLogger(BeforeConfirmOrderServiceImpl.class);
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;
    @Resource
    private SkTokenService skTokenService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${spring.profiles.active}")
    private String env;
    @SentinelResource(value = "beforeDoConfirmOrder", blockHandler = "beforeDoConfirmOrderBlockHandler")
    @Override
    public void beforeDoConfirmOrder(ConfirmOrderDoReq confirmOrderSaveReq) {
        confirmOrderSaveReq.setMemberId(LoginMemberContext.getId());
        if(!env.equals("dev")){
            /*
             * 校验验证码
             */
            String verifyCode = stringRedisTemplate.opsForValue().get(confirmOrderSaveReq.getVerifyCodeToken());
            LOG.info("从redis中获取验证码:{}", verifyCode);
            if (ObjectUtil.isNull(verifyCode)) {
                LOG.info("验证码已经过期");
                throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_VERIFY_CODE_EXPIRE);
            }
            if (!verifyCode.equalsIgnoreCase(confirmOrderSaveReq.getVerifyCode())) {
                LOG.info("验证码不正确");
                throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_VERIFY_CODE_ERROR);
            } else {
                //从redis中删除验证码
                stringRedisTemplate.delete(confirmOrderSaveReq.getVerifyCodeToken());
            }
            /*
             * 获取令牌
             */
            Boolean validated = skTokenService.validateToken(confirmOrderSaveReq.getDate(), confirmOrderSaveReq.getTrainCode(), LoginMemberContext.getId());
            if (!validated) {
                throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_SK_TOKEN_FALL);
            }
        }
        //1、保存确认订单表，状态初始化
        ConfirmOrder confirmOrder = new ConfirmOrder();
        confirmOrder.setId(IdUtil.getSnowflake(1, 1).nextId());
        confirmOrder.setMemberId(confirmOrderSaveReq.getMemberId());
        confirmOrder.setDate(confirmOrderSaveReq.getDate());
        confirmOrder.setTrainCode(confirmOrderSaveReq.getTrainCode());
        confirmOrder.setStart(confirmOrderSaveReq.getStart());
        confirmOrder.setEnd(confirmOrderSaveReq.getEnd());
        confirmOrder.setDailyTrainTicketId(confirmOrderSaveReq.getDailyTrainTicketId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        confirmOrder.setCreateTime(DateTime.now());
        confirmOrder.setUpdateTime(DateTime.now());
        confirmOrder.setTickets(JSON.toJSONString(confirmOrderSaveReq.getTickets()));
        confirmOrderMapper.insertSelective(confirmOrder);

        //发送mq排队购票
        ConfirmOrderDto confirmOrderDto = new ConfirmOrderDto();
        confirmOrderDto.setDate(confirmOrderSaveReq.getDate());
        confirmOrderDto.setTrainCode(confirmOrderSaveReq.getTrainCode());
        String message = JSON.toJSONString(confirmOrderDto);
        LOG.info("发送mq排队购票:{}，开始", message);
        rocketMQTemplate.convertAndSend(RocketMQTopicEnum.CONFIRM_ORDER.getCode(), message);
        LOG.info("发送mq排队购票:{}，结束", message);
    }

    /**
     * 限流或降级处理  需包含限流的方法中的所有参数和BlockException参数
     *
     * @param req 限流的方法中的所有参数
     * @param e   BlockException参数
     */
    public void beforeDoConfirmOrderBlockHandler(ConfirmOrderDoReq req, BlockException e) {
        LOG.error("购票接口限流或降级:{}", req);
        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_FLOW_EXCEPTION);
    }
}

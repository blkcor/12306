package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.ConfirmOrder;
import com.github.blkcor.entity.ConfirmOrderExample;
import com.github.blkcor.mapper.ConfirmOrderMapper;
import com.github.blkcor.req.ConfirmOrderQueryReq;
import com.github.blkcor.req.ConfirmOrderSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.ConfirmOrderQueryResp;
import com.github.blkcor.service.ConfirmOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConfirmOrderServiceImpl implements ConfirmOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderServiceImpl.class);
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Override
    public CommonResp<Void> saveConfirmOrder(ConfirmOrderSaveReq confirmOrderSaveReq) {
        ConfirmOrder confirmOrder  = BeanUtil.copyProperties(confirmOrderSaveReq, ConfirmOrder.class);
        if(ObjectUtil.isNull(confirmOrder.getId())){
            confirmOrder.setCreateTime(DateTime.now());
            confirmOrder.setUpdateTime(DateTime.now());
            confirmOrder.setId(IdUtil.getSnowflake(1,1).nextId());
            confirmOrderMapper.insertSelective(confirmOrder);
        }else{
            confirmOrder.setUpdateTime(DateTime.now());
            confirmOrderMapper.updateByPrimaryKeySelective(confirmOrder);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<ConfirmOrderQueryResp>> queryConfirmOrderList(ConfirmOrderQueryReq confirmOrderQueryReq) {
        ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();

        LOG.info("查询页码：{}",confirmOrderQueryReq.getPage());
        LOG.info("查询条数：{}",confirmOrderQueryReq.getSize());

        PageHelper.startPage(confirmOrderQueryReq.getPage(),confirmOrderQueryReq.getSize());
        List<ConfirmOrder> confirmOrders = confirmOrderMapper.selectByExample(confirmOrderExample);
        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrders);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<ConfirmOrderQueryResp> list = BeanUtil.copyToList(confirmOrders, ConfirmOrderQueryResp.class);
        PageResp<ConfirmOrderQueryResp> confirmOrderQueryRespPageResp = new PageResp<>();
        confirmOrderQueryRespPageResp.setList(list);
        confirmOrderQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(confirmOrderQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteConfirmOrder(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}
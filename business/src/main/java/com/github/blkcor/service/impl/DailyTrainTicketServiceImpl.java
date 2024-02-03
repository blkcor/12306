package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.DailyTrainTicket;
import com.github.blkcor.entity.DailyTrainTicketExample;
import com.github.blkcor.mapper.DailyTrainTicketMapper;
import com.github.blkcor.req.DailyTrainTicketQueryReq;
import com.github.blkcor.req.DailyTrainTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainTicketQueryResp;
import com.github.blkcor.service.DailyTrainTicketService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DailyTrainTicketServiceImpl implements DailyTrainTicketService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainTicketServiceImpl.class);
    @Resource
    private DailyTrainTicketMapper dailyTrainTicketMapper;

    @Override
    public CommonResp<Void> saveDailyTrainTicket(DailyTrainTicketSaveReq dailyTrainTicketSaveReq) {
        DailyTrainTicket dailyTrainTicket  = BeanUtil.copyProperties(dailyTrainTicketSaveReq, DailyTrainTicket.class);
        if(ObjectUtil.isNull(dailyTrainTicket.getId())){
            dailyTrainTicket.setCreateTime(DateTime.now());
            dailyTrainTicket.setUpdateTime(DateTime.now());
            dailyTrainTicket.setId(IdUtil.getSnowflake(1,1).nextId());
            dailyTrainTicketMapper.insertSelective(dailyTrainTicket);
        }else{
            dailyTrainTicket.setUpdateTime(DateTime.now());
            dailyTrainTicketMapper.updateByPrimaryKeySelective(dailyTrainTicket);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryDailyTrainTicketList(DailyTrainTicketQueryReq dailyTrainTicketQueryReq) {
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        DailyTrainTicketExample.Criteria criteria = dailyTrainTicketExample.createCriteria();

        LOG.info("查询页码：{}",dailyTrainTicketQueryReq.getPage());
        LOG.info("查询条数：{}",dailyTrainTicketQueryReq.getSize());

        PageHelper.startPage(dailyTrainTicketQueryReq.getPage(),dailyTrainTicketQueryReq.getSize());
        List<DailyTrainTicket> dailyTrainTickets = dailyTrainTicketMapper.selectByExample(dailyTrainTicketExample);
        PageInfo<DailyTrainTicket> pageInfo = new PageInfo<>(dailyTrainTickets);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<DailyTrainTicketQueryResp> list = BeanUtil.copyToList(dailyTrainTickets, DailyTrainTicketQueryResp.class);
        PageResp<DailyTrainTicketQueryResp> dailyTrainTicketQueryRespPageResp = new PageResp<>();
        dailyTrainTicketQueryRespPageResp.setList(list);
        dailyTrainTicketQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(dailyTrainTicketQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteDailyTrainTicket(Long id) {
        dailyTrainTicketMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}
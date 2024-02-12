package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.MemberTicket;
import com.github.blkcor.entity.MemberTicketExample;
import com.github.blkcor.mapper.MemberTicketMapper;
import com.github.blkcor.req.MemberTicketQueryReq;
import com.github.blkcor.req.MemberTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.MemberTicketQueryResp;
import com.github.blkcor.service.MemberTicketService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemberTicketServiceImpl implements MemberTicketService {
    private static final Logger LOG = LoggerFactory.getLogger(MemberTicketServiceImpl.class);
    @Resource
    private MemberTicketMapper memberTicketMapper;

    @Override
    public CommonResp<Void> saveMemberTicket(MemberTicketSaveReq memberTicketSaveReq) {
        MemberTicket memberTicket  = BeanUtil.copyProperties(memberTicketSaveReq, MemberTicket.class);
        if(ObjectUtil.isNull(memberTicket.getId())){
            memberTicket.setCreateTime(DateTime.now());
            memberTicket.setUpdateTime(DateTime.now());
            memberTicket.setId(IdUtil.getSnowflake(1,1).nextId());
            memberTicketMapper.insertSelective(memberTicket);
        }else{
            memberTicket.setUpdateTime(DateTime.now());
            memberTicketMapper.updateByPrimaryKeySelective(memberTicket);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<MemberTicketQueryResp>> queryMemberTicketList(MemberTicketQueryReq memberTicketQueryReq) {
        MemberTicketExample memberTicketExample = new MemberTicketExample();
        MemberTicketExample.Criteria criteria = memberTicketExample.createCriteria();

        LOG.info("查询页码：{}",memberTicketQueryReq.getPage());
        LOG.info("查询条数：{}",memberTicketQueryReq.getSize());

        PageHelper.startPage(memberTicketQueryReq.getPage(),memberTicketQueryReq.getSize());
        List<MemberTicket> memberTickets = memberTicketMapper.selectByExample(memberTicketExample);
        PageInfo<MemberTicket> pageInfo = new PageInfo<>(memberTickets);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<MemberTicketQueryResp> list = BeanUtil.copyToList(memberTickets, MemberTicketQueryResp.class);
        PageResp<MemberTicketQueryResp> memberTicketQueryRespPageResp = new PageResp<>();
        memberTicketQueryRespPageResp.setList(list);
        memberTicketQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(memberTicketQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteMemberTicket(Long id) {
        memberTicketMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}
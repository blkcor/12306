package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.SkToken;
import com.github.blkcor.entity.SkTokenExample;
import com.github.blkcor.mapper.SkTokenMapper;
import com.github.blkcor.req.SkTokenQueryReq;
import com.github.blkcor.req.SkTokenSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.SkTokenQueryResp;
import com.github.blkcor.service.SkTokenService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SkTokenServiceImpl implements SkTokenService {
    private static final Logger LOG = LoggerFactory.getLogger(SkTokenServiceImpl.class);
    @Resource
    private SkTokenMapper skTokenMapper;

    @Override
    public CommonResp<Void> saveSkToken(SkTokenSaveReq skTokenSaveReq) {
        SkToken skToken  = BeanUtil.copyProperties(skTokenSaveReq, SkToken.class);
        if(ObjectUtil.isNull(skToken.getId())){
            skToken.setCreateTime(DateTime.now());
            skToken.setUpdateTime(DateTime.now());
            skToken.setId(IdUtil.getSnowflake(1,1).nextId());
            skTokenMapper.insertSelective(skToken);
        }else{
            skToken.setUpdateTime(DateTime.now());
            skTokenMapper.updateByPrimaryKeySelective(skToken);
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<SkTokenQueryResp>> querySkTokenList(SkTokenQueryReq skTokenQueryReq) {
        SkTokenExample skTokenExample = new SkTokenExample();
        SkTokenExample.Criteria criteria = skTokenExample.createCriteria();

        LOG.info("查询页码：{}",skTokenQueryReq.getPage());
        LOG.info("查询条数：{}",skTokenQueryReq.getSize());

        PageHelper.startPage(skTokenQueryReq.getPage(),skTokenQueryReq.getSize());
        List<SkToken> skTokens = skTokenMapper.selectByExample(skTokenExample);
        PageInfo<SkToken> pageInfo = new PageInfo<>(skTokens);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<SkTokenQueryResp> list = BeanUtil.copyToList(skTokens, SkTokenQueryResp.class);
        PageResp<SkTokenQueryResp> skTokenQueryRespPageResp = new PageResp<>();
        skTokenQueryRespPageResp.setList(list);
        skTokenQueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(skTokenQueryRespPageResp);
    }

    @Override
    public CommonResp<Void> deleteSkToken(Long id) {
        skTokenMapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}
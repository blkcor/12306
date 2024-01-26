package com.github.blkcor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.blkcor.entity.${Domain};
import com.github.blkcor.entity.${Domain}Example;
import com.github.blkcor.mapper.${Domain}Mapper;
import com.github.blkcor.req.${Domain}QueryReq;
import com.github.blkcor.req.${Domain}SaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.${Domain}QueryResp;
import com.github.blkcor.service.${Domain}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ${Domain}ServiceImpl implements ${Domain}Service {
    private static final Logger LOG = LoggerFactory.getLogger(${Domain}ServiceImpl.class);
    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    @Override
    public CommonResp<Void> save${Domain}(${Domain}SaveReq ${domain}SaveReq) {
        ${Domain} ${domain}  = BeanUtil.copyProperties(${domain}SaveReq, ${Domain}.class);
        if(ObjectUtil.isNull(${domain}.getId())){
            ${domain}.setCreateTime(DateTime.now());
            ${domain}.setUpdateTime(DateTime.now());
            ${domain}.setId(IdUtil.getSnowflake(1,1).nextId());
            ${domain}Mapper.insertSelective(${domain});
        }else{
            ${domain}.setUpdateTime(DateTime.now());
            ${domain}Mapper.updateByPrimaryKeySelective(${domain});
        }
        return CommonResp.success(null);
    }

    @Override
    public CommonResp<PageResp<${Domain}QueryResp>> query${Domain}List(${Domain}QueryReq ${domain}QueryReq) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();

        LOG.info("查询页码：{}",${domain}QueryReq.getPage());
        LOG.info("查询条数：{}",${domain}QueryReq.getSize());

        PageHelper.startPage(${domain}QueryReq.getPage(),${domain}QueryReq.getSize());
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}s);

        LOG.info("总条数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<${Domain}QueryResp> list = BeanUtil.copyToList(${domain}s, ${Domain}QueryResp.class);
        PageResp<${Domain}QueryResp> ${domain}QueryRespPageResp = new PageResp<>();
        ${domain}QueryRespPageResp.setList(list);
        ${domain}QueryRespPageResp.setTotal(pageInfo.getTotal());
        return CommonResp.success(${domain}QueryRespPageResp);
    }

    @Override
    public CommonResp<Void> delete${Domain}(Long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
        return CommonResp.success(null);
    }
}
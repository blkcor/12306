package com.github.blkcor.service;

import com.github.blkcor.req.${Domain}QueryReq;
import com.github.blkcor.req.${Domain}SaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.${Domain}QueryResp;


public interface ${Domain}Service {
    /**
     * 保存${DomainNameCN}信息
     * @param ${domain}SaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> save${Domain}(${Domain}SaveReq ${domain}SaveReq);

    /**
     * 查询${DomainNameCN}列表
     * @param ${domain}QueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<${Domain}QueryResp>> query${Domain}List(${Domain}QueryReq ${domain}QueryReq);


    /**
     * 删除${DomainNameCN}信息
     * @param id ${DomainNameCN}id
     * @return 返回结果
     */
    CommonResp<Void> delete${Domain}(Long id);
}
package com.github.blkcor.service;

import com.github.blkcor.req.SkTokenQueryReq;
import com.github.blkcor.req.SkTokenSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.SkTokenQueryResp;


public interface SkTokenService {

    /**
     * 保存秒杀令牌信息
     * @param skTokenSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveSkToken(SkTokenSaveReq skTokenSaveReq);

    /**
     * 查询秒杀令牌列表
     * @param skTokenQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<SkTokenQueryResp>> querySkTokenList(SkTokenQueryReq skTokenQueryReq);


    /**
     * 删除秒杀令牌信息
     * @param id 秒杀令牌id
     * @return 返回结果
     */
    CommonResp<Void> deleteSkToken(Long id);
}
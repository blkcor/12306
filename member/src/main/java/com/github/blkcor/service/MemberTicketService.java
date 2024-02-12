package com.github.blkcor.service;

import com.github.blkcor.req.MemberTicketQueryReq;
import com.github.blkcor.req.MemberTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.MemberTicketQueryResp;


public interface MemberTicketService {

    /**
     * 保存会员车票信息
     * @param memberTicketSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveMemberTicket(MemberTicketSaveReq memberTicketSaveReq);

    /**
     * 查询会员车票列表
     * @param memberTicketQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<MemberTicketQueryResp>> queryMemberTicketList(MemberTicketQueryReq memberTicketQueryReq);


    /**
     * 删除会员车票信息
     * @param id 会员车票id
     * @return 返回结果
     */
    CommonResp<Void> deleteMemberTicket(Long id);
}
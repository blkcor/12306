package com.github.blkcor.service;

import com.github.blkcor.req.DailyTrainTicketQueryReq;
import com.github.blkcor.req.DailyTrainTicketSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainTicketQueryResp;


public interface DailyTrainTicketService {

    /**
     * 保存每日车次余票信息
     * @param dailyTrainTicketSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveDailyTrainTicket(DailyTrainTicketSaveReq dailyTrainTicketSaveReq);

    /**
     * 查询每日车次余票列表
     * @param dailyTrainTicketQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<DailyTrainTicketQueryResp>> queryDailyTrainTicketList(DailyTrainTicketQueryReq dailyTrainTicketQueryReq);


    /**
     * 删除每日车次余票信息
     * @param id 每日车次余票id
     * @return 返回结果
     */
    CommonResp<Void> deleteDailyTrainTicket(Long id);
}
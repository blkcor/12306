package com.github.blkcor.service;

import com.github.blkcor.req.DailyTrainQueryReq;
import com.github.blkcor.req.DailyTrainSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainQueryResp;


public interface DailyTrainService {

    /**
     * 保存每日车次信息
     * @param dailyTrainSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveDailyTrain(DailyTrainSaveReq dailyTrainSaveReq);

    /**
     * 查询每日车次列表
     * @param dailyTrainQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<DailyTrainQueryResp>> queryDailyTrainList(DailyTrainQueryReq dailyTrainQueryReq);


    /**
     * 删除每日车次信息
     * @param id 每日车次id
     * @return 返回结果
     */
    CommonResp<Void> deleteDailyTrain(Long id);
}
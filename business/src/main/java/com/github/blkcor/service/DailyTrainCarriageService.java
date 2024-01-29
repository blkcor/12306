package com.github.blkcor.service;

import com.github.blkcor.req.DailyTrainCarriageQueryReq;
import com.github.blkcor.req.DailyTrainCarriageSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainCarriageQueryResp;


public interface DailyTrainCarriageService {

    /**
     * 保存，每日车次车厢信息
     * @param dailyTrainCarriageSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveDailyTrainCarriage(DailyTrainCarriageSaveReq dailyTrainCarriageSaveReq);

    /**
     * 查询，每日车次车厢列表
     * @param dailyTrainCarriageQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<DailyTrainCarriageQueryResp>> queryDailyTrainCarriageList(DailyTrainCarriageQueryReq dailyTrainCarriageQueryReq);


    /**
     * 删除，每日车次车厢信息
     * @param id ，每日车次车厢id
     * @return 返回结果
     */
    CommonResp<Void> deleteDailyTrainCarriage(Long id);
}
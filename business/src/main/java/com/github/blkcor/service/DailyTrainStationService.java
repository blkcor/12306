package com.github.blkcor.service;

import com.github.blkcor.req.DailyTrainStationQueryReq;
import com.github.blkcor.req.DailyTrainStationSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainStationQueryResp;


public interface DailyTrainStationService {

    /**
     * 保存车次车站信息
     * @param dailyTrainStationSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveDailyTrainStation(DailyTrainStationSaveReq dailyTrainStationSaveReq);

    /**
     * 查询车次车站列表
     * @param dailyTrainStationQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<DailyTrainStationQueryResp>> queryDailyTrainStationList(DailyTrainStationQueryReq dailyTrainStationQueryReq);


    /**
     * 删除车次车站信息
     * @param id 车次车站id
     * @return 返回结果
     */
    CommonResp<Void> deleteDailyTrainStation(Long id);
}
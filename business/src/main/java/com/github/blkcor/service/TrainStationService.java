package com.github.blkcor.service;

import com.github.blkcor.req.TrainStationQueryReq;
import com.github.blkcor.req.TrainStationSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainStationQueryResp;


public interface TrainStationService {

    /**
     * 保存乘客信息
     * @param trainStationSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveTrainStation(TrainStationSaveReq trainStationSaveReq);

    /**
     * 查询乘客列表
     * @param trainStationQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<TrainStationQueryResp>> queryTrainStationList(TrainStationQueryReq trainStationQueryReq);


    /**
     * 删除乘客信息
     * @param id 乘客id
     * @return 返回结果
     */
    CommonResp<Void> deleteTrainStation(Long id);
}
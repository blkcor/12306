package com.github.blkcor.service;

import com.github.blkcor.req.TrainSeatQueryReq;
import com.github.blkcor.req.TrainSeatSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainSeatQueryResp;


public interface TrainSeatService {

    /**
     * 保存车次座位信息
     * @param trainSeatSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveTrainSeat(TrainSeatSaveReq trainSeatSaveReq);

    /**
     * 查询车次座位列表
     * @param trainSeatQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<TrainSeatQueryResp>> queryTrainSeatList(TrainSeatQueryReq trainSeatQueryReq);


    /**
     * 删除车次座位信息
     * @param id 车次座位id
     * @return 返回结果
     */
    CommonResp<Void> deleteTrainSeat(Long id);
}
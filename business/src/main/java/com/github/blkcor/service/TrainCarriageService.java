package com.github.blkcor.service;

import com.github.blkcor.req.TrainCarriageQueryReq;
import com.github.blkcor.req.TrainCarriageSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainCarriageQueryResp;


public interface TrainCarriageService {

    /**
     * 保存车厢信息
     * @param trainCarriageSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveTrainCarriage(TrainCarriageSaveReq trainCarriageSaveReq);

    /**
     * 查询车厢列表
     * @param trainCarriageQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<TrainCarriageQueryResp>> queryTrainCarriageList(TrainCarriageQueryReq trainCarriageQueryReq);


    /**
     * 删除车厢信息
     * @param id 车厢id
     * @return 返回结果
     */
    CommonResp<Void> deleteTrainCarriage(Long id);
}
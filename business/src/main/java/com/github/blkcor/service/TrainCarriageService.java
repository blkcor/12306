package com.github.blkcor.service;

import com.github.blkcor.req.TrainCarriageQueryReq;
import com.github.blkcor.req.TrainCarriageSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainCarriageQueryResp;


public interface TrainCarriageService {

    /**
     * 保存乘客信息
     * @param trainCarriageSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveTrainCarriage(TrainCarriageSaveReq trainCarriageSaveReq);

    /**
     * 查询乘客列表
     * @param trainCarriageQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<TrainCarriageQueryResp>> queryTrainCarriageList(TrainCarriageQueryReq trainCarriageQueryReq);


    /**
     * 删除乘客信息
     * @param id 乘客id
     * @return 返回结果
     */
    CommonResp<Void> deleteTrainCarriage(Long id);
}
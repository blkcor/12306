package com.github.blkcor.service;

import com.github.blkcor.req.TrainQueryReq;
import com.github.blkcor.req.TrainSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.TrainQueryResp;


public interface TrainService {

    /**
     * 保存乘客信息
     * @param trainSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveTrain(TrainSaveReq trainSaveReq);

    /**
     * 查询乘客列表
     * @param trainQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<TrainQueryResp>> queryTrainList(TrainQueryReq trainQueryReq);


    /**
     * 删除乘客信息
     * @param id 乘客id
     * @return 返回结果
     */
    CommonResp<Void> deleteTrain(Long id);
}
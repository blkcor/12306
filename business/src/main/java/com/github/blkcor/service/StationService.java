package com.github.blkcor.service;

import com.github.blkcor.req.StationQueryReq;
import com.github.blkcor.req.StationSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.StationQueryResp;


public interface StationService {

    /**
     * 保存乘客信息
     * @param stationSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveStation(StationSaveReq stationSaveReq);

    /**
     * 查询乘客列表
     * @param stationQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<StationQueryResp>> queryStationList(StationQueryReq stationQueryReq);


    /**
     * 删除乘客信息
     * @param id 乘客id
     * @return 返回结果
     */
    CommonResp<Void> deleteStation(Long id);
}
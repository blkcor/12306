package com.github.blkcor.service;

import com.github.blkcor.entity.DailyTrainSeat;
import com.github.blkcor.req.DailyTrainSeatQueryReq;
import com.github.blkcor.req.DailyTrainSeatSaveReq;
import com.github.blkcor.resp.CommonResp;
import com.github.blkcor.resp.PageResp;
import com.github.blkcor.resp.DailyTrainSeatQueryResp;

import java.util.Date;
import java.util.List;


public interface DailyTrainSeatService {

    /**
     * 保存每日车次座位信息
     * @param dailyTrainSeatSaveReq 请求参数
     * @return 返回结果
     */
    CommonResp<Void> saveDailyTrainSeat(DailyTrainSeatSaveReq dailyTrainSeatSaveReq);

    /**
     * 查询每日车次座位列表
     * @param dailyTrainSeatQueryReq 请求参数
     * @return 返回结果
     */
    CommonResp<PageResp<DailyTrainSeatQueryResp>> queryDailyTrainSeatList(DailyTrainSeatQueryReq dailyTrainSeatQueryReq);


    /**
     * 删除每日车次座位信息
     * @param id 每日车次座位id
     * @return 返回结果
     */
    CommonResp<Void> deleteDailyTrainSeat(Long id);

    /**
     * 生成每日车次座位信息
     * @param date 日期
     * @param trainCode 车次编号
     * @return 返回结果
     */
    CommonResp<Void> genDailyTrainSeat(Date date, String trainCode);

    /**
     * 查询每日车次座位信息
     * @param date 日期
     * @param trainCode 车次编号
     * @param seatType 座位类型
     * @return 返回结果
     */
    Long countSeat(Date date,String trainCode,String seatType);

    /**
     * 查询每日车次座位信息
     * @param date 日期
     * @param trainCode 车次编号
     * @return 返回结果
     */
    Long countSeat(Date date,String trainCode);

    List<DailyTrainSeat> selectByCarriageIndex(String trainCode, Date date, Integer carriageIndex);
}
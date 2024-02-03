package com.github.blkcor.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.blkcor.feign.BusinessFeign;
import com.github.blkcor.resp.CommonResp;
import jakarta.annotation.Resource;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@DisallowConcurrentExecution
public class DailyTrainJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainJob.class);
    @Resource
    private BusinessFeign businessFeign;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("生成15日后车次任务开始!");
        DateTime dateTime = DateUtil.offsetDay(DateUtil.date(), 15);
        Date offsetDate = dateTime.toJdkDate();
        businessFeign.genDaily(offsetDate);
        LOG.info("生成15日后车次任务结束!");
    }
}

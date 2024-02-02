package com.github.blkcor.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class DailyTrainJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainJob.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("生成每日车次任务开始!");

        LOG.info("生成每日车次任务结束!");
    }
}

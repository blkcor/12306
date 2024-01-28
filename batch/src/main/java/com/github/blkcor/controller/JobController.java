package com.github.blkcor.controller;

import com.github.blkcor.req.CronJobReq;
import com.github.blkcor.resp.CommonResp;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/job")
public class JobController {
    private static final Logger LOG = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @PostMapping("/add")
    public CommonResp<Void> addJob(@RequestBody CronJobReq cronJobReq) {
        String jobName = cronJobReq.getName();
        String jobGroup = cronJobReq.getGroup();
        String cronExpression = cronJobReq.getCronExpression();
        String description = cronJobReq.getDescription();
        LOG.info("创建定时任务开始：{} {} {} {}", jobName, jobGroup, cronExpression, description);
        try {
            //获得调度器实例
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //启动调度器
            scheduler.start();
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(jobName)).withIdentity(jobName, jobGroup).build();
            //表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            //按照cron表达式创建一个trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobName, jobGroup).withSchedule(cronScheduleBuilder).withDescription(description).build();
            //执行任务
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            LOG.error("创建定时任务失败：{}", e);
            return new CommonResp<>(false, "创建定时任务失败:异常调度", null);
        } catch (ClassNotFoundException e) {
            LOG.error("创建定时任务失败：{}", e);
            return new CommonResp<>(false, "创建定时任务失败:任务类不存在", null);
        }
        LOG.info("创建定时任务成功：{} {} {} {}", jobName, jobGroup, cronExpression, description);
        return CommonResp.success(null);
    }

    @PostMapping("/pause")
    public CommonResp<Void> pauseJob(@RequestBody CronJobReq cronJobReq) {
        String jobName = cronJobReq.getName();
        String jobGroup = cronJobReq.getGroup();
        LOG.info("暂停定时任务开始：{} {}", jobName, jobGroup);
        try {
            //获得调度器实例
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //暂停定时任务
            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            LOG.error("暂停定时任务失败：{}", e);
            return new CommonResp<>(false, "暂停定时任务失败", null);
        }
        LOG.info("暂停定时任务成功：{} {}", jobName, jobGroup);
        return CommonResp.success(null);
    }

    @PostMapping("/resume")
    public CommonResp<Void> resumeJob(@RequestBody CronJobReq cronJobReq) {
        String jobName = cronJobReq.getName();
        String jobGroup = cronJobReq.getGroup();
        LOG.info("恢复定时任务开始：{} {}", jobName, jobGroup);
        try {
            //获得调度器实例
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //恢复定时任务
            scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            LOG.error("恢复定时任务失败：{}", e);
            return new CommonResp<>(false, "恢复定时任务失败", null);
        }
        LOG.info("恢复定时任务成功：{} {}", jobName, jobGroup);
        return CommonResp.success(null);
    }

    @PostMapping("/reschedule")
    public CommonResp<Void> reschedule(@RequestBody CronJobReq cronJobReq){
        String jobName = cronJobReq.getName();
        String jobGroup = cronJobReq.getGroup();
        String cronExpression = cronJobReq.getCronExpression();
        String description = cronJobReq.getDescription();
        LOG.info("重置定时任务开始：{} {} {} {}", jobName, jobGroup, cronExpression, description);
        try {
            //获得调度器实例
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(jobName)).withIdentity(jobName, jobGroup).build();
            //表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            //按照cron表达式创建一个trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobName, jobGroup).withSchedule(cronScheduleBuilder).withDescription(description).build();
            //执行任务
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            LOG.error("重置定时任务失败：{}", e);
            return new CommonResp<>(false, "重置定时任务失败:异常调度", null);
        } catch (ClassNotFoundException e) {
            LOG.error("重置定时任务失败：{}", e);
            return new CommonResp<>(false, "重置定时任务失败:任务类不存在", null);
        }
        LOG.info("重置定时任务成功：{} {} {} {}", jobName, jobGroup, cronExpression, description);
        return CommonResp.success(null);
    }

    @PostMapping("/delete")
    public CommonResp<Void> deleteJob(@RequestBody CronJobReq cronJobReq){
        String jobName = cronJobReq.getName();
        String jobGroup = cronJobReq.getGroup();
        LOG.info("删除定时任务开始：{} {}", jobName, jobGroup);
        try {
            //获得调度器实例
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //删除定时任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            LOG.error("删除定时任务失败：{}", e);
            return new CommonResp<>(false, "删除定时任务失败", null);
        }
        LOG.info("删除定时任务成功：{} {}", jobName, jobGroup);
        return CommonResp.success(null);
    }
}

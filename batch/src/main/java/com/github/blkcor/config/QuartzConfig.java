//package com.github.blkcor.config;
//
//import com.github.blkcor.job.QuartzJob;
//import org.quartz.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class QuartzConfig {
//    /**
//     * 声明一个任务
//     */
//    @Bean
//    public JobDetail jobDetail(){
//        return JobBuilder.newJob(QuartzJob.class)
//                .withIdentity("quartzJob", "quartzJobGroup")
//                .storeDurably()
//                .build();
//    }
//
//    /**
//     * 声明一个触发器，即什么时候触发这个任务
//     */
//    @Bean
//    public Trigger trigger(){
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail())
//                .withIdentity("quartzJobTrigger", "quartzJobTriggerGroup")
//                .startNow()
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(5)
//                        .repeatForever())
//                .build();
//    }
//}

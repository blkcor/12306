package com.github.blkcor.resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CronJobResp {
    /**
     * 任务分组
     */
    private String group;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务描述
     */
    private String description;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 上一次执行时间
     */
    private String previousFireTime;

    /**
     * 下一次执行时间
     */
    private String nextFireTime;

    /**
     * 任务状态
     */
    private String status;
}

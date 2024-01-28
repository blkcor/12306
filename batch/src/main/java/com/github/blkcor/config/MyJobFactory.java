package com.github.blkcor.config;


import jakarta.annotation.Resource;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/*
    * 可以使用下面的方法来inject一个bean到spring容器中
 */
@Component
public class MyJobFactory  extends SpringBeanJobFactory {
    @Resource
    private AutowireCapableBeanFactory beanFactory;

    /**
     * 这里覆盖了父类的createJobInstance方法，对其创建出来的类再进行autowire。
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}

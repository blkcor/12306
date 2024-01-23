package com.github.blkcor.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 * 接口调用日志切面
 * 用于记录接口调用日志
 */
@Aspect
@Component
public class LogAspect {
    public LogAspect() {
        System.out.println("LogAspect");
    }

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义Pointcut
     */
    @Pointcut("execution(public * com.github.blkcor..*Controller.*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        //开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        //打印请求日志
        LOG.info("===============开始===============");
        LOG.info("请求地址:{},{}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("类名方法:{}.{}", signature.getDeclaringTypeName(), name);
        LOG.info("远程地址:{}", request.getRemoteAddr());

        //打印请求参数
        Object[] args = joinPoint.getArgs();
        LOG.info("请求参数:{}", args);

        //排除特殊类型的参数，如文件类型
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }

        //排除字段 敏感字段或太长的字段不显示： 如身份证，手机号，邮箱，密码
        String[] excludeFields = {"mobile"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeFields);
        LOG.info("请求参数:{}", com.alibaba.fastjson.JSON.toJSONString(arguments, excludeFilter));
    }

    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        String[] excludeFields = {"mobile"};
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOG.error("异常信息:{}", throwable.getMessage());
            throw throwable;
        } finally {
            //返回结果也需要过滤敏感字段
            PropertyPreFilters propertyPreFilters = new PropertyPreFilters();
            PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = propertyPreFilters.addFilter();
            excludeFilter.addExcludes(excludeFields);
            //打印出参日志
            LOG.info("返回结果:{}", JSONObject.toJSONString(result, excludeFilter));
            LOG.info("===============结束 耗时：{} ms===============", System.currentTimeMillis() - startTime);
        }
        return result;
    }
}

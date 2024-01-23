package com.github.blkcor.handler;


import com.github.blkcor.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 所有异常统一处理
     */
    @ExceptionHandler(Exception.class)
    public CommonResp<Exception> exceptionHandler(Exception e) {
        CommonResp<Exception> commonResp = new CommonResp<>();
        LOG.error("系统异常: ", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统异常，请联系管理员");
        commonResp.setContent(e);
        return commonResp;
    }


}

package com.github.blkcor.handler;


import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.exception.BusinessExceptionEnum;
import com.github.blkcor.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 所有异常统一处理
     * 注意要使用@ResponseBody注解将结果转换为Json格式
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResp<Exception> exceptionHandler(Exception e) {
        CommonResp<Exception> commonResp = new CommonResp<>();
        LOG.error("系统异常: ", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统异常，请联系管理员");
        commonResp.setContent(e);
        return commonResp;
    }

    /**
     * 业务异常统一处理
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public CommonResp<String> businessExceptionHandler(BusinessException exception) {
        CommonResp<String> commonResp = new CommonResp<>();
        LOG.error("业务异常: ",exception);
        commonResp.setSuccess(false);
        commonResp.setMessage(exception.getBusinessExceptionEnum().getDesc());
        return commonResp;
    }

}

package com.github.blkcor.handler;


import com.github.blkcor.exception.BusinessException;
import com.github.blkcor.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 所有异常统一处理
     * 注意要使用@ResponseBody注解将结果转换为Json格式
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResp<Exception> exceptionHandler(Exception e) {
        CommonResp<Exception> commonResp = new CommonResp<>();
        LOG.error("系统异常: {}", e.getMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage("系统异常，请联系管理员");
        return commonResp;
    }

    /**
     * 业务异常统一处理
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResp<String> businessExceptionHandler(BusinessException exception) {
        CommonResp<String> commonResp = new CommonResp<>();
        LOG.error("业务异常: {}",exception.getBusinessExceptionEnum().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(exception.getBusinessExceptionEnum().getDesc());
        return commonResp;
    }

    /**
     * 参数绑定异常统一处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResp<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        CommonResp<String> commonResp = new CommonResp<>();
        String defaultMessage = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();
        LOG.error("参数绑定异常: {}",defaultMessage);
        commonResp.setSuccess(false);
        commonResp.setMessage(defaultMessage);
        return commonResp;
    }


}

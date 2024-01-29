package com.github.blkcor.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DailyTrainQueryReq extends PageReq {

    private String trainCode;

    /**
     * 对于Get请求，SpringMVC默认使用ISO-8859-1编码，所以需要使用@DateTimeFormat注解指定日期格式
     * 对于Post请求，SpringMVC默认使用UTF-8编码，所以不需要使用@DateTimeFormat，使用@JsonFormat注解指定日期格式
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}

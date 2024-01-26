package com.github.blkcor.resp;

<#list typeSet as type>
    <#if type == "Date">
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
    </#if>
    <#if type == "BigDecimal">
        import java.math.BigDecimal;
    </#if>
</#list>
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ${Domain}QueryResp {
<#list fieldList as field>

    /**
    * ${field.comment}
    */
    <#if field.javaType=='Date'>
        <#if field.type=='time'>
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
        <#elseif field.type=='date'>
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        <#else>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        </#if>
    </#if>
    <#if field.name=='id' || field.name?ends_with('_id')>
    @JsonSerialize(using = ToStringSerializer.class)
    </#if>
    private ${field.javaType} ${field.nameHump};
</#list>
}

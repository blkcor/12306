package com.github.blkcor.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterReq {

    @NotBlank(message = "手机号不能为空")
    private String mobile;
}

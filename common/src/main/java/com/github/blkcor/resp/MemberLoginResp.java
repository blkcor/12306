package com.github.blkcor.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginResp {

    private Long id;

    private String mobile;

    private String token;
}

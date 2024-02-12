package com.github.blkcor.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MemberTicketQueryReq extends PageReq {
    private Long memberId;
}

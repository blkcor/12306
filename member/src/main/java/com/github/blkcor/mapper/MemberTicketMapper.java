package com.github.blkcor.mapper;

import com.github.blkcor.entity.MemberTicket;
import com.github.blkcor.entity.MemberTicketExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberTicketMapper {
    long countByExample(MemberTicketExample example);

    int deleteByExample(MemberTicketExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MemberTicket row);

    int insertSelective(MemberTicket row);

    List<MemberTicket> selectByExample(MemberTicketExample example);

    MemberTicket selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") MemberTicket row, @Param("example") MemberTicketExample example);

    int updateByExample(@Param("row") MemberTicket row, @Param("example") MemberTicketExample example);

    int updateByPrimaryKeySelective(MemberTicket row);

    int updateByPrimaryKey(MemberTicket row);
}
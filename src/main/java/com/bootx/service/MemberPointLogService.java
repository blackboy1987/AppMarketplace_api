package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.MemberPointLog;


/**
 * @author black
 */
public interface MemberPointLogService extends BaseService<MemberPointLog,Long> {

    void create(Member member, Integer type,Long point,String memo);
}

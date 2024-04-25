package com.bootx.service.impl;

import com.bootx.dao.MemberPointLogDao;
import com.bootx.entity.Member;
import com.bootx.entity.MemberPointLog;
import com.bootx.service.MemberPointLogService;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class MemberPointLogServiceImpl extends BaseServiceImpl<MemberPointLog,Long> implements MemberPointLogService {
    @Override
    public void create(Member member, Integer type,Long point,String memo) {
        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setPoint(point);
        memberPointLog.setMember(member);
        memberPointLog.setType(type);
        memberPointLog.setMemo(memo);
        super.save(memberPointLog);
    }
}

package com.bootx.service.impl;

import com.bootx.entity.CollectLog;
import com.bootx.entity.Member;
import com.bootx.service.CollectLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Service
public class CollectLogServiceImpl extends BaseServiceImpl<CollectLog,Long> implements CollectLogService {

    @Override
    public void create(Long softId, Member member) {
        CollectLog collectLog = new CollectLog();
        collectLog.setMemberId(member.getId());
        collectLog.setSoftId(softId);
        super.save(collectLog);
    }

    @Override
    public void remove(Long softId, Member member) {
        jdbcTemplate.update("delete from collectlog where softId=? and memberId=?",softId,member.getId());
    }

    @Override
    public List<Map<String, Object>> list(Member member) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select soft.categoryName,soft.id,soft.name,soft.logo,soft.versionName from collectlog,soft where memberId=? and soft.id=collectlog.softId order by soft.createdDate desc ", member.getId());
        return maps;
    }
}

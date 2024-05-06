package com.bootx.service;

import com.bootx.entity.CollectLog;
import com.bootx.entity.Member;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
public interface CollectLogService extends BaseService<CollectLog,Long> {
    void create(Long softId, Member member);

    void remove(Long softId, Member member);

    List<Map<String,Object>> list(Member member);
}

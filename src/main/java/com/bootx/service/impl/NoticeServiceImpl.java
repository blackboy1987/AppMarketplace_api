package com.bootx.service.impl;

import com.bootx.entity.Notice;
import com.bootx.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice,Long> implements NoticeService {

    @Override
    public List<Map<String, Object>> get(Integer type) {
        return jdbcTemplate.queryForList("select id, title,content from notice where type=? order by id desc limit 1;",type);
    }
}

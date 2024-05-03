package com.bootx.service;

import com.bootx.entity.Notice;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
public interface NoticeService extends BaseService<Notice,Long> {
    List<Map<String,Object>> get();
}

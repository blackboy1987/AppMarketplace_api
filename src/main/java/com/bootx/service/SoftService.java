
package com.bootx.service;

import com.bootx.common.Pageable;
import com.bootx.entity.Category;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.pojo.SoftPOJO;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
public interface SoftService extends BaseService<Soft, Long> {

    List<Map<String,Object>> list(Pageable pageable, Long categoryId,Boolean cache);

    Map<String,Object> detail(Long id);

    void updateDownloads(Long id, int i);

    void updateViewCount(Long id, int i);

    List<Map<String, Object>> get(Pageable pageable, String orderBy, Long categoryId);

    List<Map<String, Object>> search(String keywords, Pageable pageable);

    void create(Soft soft, Member member,Category category);

    void load(List<Map<String, Object>> list);
}
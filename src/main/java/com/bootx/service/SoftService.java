
package com.bootx.service;

import com.bootx.common.Pageable;
import com.bootx.entity.Category;
import com.bootx.entity.Soft;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
public interface SoftService extends BaseService<Soft, Long> {

    List<Map<String,Object>> list(Pageable pageable, Long categoryId);

    Map<String,Object> detail(Long id);

    void updateDownloads(Long id, int i);

    List<Map<String, Object>> get(Pageable pageable, String orderBy, Long categoryId);

    void batchSave(Category category, List<Soft> soft);

    List<Map<String, Object>> search(String keywords, Pageable pageable);
}

package com.bootx.service;

import com.bootx.common.Pageable;
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
}
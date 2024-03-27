
package com.bootx.service;

import com.bootx.entity.Category;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
public interface CategoryService extends BaseService<Category, Long> {

    Category create(Category root);

    List<Map<String,Object>> list();

    void clearCache(String cacheKey);

    List<Map<String,Object>> tree();
}
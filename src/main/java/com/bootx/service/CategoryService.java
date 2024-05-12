
package com.bootx.service;

import com.bootx.entity.Category;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
public interface CategoryService extends BaseService<Category, Long> {

    List<Map<String,Object>> list();

    void clearCache(String cacheKey);

    List<Map<String,Object>> tree();


    /**
     * 查找顶级地区
     *
     * @return 顶级地区
     */
    List<Category> findRoots();

    /**
     * 查找顶级地区
     *
     * @param count
     *            数量
     * @return 顶级地区
     */
    List<Category> findRoots(Integer count);

    /**
     * 查找上级地区
     *
     * @param category
     *            分类
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 上级地区
     */
    List<Category> findParents(Category category, boolean recursive, Integer count);

    /**
     * 查找下级地区
     *
     * @param category
     *            分类
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 下级地区
     */
    List<Category> findChildren(Category category, boolean recursive, Integer count);
}
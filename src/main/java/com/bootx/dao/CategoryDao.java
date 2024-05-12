
package com.bootx.dao;


import com.bootx.entity.Category;

import java.util.List;

/**
 * @author black
 */
public interface CategoryDao extends BaseDao<Category, Long> {
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
package com.bootx.service.impl;

import com.bootx.dao.CategoryDao;
import com.bootx.entity.Category;
import com.bootx.service.CategoryService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    @Transactional(readOnly = true)
    public List<Category> findRoots() {
        return categoryDao.findRoots(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findRoots(Integer count) {
        return categoryDao.findRoots(count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findParents(Category category, boolean recursive, Integer count) {
        return categoryDao.findParents(category, recursive, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findChildren(Category category, boolean recursive, Integer count) {
        return categoryDao.findChildren(category, recursive, count);
    }


    @Override
    public List<Map<String, Object>> list() {
        List<Map<String,Object>> list = jdbcTemplate.queryForList("select id,name from category order by orders asc ;");
        return list;
    }

    @Override
    public void clearCache(String cacheKey) {
        if(cacheKey==null){
            redisService.delete("category:list");
            redisService.delete("category:list1");
            redisService.delete("category:tree");
        }else{
            redisService.delete(cacheKey);
        }
    }

    @Override
    public List<Map<String, Object>> tree() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select parent.id,parent.name,(select count(child.id) from category child where parent_id=parent.id) childrenCount from category parent where parent.parent_id is null order by orders asc ;");
        maps.forEach(item->{
            if(!StringUtils.equalsIgnoreCase(item.get("childrenCount")+"","0")){
                item.put("children",jdbcTemplate.queryForList("select id,name from category where parent_id=? order by orders asc ",item.get("id")));
            }
            item.remove("childrenCount");
        });
        return maps;
    }

    @Override
    @Transactional
    public Category save(Category category) {
        Assert.notNull(category, "[Assertion failed] - area is required; it must not be null");

        setValue(category);
        return super.save(category);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        Assert.notNull(category, "[Assertion failed] - area is required; it must not be null");

        setValue(category);
        for (Category children : categoryDao.findChildren(category, true, null)) {
            setValue(children);
        }
        return super.update(category);
    }

    /**
     * 设置值
     *
     * @param category
     *            地区
     */
    private void setValue(Category category) {
        if (category == null) {
            return;
        }
        Category parent = category.getParent();
        if (parent != null) {
            category.setFullName(parent.getFullName() + category.getName());
            category.setTreePath(parent.getTreePath() + parent.getId() + Category.TREE_PATH_SEPARATOR);
        } else {
            category.setFullName(category.getName());
            category.setTreePath(Category.TREE_PATH_SEPARATOR);
        }
        category.setGrade(category.getParentIds().length);
    }
}
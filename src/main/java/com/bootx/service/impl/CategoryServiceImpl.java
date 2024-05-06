package com.bootx.service.impl;

import com.bootx.dao.CategoryDao;
import com.bootx.entity.Category;
import com.bootx.service.CategoryService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
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
    public Category create(Category category) {

        setValue(category);

        Category current = categoryDao.find("url", category.getUrl());
        if(current==null){
            return super.save(category);
        }
        current.setParent(category.getParent());
        current.setName(category.getName());
        current.setUrl(category.getUrl());
        return super.update(current);
    }

    @Override
    public List<Map<String, Object>> list() {
        List<Map<String,Object>> list = new ArrayList<>();
        String s = redisService.get("category:list");
        try {
            list = JsonUtils.toObject(s, new TypeReference<List<Map<String, Object>>>() {
            });
        }catch (Exception ignored){

        }
        if(list.isEmpty()){
            Map<String,Object> root = new HashMap<>();
            root.put("id",0);
            root.put("name","全部");
            list.add(root);
            List<Map<String,Object>> list1 = jdbcTemplate.queryForList("select id,name from category where parent_id is not null order by orders asc ;");
            list.addAll(list1);
            redisService.set("category:list",JsonUtils.toJson(list));
        }
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
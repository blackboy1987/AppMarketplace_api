package com.bootx.service.impl;

import com.bootx.dao.CategoryDao;
import com.bootx.entity.Category;
import com.bootx.service.CategoryService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
        }else{
            redisService.delete(cacheKey);
        }
    }
}
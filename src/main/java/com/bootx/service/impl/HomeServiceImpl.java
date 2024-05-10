package com.bootx.service.impl;

import com.bootx.service.CategoryService;
import com.bootx.service.HomeService;
import com.bootx.service.NoticeService;
import com.bootx.service.RedisService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private CategoryService categoryService;

    @Resource
    private RedisService redisService;

    @Resource
    private NoticeService noticeService;

    @Override
    public Map<String, Object> load(Boolean cache) {
        String cacheKey = "home:index";
        Map<String,Object> data = new HashMap<>();
        if(cache){
            try {
                String s = redisService.get(cacheKey);
                data = JsonUtils.toObject(s, new TypeReference<Map<String, Object>>() {
                });
                // 通知公告
                return data;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        List<Map<String, Object>> categories = categoryService.list();
        data.put("categories",categories);
        data.put("carousel",jdbcTemplate.queryForList("select id,image,url from carousel order by  orders asc "));
        data.put("list",jdbcTemplate.queryForList("select id,name,logo,categoryName,versionName,DATE_FORMAT(updateDate,'%Y-%m-%d %h:%i') updateDate from soft order by updateDate desc limit 30"));
        // 通知公告
        data.put("notice",noticeService.get());

        redisService.set(cacheKey,JsonUtils.toJson(data),2, TimeUnit.HOURS);
        return data;
    }

    @Override
    public Map<String, Object> data(boolean b) {
        return Map.of();
    }
}

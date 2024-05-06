package com.bootx.service.impl;

import com.bootx.common.Pageable;
import com.bootx.dao.CategoryDao;
import com.bootx.dao.SoftDao;
import com.bootx.entity.Category;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftImage;
import com.bootx.pojo.SoftPOJO;
import com.bootx.service.SoftImageService;
import com.bootx.service.SoftService;
import com.bootx.util.DateUtils;
import com.bootx.util.ImageUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.UploadUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@Service
public class SoftServiceImpl extends BaseServiceImpl<Soft, Long> implements SoftService {

    @Resource
    private SoftDao softDao;

    @Resource
    private SoftImageService softImageService;
    @Resource
    private CategoryDao categoryDao;

    @Override
    public List<Map<String, Object>> list(Pageable pageable, Long categoryId,Boolean cache) {
        String cacheKey = "SoftServiceImpl:list:"+pageable.getPageSize()+"_"+pageable.getPageNumber()+"_"+categoryId;
        if(cache==null || cache){
            String s = redisService.get(cacheKey);
            if(StringUtils.isNotBlank(s)){
                try {
                    return JsonUtils.toObject(s, new TypeReference<List<Map<String, Object>>>() {
                    });
                }catch (Exception ignored){

                }
            }
        }
        StringBuffer sb = new StringBuffer();
        if(categoryId!=null && categoryId!=0){
            sb.append("select id,name,logo,DATE_FORMAT(updateDate,'%Y-%m-%d %h:%i') updateDate,versionName,viewCount from soft,soft_categories where soft_categories.softs_id=soft.id and soft_categories.categories_id="+categoryId);
        }else{
            sb.append("select id,name,logo,DATE_FORMAT(updateDate,'%Y-%m-%d %h:%i') updateDate,versionName,viewCount from soft where 1=1");
        }
        sb.append(" order by updateDate desc");
        sb.append(" limit ?,?");
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sb.toString(), (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
        maps.forEach(item->{
            Long downloads = Long.valueOf(item.get("viewCount")+"");
            if(downloads>100000){
                item.put("downloads",String.format("%.2f",downloads/10000.0)+"万次");
            }else{
                item.put("downloads",downloads+"次");
            }
        });
        // 2分钟缓存
        redisService.set(cacheKey,JsonUtils.toJson(maps),2, TimeUnit.MINUTES);
        return maps;
    }

    @Override
    public Map<String, Object> detail(Long id) {
        String cacheKey = "soft:detail_" + id;
        Map<String,Object> data = new HashMap<>();
        String s = redisService.get(cacheKey);
        try {
            data = JsonUtils.toObject(s, new TypeReference<Map<String, Object>>() {
            });
        }catch (Exception ignored){

        }
        if(!data.isEmpty()){
            return data;
        }
        Soft soft = super.find(id);
        data.put("versionCode",soft.getVersionCode());
        data.put("versionName",soft.getVersionName());
        data.put("id",soft.getId());
        data.put("reviewCount",soft.getViewCount());
        data.put("name",soft.getName());
        data.put("logo",soft.getLogo());
        data.put("size",soft.getSize());
        data.put("introduce",soft.getIntroduce());
        data.put("memo",soft.getMemo());
        data.put("categoryName",soft.getCategoryName());
        data.put("updatedContent",soft.getUpdatedContent());
        if(soft.getDownloads()>=10000){
            data.put("downloads",String.format("%.2f",soft.getViewCount()/10000.0)+"万");
        }else{
            data.put("downloads",soft.getDownloads()+"次下载");
        }
        redisService.set(cacheKey,JsonUtils.toJson(data),1,TimeUnit.MINUTES);
        return data;
    }

    @Override
    @Transactional
    public void updateDownloads(Long id, int i) {
        Soft soft = find(id);
        if (soft != null) {
            soft.setDownloads(soft.getDownloads() + i);
            update(soft);
        }
    }

    @Override
    @Transactional
    public void updateViewCount(Long id, int i) {
        Soft soft = find(id);
        if (soft != null) {
            soft.setDownloads(soft.getViewCount() + i);
            update(soft);
        }
    }


    @Override
    public List<Map<String, Object>> get(Pageable pageable, String orderBy, Long categoryId) {
        pageable.setPageSize(1000);
        List<Map<String, Object>> maps = new ArrayList<>();
        String fromSql = "from soft";
        if (categoryId != null && categoryId != 0) {
            fromSql = "from soft_categories,soft where softs_id=soft.id and categories_id=" + categoryId;
        }
        String pageQuery = "limit " + (pageable.getPageNumber() - 1) * pageable.getPageSize() + "," + pageable.getPageSize();
        if (StringUtils.equalsIgnoreCase("00", orderBy)) {
            // 下载排行
            maps = jdbcTemplate.queryForList("select size, score,versionName, id,logo,name " + fromSql + " order by downloads desc " + pageQuery);
            maps.forEach(item -> {
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("01", orderBy)) {
            // 评分排行
            maps = jdbcTemplate.queryForList("select id,score,logo,name,versionName,size " + fromSql + " order by score desc " + pageQuery);
            maps.forEach(item -> {
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("2", orderBy)) {
            // 随机
            maps = jdbcTemplate.queryForList("SELECT size, id,logo,name,score FROM soft WHERE id >= ((SELECT MAX(id) FROM soft)-(SELECT MIN(id) FROM soft)) * RAND() + (SELECT MIN(id) FROM soft) LIMIT 20");
            maps.forEach(item -> {
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("3", orderBy)) {
            // 更新排行
            maps = jdbcTemplate.queryForList("select size, id,logo,name,score,versionName,updateDate " + fromSql + " order by updateDate desc " + pageQuery);
            maps.forEach(item -> {
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("7", orderBy)) {
            maps = jdbcTemplate.queryForList("select size, id,logo,name,score,versionName " + fromSql + " order by downloads desc " + pageQuery);
            maps.forEach(item -> {
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("8", orderBy)) {
            maps = jdbcTemplate.queryForList("select size, id,logo,name " + fromSql + " order by downloads desc " + pageQuery);
        } else {
            maps = jdbcTemplate.queryForList("select size, id ,logo,name,score " + fromSql + " order by downloads desc " + pageQuery);
            maps.forEach(item -> {
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        }
        return maps;
    }


    @Override
    public List<Map<String, Object>> search(String keywords, Pageable pageable) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select versionName, id,logo,name,size,updateDate,score from soft where name like ? limit ?,?;", "%" + keywords + "%", (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
        maps.forEach(item -> {
            item.put("memo", "万次下载");
        });
        return maps;
    }


    @Override
    public void create(Soft soft, Member member,Category category) {
        soft.setMember(member);
        soft.setStatus(0);
        init(soft);
        initCategory(soft,category);
        super.save(soft);
        initSoftImage(soft);
    }

    @Override
    public void load(List<Map<String, Object>> list) {
        Pageable pageable = new Pageable();
        pageable.setPageNumber(1);
        pageable.setPageSize(1000);
        for (Map<String, Object> map : list) {
            List<Map<String, Object>> list1 = list(pageable, Long.valueOf(map.get("id") + ""),false);
            redisService.set("productList"+map.get("id"),JsonUtils.toJson(list1));
        }
    }

    private void init(Soft soft) {
        soft.setViewCount(1L);
        soft.setUpdateDate(new Date());
        soft.setDownloads(1L);
    }

    private void initCategory(Soft soft,Category category) {
        // 分类
        Set<Category> categories = new HashSet<>();
        categories.add(category.getParent());
        categories.add(category);
        soft.setCategories(categories);
        soft.setCategoryName(category.getFullName());
        // 写入终极分类
    }

    private void initSoftImage(Soft soft) {
        softImageService.remove(soft);
        for (SoftImage softImage : soft.getSoftImages()) {
            softImage.setSoft(soft);
            softImageService.save(softImage);
        }
    }
}
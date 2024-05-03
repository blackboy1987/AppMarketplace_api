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

import java.beans.Transient;
import java.io.File;
import java.util.*;

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
    public List<Map<String, Object>> list(Pageable pageable, Long categoryId) {

        StringBuffer sb = new StringBuffer();
        if(categoryId!=null && categoryId!=0){
            sb.append("select id,name,logo,DATE_FORMAT(updateDate,'%Y-%m-%d %h:%i') updateDate,versionName,downloads from soft,soft_categories where soft_categories.softs_id=soft.id and soft_categories.categories_id="+categoryId);
        }else{
            sb.append("select id,name,logo,DATE_FORMAT(updateDate,'%Y-%m-%d %h:%i') updateDate,versionName,downloads from soft where 1=1");
        }
        sb.append(" order by updateDate desc");
        sb.append(" limit ?,?");
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sb.toString(), (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
        maps.forEach(item->{
            Long downloads = Long.valueOf(item.get("downloads")+"");
            if(downloads>100000){
                item.put("downloads",String.format("%.2f",downloads/10000.0)+"万次");
            }else{
                item.put("downloads",downloads+"次");
            }
        });
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
        data.put("reviewCount",soft.getReviewCount());
        data.put("fullName",soft.getFullName());
        data.put("score",String.format("%.2f", soft.getScore()));
        data.put("name",soft.getName());
        data.put("logo",soft.getLogo());
        data.put("size",soft.getSize());
        data.put("updateDate",soft.getUpdateDate());
        data.put("introduce",soft.getIntroduce());
        data.put("memo",soft.getMemo());
        data.put("updatedContent",soft.getUpdatedContent());
        if(soft.getDownloads()>=10000){
            data.put("downloads",String.format("%.2f",soft.getDownloads()/10000.0)+"万");
        }else{
            data.put("downloads",soft.getDownloads()+"次下载");
        }
        redisService.set(cacheKey,JsonUtils.toJson(data));
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
    public List<Map<String, Object>> get(Pageable pageable, String orderBy, Long categoryId) {
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
    public void batchSave(Category category, List<Soft> softs) {
        for (Soft soft : softs) {
            Soft current = softDao.find("url", soft.getUrl());
            if(current==null){
                Soft.init(soft);
                soft.getCategories().add(category);
                super.save(soft);
            }else{
                current.getCategories().add(category);
                super.update(current);
            }
        }
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
    public void create(SoftPOJO softPOJO, Member member) {
        Soft soft = new Soft();
        //soft.setMember(member);
        soft.setStatus(0);
        init(soft, softPOJO);
        initCategory(soft, softPOJO);
        super.save(soft);
        //initSoftImage(soft, softPOJO);

    }

    private void init(Soft soft, SoftPOJO softPOJO) {
        soft.setSize(softPOJO.getSize() + "");
        soft.setMemo(softPOJO.getUpdatedContent());
        soft.setScore(9.0);
        soft.setUpdateDate(DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        soft.setVersionName(softPOJO.getVersionName());
        soft.setVersionCode(softPOJO.getVersionCode() + "");
        soft.setDownloads(0L);
        soft.setFullName(softPOJO.getTitle());
        soft.setReviewCount(0L);
        soft.setName(softPOJO.getTitle());
        soft.setDownloadUrl(softPOJO.getDownloadUrl());
        //soft.setPassword(softPOJO.getPassword());
        soft.setMinSdkVersion(softPOJO.getMinSdkVersion());
        soft.setTargetSdkVersion(softPOJO.getTargetSdkVersion());
        soft.setPackageName(softPOJO.getPackageName());


        String path = "yysc/" + DateUtils.formatDateToString(new Date(), "yyyy/MM/dd/") + UUID.randomUUID().toString().replace("-", "") + ".png";
        File temp = new File(SystemUtils.getJavaIoTmpDir(), path);
        if (!temp.getParentFile().exists()) {
            temp.getParentFile().mkdirs();
        }
        ImageUtils.base64ToImage(softPOJO.getAppLogo(), temp);
        soft.setLogo(UploadUtils.upload(path, temp));
        System.out.println(soft.getLogo());
    }

    private void initCategory(Soft soft, SoftPOJO softPOJO) {
        // 分类
        Long categoryId0 = softPOJO.getCategoryId0();
        Long categoryId1 = softPOJO.getCategoryId1();
        Set<Category> categories = new HashSet<>();
        categories.add(categoryDao.find(categoryId0));
        categories.add(categoryDao.find(categoryId1));
        soft.setCategories(categories);
    }

    /*private void initSoftImage(Soft soft, SoftPOJO softPOJO) {
        softImageService.remove(soft);
        String urls = softPOJO.getUrls();
        if(StringUtils.isNotBlank(urls)){
            String[] split = urls.split(",");
            for (String s : split) {
                SoftImage softImage = new SoftImage();
                softImage.setType(0);
                softImage.setSoft(soft);
                softImage.setStatus(0);
                softImage.setUrl(s);
                softImage.setSoft(soft);
                softImageService.save(softImage);
            }
        }
    }*/
}
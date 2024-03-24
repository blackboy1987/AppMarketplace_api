package com.bootx.service.impl;

import com.bootx.common.Pageable;
import com.bootx.entity.Soft;
import com.bootx.service.SoftService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Service
public class SoftServiceImpl extends BaseServiceImpl<Soft, Long> implements SoftService {

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
        String cacheKey = "soft:detail_" + id+"3";
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
        //redisService.set(cacheKey,JsonUtils.toJson(data));
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
}
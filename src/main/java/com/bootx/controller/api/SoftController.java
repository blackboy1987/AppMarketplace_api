
package com.bootx.controller.api;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.security.CurrentUser;
import com.bootx.service.RedisService;
import com.bootx.service.SoftDownloadLogService;
import com.bootx.service.SoftService;
import com.bootx.service.SoftViewLogService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@RestController("ApiSoftController")
@RequestMapping("/api/soft")
public class SoftController {

	@Resource
	private SoftService softService;

	@Resource
	private RedisService redisService;

	@Resource
	private SoftViewLogService softViewLogService;

	@Resource
	private SoftDownloadLogService softDownloadLogService;

	@PostMapping("/list")
	public Result list(Pageable pageable,Long categoryId) {
		return Result.success(softService.list(pageable,categoryId));
	}

	@PostMapping("/detail")
	public Result detail(Long id,@CurrentUser Member member) {
		Soft soft = softService.find(id);
		if(soft==null){
			return Result.error("软件不存在");
		}
		// 写入浏览日志
		softViewLogService.add(member,soft);
		return Result.success(softService.detail(id));
	}


	@PostMapping("/download")
	@JsonView(Soft.DownloadView.class)
	public Result download(Long id) {
		Map<String,Object> data = new HashMap<>();
		Soft soft = softService.find(id);
		if(soft == null){
			return Result.error("软件不存在");
		}
		if(StringUtils.isBlank(soft.getVersionName())){
			soft.setVersionName("未知");
		}
		if(StringUtils.isBlank(soft.getDownloadUrl())){
			return Result.error("暂无下载地址");
		}
		if(!StringUtils.startsWith(soft.getDownloadUrl(),"http")){
			return Result.error("暂无下载地址");
		}
		if(!StringUtils.endsWith(soft.getDownloadUrl(),"apk")){
			return Result.error("暂无下载地址");
		}
		if(StringUtils.isBlank(soft.getVersionName())){
			soft.setVersionName("未知");
		}
		softService.updateDownloads(id,1);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		data.put("adId",uuid);
		data.put("id",soft.getId());
		redisService.set("adId:"+soft.getId(),uuid,10, TimeUnit.MINUTES);
		return Result.success(data);
	}

	@PostMapping("/url")
	public Result url(Long id,@CurrentUser Member member) {
		Map<String,Object> data = new HashMap<>();
		Soft soft = softService.find(id);
		data.put("url",soft.getDownloadUrl());
		//data.put("pwd",soft.getPassword());
		if(soft.getDownloadUrl().endsWith(".apk")){
			data.put("type",0);
		}else{
			data.put("type",1);
		}
		// 写入下载日志
		softDownloadLogService.add(member,soft);
		return Result.success(data);
	}

	@PostMapping("/checkDownload")
	public Result checkDownload(Long id) {
		Map<String,Object> data = new HashMap<>();
		Soft soft = softService.find(id);
		if(StringUtils.isNotBlank(soft.getDownloadUrl())){
			return Result.success();
		}
		return Result.error("暂无下载地址");
	}


	/**
	 * orderBy
	 *  0开头：今日
	 *  	00： 今日下载排行榜
	 *  	01： 今日好评排行榜
	 *  1：新鲜
	 *  2：随心看
	 *  3：最新发布排行
	 *  4：最近活跃排行
	 *  5：最多评价排行
	 *  6：最多投币排行
	 *  7：下载量排行
	 *  8: 热搜应用
	 * @return
	 */
	@PostMapping("/orderBy")
	@Audit(action = "软件列表")
	public Result orderBy(Pageable pageable, String orderBy, Long categoryId, @CurrentUser Member member){
		String cacheKey = orderBy+"_"+categoryId+"_"+pageable.getPageNumber()+"_"+pageable.getPageSize();
		List<Map<String, Object>> maps;
		try {
			maps = JsonUtils.toObject(redisService.get(cacheKey), new TypeReference<List<Map<String, Object>>>() {
			});
		}catch (Exception e){
			maps = softService.get(pageable,orderBy,categoryId);
			if(!maps.isEmpty()){
				redisService.set(cacheKey,JsonUtils.toJson(maps));
			}
		}

		return Result.success(maps);
	}
}
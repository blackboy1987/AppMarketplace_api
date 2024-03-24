
package com.bootx.controller.api;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Soft;
import com.bootx.service.RedisService;
import com.bootx.service.SoftService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

	@PostMapping("/list")
	public Result list(Pageable pageable,Long categoryId) {
		return Result.success(softService.list(pageable,categoryId));
	}

	@PostMapping("/detail")
	public Result detail(Long id) {

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
	public Result url(Long id) {
		Map<String,Object> data = new HashMap<>();
		Soft soft = softService.find(id);
		data.put("url",soft.getDownloadUrl());
		data.put("pwd",soft.getPackageName());
		return Result.success(data);
	}
}
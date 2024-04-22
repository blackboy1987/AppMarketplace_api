
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.entity.Soft;
import com.bootx.service.RedisService;
import com.bootx.service.SoftService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@RestController("ApiDownloadController")
@RequestMapping("/api/download")
public class DownloadController {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private RedisService redisService;

	@Resource
	private SoftService softService;

	/**
	 * 广告
	 * @return
	 */
	@PostMapping("/adReward")
	public Result adReward(Long id, String adId){
		Map<String,Object> data = new HashMap<>();
		Soft soft = softService.find(id);
		String s = redisService.get("adId:" + soft.getId());
		data.put("adId",adId);
		data.put("id",soft.getId());
		if(StringUtils.equalsIgnoreCase(adId,s)){
			// 匹配到了观看记录，返回下载地址
			return Result.success(data);
		}
		return Result.error("error");
	}
}

package com.bootx.controller.api;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @author black
 */
@RestController("ApiIndexController")
@RequestMapping("/api")
public class IndexController{

	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * 广告
	 * @return
	 */
	@PostMapping("/adConfig")
	public Map<String,Object> adConfig(){
		try {
			return jdbcTemplate.queryForMap("select bannerAdId,feedAdId,fullScreenVideoAdId,interAdId,mediaId,rewardVideoAdId,splashAdId,templateAdId,videoFeedAdId from adconfig order by id desc limit 1");
		}catch (Exception e){
			return Collections.emptyMap();
		}
	}
}
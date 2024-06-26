
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.entity.DeviceInfo;
import com.bootx.entity.Member;
import com.bootx.service.DeviceInfoService;
import com.bootx.service.MemberService;
import freemarker.core.ReturnInstruction;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
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

	@Resource
	private MemberService memberService;

	@Resource
	private DeviceInfoService deviceInfoService;

	/**
	 * 广告
	 * @return
	 */
	@PostMapping("/adConfig")
	public Map<String,Object> adConfig(){
		try {
			return jdbcTemplate.queryForMap("select bannerAdId,feedAdId,fullScreenVideoAdId,interAdId,mediaId,rewardVideoAdId,splashAdId,templateAdId,videoFeedAdId from adconfig order by id desc limit 1");
		}catch (Exception e){
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}


	/**
	 * 系统的设置信息
	 * @return
	 */
	@PostMapping("/setting")
	public Map<String,Object> setting(){
		try {
			return jdbcTemplate.queryForMap("select adType0, adType1, adType2, adType3, adType4, adType5, adType6, memberAdType0Count, memberAdType1Count, memberAdType2Count, memberAdType3Count, memberAdType4Count, memberAdType5Count, siteLogo, siteName, memberAdType6Count, adType0Count, adType1Count, adType2Count, adType3Count, adType4Count, adType5Count, adType6Count from siteconfig order by id desc  limit 1");
		}catch (Exception e){
			return Collections.emptyMap();
		}
	}

	@GetMapping("/gather")
	public Result setting(DeviceInfo deviceInfo){
		Member current = memberService.getCurrent();
		if(current!=null){
			deviceInfo.setMemberId(current.getId());
		}
		deviceInfoService.save(deviceInfo);
		return Result.success();
	}
}
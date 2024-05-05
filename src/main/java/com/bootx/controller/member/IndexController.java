
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.util.DateUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@RestController("MemberIndexController")
@RequestMapping("/api/member")
public class IndexController extends BaseController {

	@Resource
	private MemberService memberService;

	@PostMapping("/currentUser")
	public Result currentUser(@CurrentUser Member member) {
		if(member==null){
			return Result.error("请先登录");
		}
		Map<String,Object> data = new HashMap<>();
		data.put("username",member.getUsername());
		data.put("id",member.getId());
		data.put("upload",0);


		// 横幅广告
		data.put("adType0",1);
		// 模板信息流广告
		data.put("adType1",2);
		// 模板视频信息流
		data.put("adType2",3);
		// 全屏视频
		data.put("adType3",4);
		// 插屏广告
		data.put("adType4",5);
		// 激励视频广告
		data.put("adType5",6);
		// 开屏广告
		data.put("adType6",6);
		if(StringUtils.isNotBlank(member.getAvatar())){
			data.put("avatar","https://bootxyysc.oss-cn-hangzhou.aliyuncs.com/logo.png");
		}else{
			data.put("avatar","https://bootxyysc.oss-cn-hangzhou.aliyuncs.com/logo.png");
		}
		/*data.put("point",member.getPoint());
		if(member.getMemberRank()!=null){
			data.put("nextPoint",jdbcTemplate.queryForObject("select point from memberrank where id>? order by id asc limit 1;",Long.class,member.getMemberRank().getId()));
		}else{
			data.put("nextPoint",0);
		}
		data.put("concernCount",jdbcTemplate.queryForObject("select count(id) from fan where member_id=?",Long.class,member.getId()));
		data.put("fanCount",jdbcTemplate.queryForObject("select count(id) from fan where fan_id=?",Long.class,member.getId()));
		data.put("rankName",member.getMemberRank().getName());*/
		return Result.success(data);
	}

	@PostMapping("/update")
	public Result update(String username,@CurrentUser Member member) {
		/*if(memberService.usernameUnique(member.getId(),username)){
			member.setUsername(username);
			memberService.update(member);
			return Result.success();
		}
		return Result.error("昵称已存在");*/
		return Result.error("暂不支持修改昵称");
	}

	@PostMapping("/index")
	public Result index(@CurrentUser Member member) {
		Map<String,Object> data = new HashMap<>();
		data.put("username",member.getUsername());
		return Result.success(data);
	}


	/**
	 * 用户信息
	 * @param member
	 * 		当前登录用户
	 * @param id
	 * 		用户id
	 * @return
	 * 		用户信息
	 */
	@PostMapping("/load")
	public Result load(@CurrentUser Member member,Long id) {
		Map<String,Object> data = new HashMap<>();
		Member member1 = memberService.find(id);
		data.put("avatar",member1.getAvatar());
		data.put("username",member1.getUsername());
		data.put("createdDate", DateUtils.formatDateToString(member1.getCreatedDate(),"yyyy年MM月dd日"));
		data.put("point",member1.getRemainPoint());
		data.put("rankName",member1.getMemberRankName());
		data.put("memo","每日一言");
		data.put("concernCount",jdbcTemplate.queryForObject("select count(id) from fan where member_id=?",Long.class, member.getId()));
		data.put("fanCount",jdbcTemplate.queryForObject("select count(id) from fan where fan_id=?",Long.class, member.getId()));
		return Result.success(data);
	}
}
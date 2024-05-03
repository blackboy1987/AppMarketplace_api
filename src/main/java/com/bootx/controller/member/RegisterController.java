
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.service.EmailService;
import com.bootx.service.MemberRankService;
import com.bootx.service.MemberService;
import com.bootx.util.CodeUtils;
import com.bootx.util.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@RestController("memberRegisterController")
@RequestMapping("/api/member/register")
public class RegisterController extends BaseController {

	@Resource
	private MemberService memberService;

	@Resource
	private MemberRankService memberRankService;

	@Resource
	private EmailService emailService;


	@PostMapping("/sendCode")
	public Result sendCode(String email, HttpServletRequest request) {
		if(StringUtils.isBlank(email)){
			return Result.error("请输入邮箱");
		}
		if (memberService.emailExists(email)) {
			return Result.error("邮箱已存在");
		}

		String code = CodeUtils.getCode(6);
		boolean flag = emailService.send(email, code);
		if(flag){
			redisService.set("register:"+ DigestUtils.md5Hex(email),code,10,TimeUnit.MINUTES);
			return Result.success("验证码发送成功");
		}
		return Result.error("验证码发送失败");
	}

	/**
	 * 注册提交
	 */
	@PostMapping
	public Result submit(String username, String password, String email,String code, String spreadMemberUsername, HttpServletRequest request) {
		if (memberService.usernameExists(username)) {
			return Result.error("用户名已存在");
		}
		/*String s = redisService.get("register:" + DigestUtils.md5Hex(email) + ":" + code);
		if(!StringUtils.equalsIgnoreCase(s,code)){
			return Result.error("验证码输入错误");
		}*/
		email = username+"@qq.com";
		if (memberService.emailExists(email)) {
			return Result.error("邮箱已存在");
		}
		Member member = new Member();
		member.setUsername(username);
		member.setPassword(password);
		member.setEmail(email);
		member.setPoint(0L);
		member.setRemainPoint(0L);
		member.setIsEnabled(true);
		member.setIsLocked(false);
		member.setLockDate(null);
		member.setContinuousSignInDays(0);
		member.setSignInDate(null);
		member.setSignInDays(0);
		member.setLastLoginIp(request.getRemoteAddr());
		member.setLastLoginDate(new Date());
		member.setMemberRank(memberRankService.findDefault());
		Member spreadMember = memberService.findByUsername(spreadMemberUsername);
		if (spreadMember != null) {
			member.setParent(spreadMember);
		}
		memberService.save(member);
		Map<String,Object> data = new HashMap<>();
		data.put("username",member.getUsername());
		data.put("id",member.getId());
		data.put("token", JWTUtils.create(member.getId()+"",data));
		System.out.println("register:"+data.get("token"));
		return Result.success(data);
	}

}
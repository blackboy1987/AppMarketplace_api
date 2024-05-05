
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.entity.AdLog;
import com.bootx.entity.Member;
import com.bootx.service.AdLogService;
import com.bootx.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;

/**
 * @author black
 */
@RestController("ApiAdLogController")
@RequestMapping("/api/adLog")
public class AdLogController {

	@Resource
	private AdLogService adLogService;

	@Resource
	private MemberService memberService;

	@PostMapping("/add")
	public Result add(AdLog adLog,HttpServletRequest request){
		String token = request.getParameter("token");
		if(StringUtils.isNotEmpty(token)){
			Member current = memberService.getCurrent(token);
			if(current!=null){
				adLog.setMemberId(current.getId());
			}
		}
		adLogService.save(adLog);
		return Result.success();
	}
}

package com.bootx.controller.member;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.pojo.SoftPOJO;
import com.bootx.security.CurrentUser;
import com.bootx.service.CategoryService;
import com.bootx.service.CollectLogService;
import com.bootx.service.MemberService;
import com.bootx.service.SoftService;
import com.bootx.util.DateUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController("MemberCollectController")
@RequestMapping("/api/member/collectLog")
public class CollectController extends BaseController {

	@Resource
	private MemberService memberService;

	@Resource
	private CollectLogService collectLogService;

	@PostMapping("/add")
	private Result add(Long softId,@CurrentUser Member member){
		collectLogService.create(softId,member);
		return Result.success();
	}

	@PostMapping("/list")
	private Result list(@CurrentUser Member member){
		return Result.success(collectLogService.list(member));
	}

	@PostMapping("/delete")
	private Result delete(Long softId,@CurrentUser Member member){
		collectLogService.remove(softId,member);
		return Result.success();
	}


}

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
import com.bootx.service.MemberService;
import com.bootx.service.SoftService;
import com.bootx.util.DateUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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
@RestController("MemberTouGaoController")
@RequestMapping("/api/member/touGao")
public class TouGaoController extends BaseController {

	@Resource
	private MemberService memberService;

	@Resource
	private SoftService softService;
	@Resource
	private CategoryService categoryService;
	/**
	 * 获取分类
	 * @param request
	 * @param member
	 * @return
	 */
	@PostMapping("/category")
	public Result index(HttpServletRequest request, @CurrentUser Member member) {
		return Result.success(categoryService.tree());
	}

	@PostMapping("/init")
	public Result init(HttpServletRequest request, @CurrentUser Member member) {
		Map<String,Object> data = new HashMap<>();
		// 渠道
		List<String> quDaoList = new ArrayList<>();
		quDaoList.add("官方版");
		quDaoList.add("国际版");
		quDaoList.add("测试版本");
		quDaoList.add("汉化版");
		data.put("quDaoList",quDaoList);
		// 广告类型
		List<String> adList = new ArrayList<>();
		adList.add("无广告");
		adList.add("少量广告");
		adList.add("超过广告");
		data.put("adList",adList);
		// 付费模式
		List<String> payList = new ArrayList<>();
		payList.add("完全免费");
		payList.add("会员制");
		payList.add("没钱不给用");
		data.put("payList",payList);
		// 运营方式
		List<String> operationType = new ArrayList<>();
		operationType.add("企业开发");
		operationType.add("独立开发");
		data.put("operationType",operationType);
		// 应用优点
		List<String> featuresType = new ArrayList<>();
		featuresType.add("白嫖");
		featuresType.add("Material Design");
		featuresType.add("神作");
		data.put("featuresType",featuresType);
		return Result.success();
	}

	@PostMapping("/save")
	public Result save(HttpServletRequest request, SoftPOJO softPOJO, @CurrentUser Member member) {
		//softService.create(softPOJO,member,null);
		return Result.success();
	}

	@PostMapping("/list")
	@Audit(action = "上传应用列表")
	public Result list(HttpServletRequest request, Integer type, @CurrentUser Member member, Pageable pageable) {
		if(type==null){
			type = -1;
		}else if(type==0){
			type = -1;
		}else if(type==1){
			type = 1;
		}else if(type==2){
			type = 0;
		}else if(type==3){
			type = 2;
		}else if(type==4){
			type = 100;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select id,logo,name,versionName,DATE_FORMAT(createdDate,'%Y-%m-%d %H:%i:%s') createdDate,status from soft");
		List<Object> args = new ArrayList<>();
		sb.append(" where member_id=?");
		args.add(member.getId());
		if(type>=0){
			sb.append(" and status=?");
			args.add(type);
		}else {
			sb.append(" and status in (0,1,2,100)");
		}
		sb.append(" order by createdDate desc");
		sb.append(" limit ?,?");
		args.add((pageable.getPageNumber()-1)*pageable.getPageSize());
		args.add(pageable.getPageSize());
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sb.toString(), args.toArray());
		maps.forEach(item->{
			//item.put("statusInfo",softService.getStatus(item.get("status")+""));
			item.put("createdDate",DateUtils.formatDateInfo(DateUtils.formatStringToDate(item.get("createdDate")+"","yyyy-MM-dd HH:mm:ss")));
		});
		return Result.success(maps);
	}

	@PostMapping("/delete")
	@Audit(action = "删除应用")
	public Result delete(HttpServletRequest request, Integer softId, @CurrentUser Member member) {
		Soft soft = softService.find(Long.valueOf(softId));
		//if(soft==null || soft.getMember() != member){
		//	return Result.error("应用不存在");
		//}
		soft.setStatus(100);
		softService.update(soft);
		return Result.success();
	}

	@PostMapping("/update")
	@Audit(action = "更新应用信息")
	public Result update(HttpServletRequest request, Integer softId,Integer type, @CurrentUser Member member) {
		Soft soft = softService.find(Long.valueOf(softId));
		/*if(soft==null || soft.getMember() != member){
			return Result.error("应用不存在");
		}*/
		if(type==0){
			// 重新提交
			soft.setStatus(0);
			softService.update(soft);
		}else if(type==1){
			// 取消审核，变成草稿
			soft.setStatus(100);
			softService.update(soft);
		}else if(type==2){
			// 用户删除
			soft.setStatus(101);
		}
		softService.update(soft);
		return Result.success();
	}

	@PostMapping("/loadInfo")
	@Audit(action = "加载投稿信息")
	public Result loadInfo(HttpServletRequest request, @CurrentUser Member member) {
		Map<String,Object> data = jdbcTemplate.queryForMap("select sum(downloads) downloads,sum(reviewCount) reviewCount,sum(donationIcon) donationIcon,(select count(review.id) from review where review.soft_id = soft.id) as reviewCount1 from soft soft where member_id=?",member.getId());
		data.put("downloads",parseNumber(data.get("downloads")+""));

		return Result.success(data);
	}

	private String parseNumber(String downloads) {
		Long l = Long.valueOf(downloads);
		double v = (l + 0.0) / 100000000;
		if(v>0){
			return new BigDecimal(v).setScale(2, RoundingMode.HALF_DOWN)+"亿";
		}
		v = (l + 0.0) / 10000;
		if(v>0){
			return new BigDecimal(v).setScale(2, RoundingMode.HALF_DOWN)+"万";
		}
		return downloads;
	}
}
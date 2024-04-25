package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Admin;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.MemberPointLog;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberPointLogService;
import com.bootx.service.MemberService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminMemberController")
@RequestMapping("/api/admin/member")
public class MemberController extends BaseController {

    @Resource
    private MemberService memberService;

    @Resource
    private MemberPointLogService memberPointLogService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "会员查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable) {
        return Result.success(memberService.findPage(pageable));
    }

    public Result changePoint(@CurrentUser Admin admin,Long point,Long memberId){
        Member member = memberService.find(memberId);
        if(point>0){
            member.setPoint(member.getPoint()+point);
        }
        member.setRemainPoint(member.getRemainPoint()+point);
        memberService.update(member);
        /**
         * 写入积分变动日志
         */
        memberPointLogService.create(member,2,point,"积分调整");


        return Result.success();
    }
}

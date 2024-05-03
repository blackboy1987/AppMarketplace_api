package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Admin;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Carousel;
import com.bootx.entity.Notice;
import com.bootx.service.CarouselService;
import com.bootx.service.NoticeService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminNoticeController")
@RequestMapping("/api/admin/notice")
public class NoticeController extends BaseController {

    @Resource
    private NoticeService noticeService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(Notice notice) {
        noticeService.save(notice);
        return Result.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(Notice notice) {
        Notice parent = noticeService.find(notice.getId());
        Admin current = adminService.getCurrent();
        if(parent==null ){
            return Result.error("数据不存在");
        }
        noticeService.update(notice);
        return Result.success();
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "项目查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable) {
        return Result.success(noticeService.findPage(pageable));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long[] ids) {

        return Result.success();
    }

}

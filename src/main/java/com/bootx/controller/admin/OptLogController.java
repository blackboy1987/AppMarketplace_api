package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.HotSearch;
import com.bootx.service.HotSearchService;
import com.bootx.service.OptLogService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminOptLogController")
@RequestMapping("/api/admin/optLog")
public class OptLogController extends BaseController {

    @Resource
    private OptLogService optLogService;

    /**
     * 保存
     */
    @PostMapping("/list")
    @JsonView({BaseEntity.PageView.class})
    public Result list(Pageable pageable) {
        return Result.success(optLogService.findPage(pageable));
    }

}

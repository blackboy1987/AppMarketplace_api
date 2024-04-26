package com.bootx.controller.admin;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.service.SoftDownloadLogService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminSoftViewLogController")
@RequestMapping("/api/admin/softViewLog")
public class SoftViewLogController extends BaseController {

    @Resource
    private SoftDownloadLogService softDownloadLogService;


    /**
     * 保存
     */
    @PostMapping("/list")
    @JsonView({BaseEntity.PageView.class})
    public Result list(Pageable pageable,Long softId) {
        return Result.success(softDownloadLogService.findPage(pageable,softId));
    }

}

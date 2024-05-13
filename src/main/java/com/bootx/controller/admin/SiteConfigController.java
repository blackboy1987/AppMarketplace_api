package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.AdConfig;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.SiteConfig;
import com.bootx.service.AdConfigService;
import com.bootx.service.SiteConfigService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminSiteConfigController")
@RequestMapping("/api/admin/siteConfig")
public class SiteConfigController extends BaseController {

    @Resource
    private SiteConfigService siteConfigService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(SiteConfig siteConfig) {
        init(siteConfig);
        siteConfigService.save(siteConfig);
        return Result.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(SiteConfig siteConfig) {
        init(siteConfig);

        siteConfigService.update(siteConfig);
        return Result.success();
    }

    private void init(SiteConfig siteConfig) {
        if(siteConfig.getAdType0()==null){
            siteConfig.setAdType0(false);
        }
        if(siteConfig.getAdType1()==null){
            siteConfig.setAdType1(false);
        }
        if(siteConfig.getAdType2()==null){
            siteConfig.setAdType2(false);
        }
        if(siteConfig.getAdType3()==null){
            siteConfig.setAdType3(false);
        }
        if(siteConfig.getAdType4()==null){
            siteConfig.setAdType4(false);
        }
        if(siteConfig.getAdType5()==null){
            siteConfig.setAdType5(false);
        }
        if(siteConfig.getAdType6()==null){
            siteConfig.setAdType6(false);
        }
        if(siteConfig.getAdType0Count()==null){
            siteConfig.setAdType0Count(0L);
        }
        if(siteConfig.getAdType1Count()==null){
            siteConfig.setAdType1Count(0L);
        }
        if(siteConfig.getAdType2Count()==null){
            siteConfig.setAdType2Count(0L);
        }
        if(siteConfig.getAdType3Count()==null){
            siteConfig.setAdType3Count(0L);
        }
        if(siteConfig.getAdType4Count()==null){
            siteConfig.setAdType4Count(0L);
        }
        if(siteConfig.getAdType5Count()==null){
            siteConfig.setAdType5Count(0L);
        }
        if(siteConfig.getAdType6Count()==null){
            siteConfig.setAdType6Count(0L);
        }
        if(siteConfig.getMemberAdType0Count()==null){
            siteConfig.setMemberAdType0Count(0L);
        }
        if(siteConfig.getMemberAdType1Count()==null){
            siteConfig.setMemberAdType1Count(0L);
        }
        if(siteConfig.getMemberAdType2Count()==null){
            siteConfig.setMemberAdType2Count(0L);
        }
        if(siteConfig.getMemberAdType3Count()==null){
            siteConfig.setMemberAdType3Count(0L);
        }
        if(siteConfig.getMemberAdType4Count()==null){
            siteConfig.setMemberAdType4Count(0L);
        }
        if(siteConfig.getMemberAdType5Count()==null){
            siteConfig.setMemberAdType5Count(0L);
        }
        if(siteConfig.getMemberAdType6Count()==null){
            siteConfig.setMemberAdType6Count(0L);
        }
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "项目查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable) {
        return Result.success(siteConfigService.findPage(pageable));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long[] ids) {
        return Result.success();
    }

}

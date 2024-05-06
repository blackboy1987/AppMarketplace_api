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
        siteConfigService.save(siteConfig);
        return Result.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(SiteConfig siteConfig) {
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
        siteConfigService.update(siteConfig);
        return Result.success();
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

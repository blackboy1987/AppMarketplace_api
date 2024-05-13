package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;

/**
 * 网站的配置
 */
@Entity
public class SiteConfig extends BaseEntity<Long>{

    /**
     * 站点名称
     */
    @JsonView({PageView.class})
    private String siteName;

    /**
     * 站点logo
     */
    @JsonView({PageView.class})
    private String siteLogo;

    /**
     * 是否开启横幅广告
     */
    @JsonView({PageView.class})
    private Boolean adType0;

    /**
     * 是否开启模板信息流广告
     */
    @JsonView({PageView.class})
    private Boolean adType1;

    /**
     * 是否开启模板视频信息流
     */
    @JsonView({PageView.class})
    private Boolean adType2;

    /**
     * 是否开启全屏视频
     */
    @JsonView({PageView.class})
    private Boolean adType3;

    /**
     * 是否开启插屏广告
     */
    @JsonView({PageView.class})
    private Boolean adType4;

    /**
     * 是否开启激励视频广告
     */
    @JsonView({PageView.class})
    private Boolean adType5;

    /**
     * 是否开启开屏广告
     */
    @JsonView({PageView.class})
    private Boolean adType6;


    /**
     * 横幅广告次数
     */
    @JsonView({PageView.class})
    private Long memberAdType0Count;

    /**
     * 模板信息流广告次数
     */
    @JsonView({PageView.class})
    private Long memberAdType1Count;

    /**
     * 模板视频信息流次数
     */
    @JsonView({PageView.class})
    private Long memberAdType2Count;

    /**
     * 全屏视频次数
     */
    @JsonView({PageView.class})
    private Long memberAdType3Count;

    /**
     * 插屏广告次数
     */
    @JsonView({PageView.class})
    private Long memberAdType4Count;

    /**
     * 激励视频广告次数
     */
    @JsonView({PageView.class})
    private Long memberAdType5Count;

    /**
     * 开屏广告次数
     */
    @JsonView({PageView.class})
    private Long memberAdType6Count;


    /**
     * 横幅广告次数
     */
    @JsonView({PageView.class})
    private Long adType0Count;

    /**
     * 模板信息流广告次数
     */
    @JsonView({PageView.class})
    private Long adType1Count;

    /**
     * 模板视频信息流次数
     */
    @JsonView({PageView.class})
    private Long adType2Count;

    /**
     * 全屏视频次数
     */
    @JsonView({PageView.class})
    private Long adType3Count;

    /**
     * 插屏广告次数
     */
    @JsonView({PageView.class})
    private Long adType4Count;

    /**
     * 激励视频广告次数
     */
    @JsonView({PageView.class})
    private Long adType5Count;

    /**
     * 开屏广告次数
     */
    @JsonView({PageView.class})
    private Long adType6Count;



    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo;
    }

    public Boolean getAdType0() {
        return adType0;
    }

    public void setAdType0(Boolean adType0) {
        this.adType0 = adType0;
    }

    public Boolean getAdType1() {
        return adType1;
    }

    public void setAdType1(Boolean adType1) {
        this.adType1 = adType1;
    }

    public Boolean getAdType2() {
        return adType2;
    }

    public void setAdType2(Boolean adType2) {
        this.adType2 = adType2;
    }

    public Boolean getAdType3() {
        return adType3;
    }

    public void setAdType3(Boolean adType3) {
        this.adType3 = adType3;
    }

    public Boolean getAdType4() {
        return adType4;
    }

    public void setAdType4(Boolean adType4) {
        this.adType4 = adType4;
    }

    public Boolean getAdType5() {
        return adType5;
    }

    public void setAdType5(Boolean adType5) {
        this.adType5 = adType5;
    }

    public Boolean getAdType6() {
        return adType6;
    }

    public void setAdType6(Boolean adType6) {
        this.adType6 = adType6;
    }

    public Long getMemberAdType0Count() {
        return memberAdType0Count;
    }

    public void setMemberAdType0Count(Long memberAdType0Count) {
        this.memberAdType0Count = memberAdType0Count;
    }

    public Long getMemberAdType1Count() {
        return memberAdType1Count;
    }

    public void setMemberAdType1Count(Long memberAdType1Count) {
        this.memberAdType1Count = memberAdType1Count;
    }

    public Long getMemberAdType2Count() {
        return memberAdType2Count;
    }

    public void setMemberAdType2Count(Long memberAdType2Count) {
        this.memberAdType2Count = memberAdType2Count;
    }

    public Long getMemberAdType3Count() {
        return memberAdType3Count;
    }

    public void setMemberAdType3Count(Long memberAdType3Count) {
        this.memberAdType3Count = memberAdType3Count;
    }

    public Long getMemberAdType4Count() {
        return memberAdType4Count;
    }

    public void setMemberAdType4Count(Long memberAdType4Count) {
        this.memberAdType4Count = memberAdType4Count;
    }

    public Long getMemberAdType5Count() {
        return memberAdType5Count;
    }

    public void setMemberAdType5Count(Long memberAdType5Count) {
        this.memberAdType5Count = memberAdType5Count;
    }

    public Long getMemberAdType6Count() {
        return memberAdType6Count;
    }

    public void setMemberAdType6Count(Long memberAdType6Count) {
        this.memberAdType6Count = memberAdType6Count;
    }

    public Long getAdType0Count() {
        return adType0Count;
    }

    public void setAdType0Count(Long adType0Count) {
        this.adType0Count = adType0Count;
    }

    public Long getAdType1Count() {
        return adType1Count;
    }

    public void setAdType1Count(Long adType1Count) {
        this.adType1Count = adType1Count;
    }

    public Long getAdType2Count() {
        return adType2Count;
    }

    public void setAdType2Count(Long adType2Count) {
        this.adType2Count = adType2Count;
    }

    public Long getAdType3Count() {
        return adType3Count;
    }

    public void setAdType3Count(Long adType3Count) {
        this.adType3Count = adType3Count;
    }

    public Long getAdType4Count() {
        return adType4Count;
    }

    public void setAdType4Count(Long adType4Count) {
        this.adType4Count = adType4Count;
    }

    public Long getAdType5Count() {
        return adType5Count;
    }

    public void setAdType5Count(Long adType5Count) {
        this.adType5Count = adType5Count;
    }

    public Long getAdType6Count() {
        return adType6Count;
    }

    public void setAdType6Count(Long adType6Count) {
        this.adType6Count = adType6Count;
    }
}

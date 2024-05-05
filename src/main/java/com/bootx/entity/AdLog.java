package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;

/**
 * @author black
 */
@Entity
public class AdLog extends BaseEntity<Long>{

    /**
     * 媒体ID
     */
    @JsonView({PageView.class})
    private String mediaId;

    private String adId;

    /**
     * 0:横幅广告
     * 1:模板信息流广告
     * 2:模板视频信息流
     * 3:全屏视频
     * 4:插屏广告
     * 5:激励视频广告
     * 6:开屏广告
     */
    private String adType;

    private Long memberId;

    /**
     * 0： 成功
     * -1： 失败
     */
    private Integer status;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

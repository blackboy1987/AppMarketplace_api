package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

/**
 * 下载记录
 * @author black
 */
@Entity
public class SoftDownloadLog extends BaseEntity<Long>{

    /**
     * 会员id。如果未登录下载，为空
     */
    private Long memberId;

    /**
     * 软件id
     */
    @NotNull
    @Column(nullable = false,updatable = false)
    private Long softId;

    private String ip;

    /**
     * 软件信息
     */
    private String softInfo;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSoftId() {
        return softId;
    }

    public void setSoftId(Long softId) {
        this.softId = softId;
    }

    public String getSoftInfo() {
        return softInfo;
    }

    public void setSoftInfo(String softInfo) {
        this.softInfo = softInfo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

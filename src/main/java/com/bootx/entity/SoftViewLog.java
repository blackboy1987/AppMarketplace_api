package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 下载记录
 * @author black
 */
@Entity
public class SoftViewLog extends BaseEntity<Long>{

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

    /**
     * 软件信息
     */
    @Convert(converter = SoftDownloadLog.SoftInfoConverter.class)
    @Column(length = 2000)
    private Map<String,Object> softInfo = new HashMap<>();

    private String ip;

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

    public Map<String, Object> getSoftInfo() {
        return softInfo;
    }

    public void setSoftInfo(Map<String, Object> softInfo) {
        this.softInfo = softInfo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Converter
    public static class SoftInfoConverter extends BaseAttributeConverter<Map<String,Object>> {

    }
}

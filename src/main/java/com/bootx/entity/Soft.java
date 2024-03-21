package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.*;

/**
 * @author black
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Soft extends BaseEntity<Long>{

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Category> categories = new HashSet<>();

    private String downloadUrl;
    private Integer downloads;
    private String fullName;

    @Column(columnDefinition = "longtext")
    private String introduce;
    private String logo;

    @Column(columnDefinition = "longtext")
    private String memo;
    private String minSdkVersion;
    private String name;
    private String packageName;
    private Long reviewCount;
    private Double score;
    private String size;
    private Integer status;
    private String targetSdkVersion;
    private String updateDate;
    private String updatedContent;
    private String versionCode;
    private String versionName;
    private String appName;

    @Convert(converter = SoftAttrsConverter.class)
    private List<Map<String,String>> softAttrs = new ArrayList<>();

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMinSdkVersion() {
        return minSdkVersion;
    }

    public void setMinSdkVersion(String minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTargetSdkVersion() {
        return targetSdkVersion;
    }

    public void setTargetSdkVersion(String targetSdkVersion) {
        this.targetSdkVersion = targetSdkVersion;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedContent() {
        return updatedContent;
    }

    public void setUpdatedContent(String updatedContent) {
        this.updatedContent = updatedContent;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<Map<String, String>> getSoftAttrs() {
        return softAttrs;
    }

    public void setSoftAttrs(List<Map<String, String>> softAttrs) {
        this.softAttrs = softAttrs;
    }


    @Converter
    public static class SoftAttrsConverter extends BaseAttributeConverter<List<Map<String,String>>>{

    }
}

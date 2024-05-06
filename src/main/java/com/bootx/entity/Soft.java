package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.*;

/**
 * @author black
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Soft extends BaseEntity<Long>{

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Category> categories = new HashSet<>();

    @JsonView({PageView.class,DownloadView.class})
    private String downloadUrl;

    @JsonView({PageView.class,DownloadView.class})
    private String downloadUrl1;

    private Long downloads;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Long viewCount;

    @Column(columnDefinition = "longtext")
    private String introduce;
    @JsonView({PageView.class,DownloadView.class})
    private String logo;

    @Column(columnDefinition = "longtext")
    private String memo;
    @JsonView({PageView.class,DownloadView.class})
    private String name;
    @JsonView({PageView.class,DownloadView.class})
    private String size;
    private Integer status;
    private String updatedContent;
    private String versionCode;

    @JsonView({DownloadView.class})
    private String versionName;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Date updateDate;

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    @OneToMany(mappedBy = "soft",fetch = FetchType.LAZY)
    private Set<SoftImage> softImages = new HashSet<>();
    public Set<SoftImage> getSoftImages() {
        return softImages;
    }

    public void setSoftImages(Set<SoftImage> softImages) {
        this.softImages = softImages;
    }


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

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Map<String, String>> getSoftAttrs() {
        return softAttrs;
    }

    public void setSoftAttrs(List<Map<String, String>> softAttrs) {
        this.softAttrs = softAttrs;
    }


    @Converter
    public static class SoftAttrsConverter extends BaseAttributeConverter<List<Map<String,String>>>{

    }

    public interface DownloadView extends DefaultView{}

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDownloadUrl1() {
        return downloadUrl1;
    }

    public void setDownloadUrl1(String downloadUrl1) {
        this.downloadUrl1 = downloadUrl1;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

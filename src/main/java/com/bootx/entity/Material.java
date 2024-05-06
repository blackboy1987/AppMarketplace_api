package com.bootx.entity;

import jakarta.persistence.Entity;

/**
 * @author black
 */
@Entity
public class Material extends BaseEntity<Long> {

    private String name;

    private String url;

    private Long adminId;

    private Long size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}

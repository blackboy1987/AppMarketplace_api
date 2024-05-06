package com.bootx.entity;

import jakarta.persistence.Entity;

/**
 * @author black
 */
@Entity
public class CollectLog extends BaseEntity<Long>{
    private Long memberId;

    private Long softId;

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
}

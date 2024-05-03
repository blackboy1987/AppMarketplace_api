package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Date;

/**
 * @author black
 */
@Entity
public class Notice extends OrderedEntity<Long>{

    @JsonView({PageView.class})
    private String title;

    @JsonView({PageView.class})
    @Column(columnDefinition = "longtext")
    private String content;

    @JsonView({PageView.class})
    private Date beginDate;

    @JsonView({PageView.class})
    private Date endDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * @author black
 */
@Entity
public class Setting extends BaseEntity<Long>{

    private String name;

    private String logo;

    @Column(length = 2000)
    private String homeCenterBar;
    @Column(length = 2000)
    private String homeBottomBar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getHomeCenterBar() {
        return homeCenterBar;
    }

    public void setHomeCenterBar(String homeCenterBar) {
        this.homeCenterBar = homeCenterBar;
    }

    public String getHomeBottomBar() {
        return homeBottomBar;
    }

    public void setHomeBottomBar(String homeBottomBar) {
        this.homeBottomBar = homeBottomBar;
    }
}

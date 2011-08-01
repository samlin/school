package com.lxit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FriendLink extends BaseEntity {

    private static final long serialVersionUID = 3019642557500517628L;

    private String name;// 名称
    private String logo;// Logo
    private String url;// 链接地址
    private Integer orderList;// 排序

    @Column(nullable = false)
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

    @Column(nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(nullable = false)
    public Integer getOrderList() {
        return orderList;
    }

    public void setOrderList(Integer orderList) {
        this.orderList = orderList;
    }

}
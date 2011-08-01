package com.lxit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 实体类 - 页面底部信息
 */

@Entity
public class Footer extends BaseEntity {

    private static final long serialVersionUID = 8309391131807288450L;

    public static final String FOOTER_ID = "1";// 记录ID

    private String content;// 内容

    @Column(length = 10000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

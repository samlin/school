package com.lxit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "actionClassName", "actionMethodName" }) })
public class LogConfig extends BaseEntity {

    private static final long serialVersionUID = -240939735880402675L;

    private String operationName;// 操作名称
    private String actionClassName;// 需要进行日志记录的Action名称
    private String actionMethodName;// 需要进行日志记录的方法名称
    private String description;// 描述

    @Column(nullable = false, unique = true)
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    @Column(nullable = false)
    public String getActionClassName() {
        return actionClassName;
    }

    public void setActionClassName(String actionClassName) {
        this.actionClassName = actionClassName;
    }

    @Column(length = 5000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getActionMethodName() {
        return actionMethodName;
    }

    public void setActionMethodName(String actionMethodName) {
        this.actionMethodName = actionMethodName;
    }

}
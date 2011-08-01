package com.lxit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 实体类 - 订单日志
 */

@Entity
public class OrderLog extends BaseEntity {

    private static final long serialVersionUID = -8602286694018650587L;

    // 订单日志类型（订单创建、订单修改、订单支付、订单退款、订单发货、订单退货、订单完成、订单作废）
    public enum OrderLogType {
        create, modify, payment, refund, shipping, reship, completed, invalid
    };

    private OrderLogType orderLogType;// 订单日志类型
    private String orderSn;// 订单编号
    private String operator;// 操作员
    private String info;// 日志信息

    private Order order;// 订单

    @Enumerated
    @Column(nullable = false, updatable = false)
    public OrderLogType getOrderLogType() {
        return orderLogType;
    }

    public void setOrderLogType(OrderLogType orderLogType) {
        this.orderLogType = orderLogType;
    }

    @Column(nullable = false, updatable = false)
    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    @Column(updatable = false)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(updatable = false, length = 5000)
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
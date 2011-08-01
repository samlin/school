package com.lxit.service;

import com.lxit.bean.Pager;
import com.lxit.entity.Member;
import com.lxit.entity.Order;

/**
 * Service接口 - 订单
 */

public interface OrderService extends BaseService<Order, String> {

    /**
     * 获取最后生成的订单编号
     * 
     * @return 订单编号
     */
    public String getLastOrderSn();

    /**
     * 根据Member、Pager获取订单分页对象
     * 
     * @param member
     *            Member对象
     * 
     * @param pager
     *            Pager对象
     * 
     * @return 订单分页对象
     */
    public Pager getOrderPager(Member member, Pager pager);

    /**
     * 获取未处理订单数
     * 
     * @return 未处理订单数
     */
    public Long getUnprocessedOrderCount();

    /**
     * 获取已支付未发货订单数（不包含已完成或已作废订单）
     * 
     * @return 已支付未发货订单数
     */
    public Long getPaidUnshippedOrderCount();

}
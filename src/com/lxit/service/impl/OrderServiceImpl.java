package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.bean.Pager;
import com.lxit.dao.OrderDao;
import com.lxit.entity.Member;
import com.lxit.entity.Order;
import com.lxit.service.OrderService;
import com.lxit.util.SerialNumberUtil;

/**
 * Service实现类 - 订单
 */

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    public void setBaseDao(OrderDao orderDao) {
        super.setBaseDao(orderDao);
    }

    @Override
    public String getLastOrderSn() {
        return orderDao.getLastOrderSn();
    }

    @Override
    public Pager getOrderPager(Member member, Pager pager) {
        return orderDao.getOrderPager(member, pager);
    }

    @Override
    public Long getUnprocessedOrderCount() {
        return orderDao.getUnprocessedOrderCount();
    }

    @Override
    public Long getPaidUnshippedOrderCount() {
        return orderDao.getPaidUnshippedOrderCount();
    }

    // 重写对象，保存时自动设置订单编号
    @Override
    public String save(Order order) {
        order.setOrderSn(SerialNumberUtil.buildOrderSn());
        return super.save(order);
    }

}
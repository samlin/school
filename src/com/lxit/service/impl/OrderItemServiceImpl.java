package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.OrderItemDao;
import com.lxit.entity.OrderItem;
import com.lxit.service.OrderItemService;

/**
 * Service实现类 - 订单项
 */

@Service
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, String> implements OrderItemService {

    @Resource
    public void setBaseDao(OrderItemDao orderItemDao) {
        super.setBaseDao(orderItemDao);
    }

}
package com.lxit.dao.impl;

import org.springframework.stereotype.Repository;

import com.lxit.dao.OrderItemDao;
import com.lxit.entity.OrderItem;

/**
 * Dao实现类 - 订单项
 */

@Repository
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, String> implements OrderItemDao {

}
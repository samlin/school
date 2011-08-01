package com.lxit.dao.impl;

import org.springframework.stereotype.Repository;

import com.lxit.dao.OrderLogDao;
import com.lxit.entity.OrderLog;

/**
 * Dao实现类 - 订单日志
 */

@Repository
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog, String> implements OrderLogDao {

}
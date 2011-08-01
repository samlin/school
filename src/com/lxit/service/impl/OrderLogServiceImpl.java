package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.OrderLogDao;
import com.lxit.entity.OrderLog;
import com.lxit.service.OrderLogService;

/**
 * Service实现类 - 订单日志
 */

@Service
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLog, String> implements OrderLogService {

    @Resource
    public void setBaseDao(OrderLogDao orderLogDao) {
        super.setBaseDao(orderLogDao);
    }

}
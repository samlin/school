package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.DeliveryItemDao;
import com.lxit.entity.DeliveryItem;
import com.lxit.service.DeliveryItemService;

/**
 * Service实现类 - 发货项
 */

@Service
public class DeliveryItemServiceImpl extends BaseServiceImpl<DeliveryItem, String> implements DeliveryItemService {

    @Resource
    public void setBaseDao(DeliveryItemDao deliveryItemDao) {
        super.setBaseDao(deliveryItemDao);
    }

}
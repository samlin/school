package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.DeliveryCorpDao;
import com.lxit.entity.DeliveryCorp;
import com.lxit.service.DeliveryCorpService;

/**
 * Service实现类 - 物流公司
 */

@Service
public class DeliveryCorpServiceImpl extends BaseServiceImpl<DeliveryCorp, String> implements DeliveryCorpService {

    @Resource
    public void setBaseDao(DeliveryCorpDao deliveryCorpDao) {
        super.setBaseDao(deliveryCorpDao);
    }

}

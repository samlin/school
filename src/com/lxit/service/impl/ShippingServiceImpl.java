package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.ShippingDao;
import com.lxit.entity.Shipping;
import com.lxit.service.ShippingService;
import com.lxit.util.SerialNumberUtil;

/**
 * Service实现类 - 发货
 */

@Service
public class ShippingServiceImpl extends BaseServiceImpl<Shipping, String> implements ShippingService {

    @Resource
    private ShippingDao shippingDao;

    @Resource
    public void setBaseDao(ShippingDao shippingDao) {
        super.setBaseDao(shippingDao);
    }

    @Override
    public String getLastShippingSn() {
        return shippingDao.getLastShippingSn();
    }

    // 重写对象，保存时自动设置发货编号
    @Override
    public String save(Shipping shipping) {
        shipping.setShippingSn(SerialNumberUtil.buildShippingSn());
        return super.save(shipping);
    }

}
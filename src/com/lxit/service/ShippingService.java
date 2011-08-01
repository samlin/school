package com.lxit.service;

import com.lxit.entity.Shipping;

/**
 * Service接口 - 发货
 */

public interface ShippingService extends BaseService<Shipping, String> {

    /**
     * 获取最后生成的发货编号
     * 
     * @return 发货编号
     */
    public String getLastShippingSn();

}
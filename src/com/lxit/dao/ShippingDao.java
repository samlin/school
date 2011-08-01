package com.lxit.dao;

import com.lxit.entity.Shipping;

/**
 * Dao接口 - 发货
 */

public interface ShippingDao extends BaseDao<Shipping, String> {

    /**
     * 获取最后生成的发货编号
     * 
     * @return 发货编号
     */
    public String getLastShippingSn();

}
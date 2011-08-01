package com.lxit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lxit.dao.ShippingDao;
import com.lxit.entity.Shipping;

/**
 * Dao实现类 - 发货
 */

@Repository
public class ShippingDaoImpl extends BaseDaoImpl<Shipping, String> implements ShippingDao {

    @Override
    @SuppressWarnings("unchecked")
    public String getLastShippingSn() {
        String hql = "from Shipping as shipping order by shipping.createDate desc";
        List<Shipping> shippingList = getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
        if (shippingList != null && shippingList.size() > 0) {
            return shippingList.get(0).getShippingSn();
        } else {
            return null;
        }
    }

}
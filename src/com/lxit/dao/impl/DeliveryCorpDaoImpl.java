package com.lxit.dao.impl;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lxit.dao.DeliveryCorpDao;
import com.lxit.entity.DeliveryCorp;
import com.lxit.entity.DeliveryType;

/**
 * Dao实现类 - 物流公司
 */

@Repository
public class DeliveryCorpDaoImpl extends BaseDaoImpl<DeliveryCorp, String> implements DeliveryCorpDao {

    // 关联处理
    @Override
    public void delete(DeliveryCorp deliveryCorp) {
        Set<DeliveryType> deliveryTypeSet = deliveryCorp.getDeliveryTypeSet();
        if (deliveryTypeSet != null) {
            for (DeliveryType deliveryType : deliveryTypeSet) {
                deliveryType.setDefaultDeliveryCorp(null);
            }
        }
        super.delete(deliveryCorp);
    }

    // 关联处理
    @Override
    public void delete(String id) {
        DeliveryCorp deliveryCorp = load(id);
        this.delete(deliveryCorp);
    }

    // 关联处理
    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            DeliveryCorp deliveryCorp = load(id);
            this.delete(deliveryCorp);
        }
    }

}
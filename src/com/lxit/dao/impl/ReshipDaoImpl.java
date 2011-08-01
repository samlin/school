package com.lxit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lxit.dao.ReshipDao;
import com.lxit.entity.Reship;

/**
 * Dao实现类 - 退货
 */

@Repository
public class ReshipDaoImpl extends BaseDaoImpl<Reship, String> implements ReshipDao {

    @Override
    @SuppressWarnings("unchecked")
    public String getLastReshipSn() {
        String hql = "from Reship as reship order by reship.createDate desc";
        List<Reship> reshipList = getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
        if (reshipList != null && reshipList.size() > 0) {
            return reshipList.get(0).getReshipSn();
        } else {
            return null;
        }
    }

}
package com.lxit.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.lxit.bean.Pager;
import com.lxit.dao.IncDao;
import com.lxit.entity.Inc;

@Repository
public class IncDaoImpl extends BaseDaoImpl<Inc, String> implements IncDao {
    @Override
    public Pager findByPager(Pager pager) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inc.class);
        return this.findByPager(pager, detachedCriteria);
    }
}

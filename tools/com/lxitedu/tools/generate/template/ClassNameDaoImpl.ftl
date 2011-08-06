package com.lxit.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.lxit.bean.Pager;
import com.lxit.dao.${className}Dao;
import com.lxit.entity.${className};


@Repository
public class ${className}DaoImpl extends BaseDaoImpl<${className}, String> implements ${className}Dao {
    @Override
    public Pager findByPager(Pager pager) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(${className}.class);
        return this.findByPager(pager, detachedCriteria);
    }
}
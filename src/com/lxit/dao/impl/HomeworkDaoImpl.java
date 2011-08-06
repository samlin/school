package com.lxit.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.lxit.bean.Pager;
import com.lxit.dao.HomeworkDao;
import com.lxit.entity.Homework;


@Repository
public class HomeworkDaoImpl extends BaseDaoImpl<Homework, String> implements HomeworkDao {
    @Override
    public Pager findByPager(Pager pager) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Homework.class);
        return this.findByPager(pager, detachedCriteria);
    }
}
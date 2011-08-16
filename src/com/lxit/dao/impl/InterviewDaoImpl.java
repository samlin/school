package com.lxit.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.lxit.bean.Pager;
import com.lxit.dao.InterviewDao;
import com.lxit.entity.Interview;


@Repository
public class InterviewDaoImpl extends BaseDaoImpl<Interview, String> implements InterviewDao {
    @Override
    public Pager findByPager(Pager pager) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Interview.class);
        return this.findByPager(pager, detachedCriteria);
    }
}
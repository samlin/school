package com.lxit.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.lxit.bean.Pager;
import com.lxit.dao.StudentDao;
import com.lxit.entity.Student;

/**
 * Dao实现类 - 会员
 */

@Repository
public class StudentDaoImpl extends BaseDaoImpl<Student, String> implements StudentDao {
    // 根据orderList排序
    @Override
    public Pager findByPager(Pager pager) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Student.class);
        return this.findByPager(pager, detachedCriteria);
    }
}
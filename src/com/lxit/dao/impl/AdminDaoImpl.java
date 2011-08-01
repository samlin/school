package com.lxit.dao.impl;

import org.springframework.stereotype.Repository;

import com.lxit.dao.AdminDao;
import com.lxit.entity.Admin;

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao {

    @Override
    @SuppressWarnings("unchecked")
    public boolean isExistByUsername(String username) {
        String hql = "from Admin admin where lower(admin.username) = lower(?)";
        Admin admin = (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
        if (admin != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Admin getAdminByUsername(String username) {
        String hql = "from Admin admin where lower(admin.username) = lower(?)";
        return (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
    }

}
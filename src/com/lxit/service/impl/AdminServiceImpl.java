package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lxit.dao.AdminDao;
import com.lxit.entity.Admin;
import com.lxit.service.AdminService;

/**
 * Service实现类 - 管理员
 */

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService {

    @Resource
    private AdminDao adminDao;

    @Resource
    public void setBaseDao(AdminDao adminDao) {
        super.setBaseDao(adminDao);
    }

    @Override
    public Admin getLoginAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null || !(principal instanceof Admin)) {
            return null;
        } else {
            return (Admin) principal;
        }
    }

    @Override
    public Admin loadLoginAdmin() {
        Admin admin = getLoginAdmin();
        if (admin == null) {
            return null;
        } else {
            return adminDao.load(admin.getId());
        }
    }

    @Override
    public boolean isExistByUsername(String username) {
        return adminDao.isExistByUsername(username);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminDao.getAdminByUsername(username);
    }

}
package com.lxit.dao;

import com.lxit.entity.Admin;

public interface AdminDao extends BaseDao<Admin, String> {

    /**
     * 根据用户名判断此用户是否存在（不区分大小写）
     * 
     */
    public boolean isExistByUsername(String username);

    /**
     * 根据用户名获取管理员对象，若管理员不存在，则返回null（不区分大小写）
     * 
     */
    public Admin getAdminByUsername(String username);

}
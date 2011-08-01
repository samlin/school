package com.lxit.dao.impl;

import org.springframework.stereotype.Repository;

import com.lxit.dao.RoleDao;
import com.lxit.entity.Role;

/**
 * Dao实现类 - 角色
 */

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, String> implements RoleDao {

    // 忽略isSystem=true的对象
    @Override
    public void delete(Role role) {
        if (role.getIsSystem()) {
            return;
        }
        super.delete(role);
    }

    // 忽略isSystem=true的对象
    @Override
    public void delete(String id) {
        Role role = load(id);
        this.delete(role);
    }

    // 忽略isSystem=true的对象
    @Override
    public void delete(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                this.delete(id);
            }
        }
    }

    // 设置isSystem=false
    @Override
    public String save(Role role) {
        role.setIsSystem(false);
        return super.save(role);
    }

    // 忽略isSystem=true的对象
    @Override
    public void update(Role role) {
        if (role.getIsSystem()) {
            return;
        }
        super.update(role);
    }

}
package com.lxit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lxit.dao.LogConfigDao;
import com.lxit.entity.LogConfig;

@Repository
public class LogConfigDaoImpl extends BaseDaoImpl<LogConfig, String> implements LogConfigDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<LogConfig> getLogConfigList(String actionClassName) {
        String hql = "from LogConfig as logConfig where logConfig.actionClassName = ?";
        return getSession().createQuery(hql).setParameter(0, actionClassName).list();
    }

}

package com.lxit.service.impl;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;

import com.lxit.dao.ResourceDao;
import com.lxit.entity.Resource;
import com.lxit.service.ResourceService;
import com.lxit.util.SpringUtil;

/**
 * Service实现类 - 资源
 */

@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, String> implements ResourceService {

    @javax.annotation.Resource
    ResourceDao resourceDao;

    @javax.annotation.Resource
    public void setBaseDao(ResourceDao resourceDao) {
        super.setBaseDao(resourceDao);
    }

    // 重写方法，删除时刷新SpringSecurity权限信息
    @Override
    public void delete(Resource resource) {
        resourceDao.delete(resource);
        resourceDao.flush();
        flushSpringSecurity();
    }

    // 重写方法，删除时刷新SpringSecurity权限信息
    @Override
    public void delete(String id) {
        Resource resource = resourceDao.load(id);
        this.delete(resource);
    }

    // 重写方法，删除时刷新SpringSecurity权限信息
    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            Resource resource = resourceDao.load(id);
            resourceDao.delete(resource);
        }
        resourceDao.flush();
        flushSpringSecurity();
    }

    // 重写方法，保存时刷新SpringSecurity权限信息
    @Override
    public String save(Resource resource) {
        String id = resourceDao.save(resource);
        resourceDao.flush();
        flushSpringSecurity();
        return id;
    }

    // 重写方法，更新时刷新SpringSecurity权限信息
    @Override
    public void update(Resource resource) {
        resourceDao.update(resource);
        resourceDao.flush();
        flushSpringSecurity();
    }

    // 刷新SpringSecurity权限信息
    private void flushSpringSecurity() {
        try {
            FactoryBean factoryBean = (FactoryBean) SpringUtil.getBean("&adminSecurityDefinitionSource");
            FilterInvocationDefinitionSource filterInvocationDefinitionSource = (FilterInvocationDefinitionSource) factoryBean
                    .getObject();
            FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor) SpringUtil
                    .getBean("filterSecurityInterceptor");
            filterSecurityInterceptor.setObjectDefinitionSource(filterInvocationDefinitionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
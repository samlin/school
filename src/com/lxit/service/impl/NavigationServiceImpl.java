package com.lxit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.lxit.dao.NavigationDao;
import com.lxit.entity.Navigation;
import com.lxit.service.NavigationService;

/**
 * Service实现类 - 导航
 */

@Service
public class NavigationServiceImpl extends BaseServiceImpl<Navigation, String> implements NavigationService {

    @Resource
    private NavigationDao navigationDao;

    @Resource
    public void setBaseDao(NavigationDao navigationDao) {
        super.setBaseDao(navigationDao);
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<Navigation> getTopNavigationList() {
        List<Navigation> topNavigationList = navigationDao.getTopNavigationList();
        if (topNavigationList != null) {
            for (Navigation topNavigation : topNavigationList) {
                Hibernate.initialize(topNavigation);
            }
        }
        return topNavigationList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<Navigation> getMiddleNavigationList() {
        List<Navigation> middleNavigationList = navigationDao.getMiddleNavigationList();
        if (middleNavigationList != null) {
            for (Navigation middleNavigation : middleNavigationList) {
                Hibernate.initialize(middleNavigation);
            }
        }
        return middleNavigationList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<Navigation> getBottomNavigationList() {
        List<Navigation> bottomNavigationList = navigationDao.getBottomNavigationList();
        if (bottomNavigationList != null) {
            for (Navigation bottomNavigation : bottomNavigationList) {
                Hibernate.initialize(bottomNavigation);
            }
        }
        return bottomNavigationList;
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(Navigation entity) {
        navigationDao.delete(entity);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String id) {
        navigationDao.delete(id);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String[] ids) {
        navigationDao.delete(ids);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public String save(Navigation entity) {
        return navigationDao.save(entity);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void update(Navigation entity) {
        navigationDao.update(entity);
    }

}

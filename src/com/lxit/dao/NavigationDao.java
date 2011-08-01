package com.lxit.dao;

import java.util.List;

import com.lxit.entity.Navigation;

/**
 * Dao接口 - 导航
 */

public interface NavigationDao extends BaseDao<Navigation, String> {

    /**
     * 获取顶部Navigation对象集合（只包含isVisible=true的对象）
     * 
     * @return Navigation对象集合
     * 
     */
    public List<Navigation> getTopNavigationList();

    /**
     * 获取中间Navigation对象集合（只包含isVisible=true的对象）
     * 
     * @return Navigation对象集合
     * 
     */
    public List<Navigation> getMiddleNavigationList();

    /**
     * 获取底部Navigation对象集合（只包含isVisible=true的对象）
     * 
     * @return Navigation对象集合
     * 
     */
    public List<Navigation> getBottomNavigationList();

}

package com.lxit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.lxit.dao.AreaDao;
import com.lxit.entity.Area;

/**
 * Dao实现类 - 地区
 */

@Repository
public class AreaDaoImpl extends BaseDaoImpl<Area, String> implements AreaDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Area> getRootAreaList() {
        String hql = "from Area area where area.parent is null";
        return getSession().createQuery(hql).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Area> getParentAreaList(Area area) {
        String hql = "from Area area where area != ? and area.id in(:ids)";
        String[] ids = area.getPath().split(Area.PATH_SEPARATOR);
        return getSession().createQuery(hql).setParameter(0, area).setParameterList("ids", ids).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Area> getChildrenAreaList(Area area) {
        String hql = "from Area area where area != ? and area.path like ?";
        return getSession().createQuery(hql).setParameter(0, area).setParameter(1, area.getPath() + "%").list();
    }

    @Override
    public Boolean isNameUnique(String parentId, String oldName, String newName) {
        if (StringUtils.equalsIgnoreCase(newName, oldName)) {
            return true;
        }
        if (StringUtils.isEmpty(parentId)) {
            String hql = "from Area area where area.name = ? and area.parent is null";
            return getSession().createQuery(hql).setParameter(0, newName).uniqueResult() == null;
        } else {
            String hql = "from Area area where area.name = ? and area.parent.id = ?";
            return getSession().createQuery(hql).setParameter(0, newName).setParameter(1, parentId).uniqueResult() == null;
        }
    }

    // 设置path值
    @Override
    public String save(Area area) {
        String id = super.save(area);
        Area parent = area.getParent();
        if (parent != null) {
            String parentPath = parent.getPath();
            area.setPath(parentPath + Area.PATH_SEPARATOR + id);
        } else {
            area.setPath(id);
        }
        super.update(area);
        return id;
    }

    // 设置path值
    @Override
    public void update(Area area) {
        Area parent = area.getParent();
        if (parent != null) {
            String parentPath = parent.getPath();
            area.setPath(parentPath + Area.PATH_SEPARATOR + area.getId());
        } else {
            area.setPath(area.getId());
        }
        super.update(area);
    }

}
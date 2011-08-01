package com.lxit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.lxit.dao.AreaDao;
import com.lxit.entity.Area;
import com.lxit.service.AreaService;

/**
 * Service实现类 - 地区
 */

@Service
public class AreaServiceImpl extends BaseServiceImpl<Area, String> implements AreaService {

    @Resource
    private AreaDao areaDao;

    @Resource
    public void setBaseDao(AreaDao areaDao) {
        super.setBaseDao(areaDao);
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<Area> getRootAreaList() {
        List<Area> rootAreaList = areaDao.getRootAreaList();
        if (rootAreaList != null) {
            for (Area rootArea : rootAreaList) {
                Hibernate.initialize(rootArea);
            }
        }
        return rootAreaList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<Area> getParentAreaList(Area area) {
        List<Area> parentAreaList = areaDao.getParentAreaList(area);
        if (parentAreaList != null) {
            for (Area parentArea : parentAreaList) {
                Hibernate.initialize(parentArea);
            }
        }
        return parentAreaList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<Area> getChildrenAreaList(Area area) {
        List<Area> childrenAreaList = areaDao.getChildrenAreaList(area);
        if (childrenAreaList != null) {
            for (Area childrenArea : childrenAreaList) {
                Hibernate.initialize(childrenArea);
            }
        }
        return childrenAreaList;
    }

    @Override
    public Boolean isNameUnique(String parentId, String oldName, String newName) {
        return areaDao.isNameUnique(parentId, oldName, newName);
    }

    @Override
    public Boolean isAreaPath(String areaPath) {
        Area area = areaDao.get("path", areaPath);
        if (area == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    @Cacheable(modelId = "caching")
    public String getAreaString(Area area) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Area> parentAreaList = this.getParentAreaList(area);
        if (parentAreaList != null) {
            for (Area parentArea : parentAreaList) {
                stringBuilder.append(parentArea.getName());
            }
        }
        stringBuilder.append(area.getName());
        return stringBuilder.toString();
    }

    @Override
    @Cacheable(modelId = "caching")
    public String getAreaString(String areaPath) {
        if (!isAreaPath(areaPath)) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String[] ids = areaPath.split(Area.PATH_SEPARATOR);
        for (String id : ids) {
            Area area = super.load(id);
            stringBuffer.append(area.getName());
        }
        return stringBuffer.toString();
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(Area entity) {
        areaDao.delete(entity);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String id) {
        areaDao.delete(id);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String[] ids) {
        areaDao.delete(ids);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public String save(Area entity) {
        return areaDao.save(entity);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void update(Area entity) {
        areaDao.update(entity);
    }

}
package com.lxit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.lxit.dao.MemberAttributeDao;
import com.lxit.entity.MemberAttribute;
import com.lxit.service.MemberAttributeService;

/**
 * Service实现类 - 会员属性
 */

@Service
public class MemberAttributeServiceImpl extends BaseServiceImpl<MemberAttribute, String> implements
        MemberAttributeService {

    @Resource
    private MemberAttributeDao memberAttributeDao;

    @Resource
    public void setBaseDao(MemberAttributeDao memberAttributeDao) {
        super.setBaseDao(memberAttributeDao);
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<MemberAttribute> getEnabledMemberAttributeList() {
        List<MemberAttribute> enabledMemberAttributeList = memberAttributeDao.getEnabledMemberAttributeList();
        if (enabledMemberAttributeList != null) {
            for (MemberAttribute enabledMemberAttribute : enabledMemberAttributeList) {
                Hibernate.initialize(enabledMemberAttribute);
            }
        }
        return enabledMemberAttributeList;
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(MemberAttribute memberAttribute) {
        super.delete(memberAttribute);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String[] ids) {
        super.delete(ids);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public String save(MemberAttribute memberAttribute) {
        return super.save(memberAttribute);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void update(MemberAttribute memberAttribute) {
        super.update(memberAttribute);
    }

}
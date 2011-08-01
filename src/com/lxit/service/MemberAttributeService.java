package com.lxit.service;

import java.util.List;

import com.lxit.entity.MemberAttribute;

/**
 * Service接口 - 会员属性
 */

public interface MemberAttributeService extends BaseService<MemberAttribute, String> {

    /**
     * 获取已启用的会员注册项.
     * 
     * @return 已启用的会员注册项集合.
     */
    public List<MemberAttribute> getEnabledMemberAttributeList();

}
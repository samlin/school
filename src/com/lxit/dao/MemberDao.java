package com.lxit.dao;

import com.lxit.entity.Member;

/**
 * Dao接口 - 会员
 */

public interface MemberDao extends BaseDao<Member, String> {

    /**
     * 根据用户名判断此用户是否存在（不区分大小写）
     * 
     */
    public boolean isExistByUsername(String username);

    /**
     * 根据用户名获取会员对象，若会员不存在，则返回null（不区分大小写）
     * 
     */
    public Member getMemberByUsername(String username);

}
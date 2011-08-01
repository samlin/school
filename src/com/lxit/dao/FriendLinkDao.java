package com.lxit.dao;

import java.util.List;

import com.lxit.entity.FriendLink;

/**
 * Dao接口 - 友情链接
 */

public interface FriendLinkDao extends BaseDao<FriendLink, String> {

    /**
     * 获取所有图片友情链接集合;
     * 
     * @return 图片友情链接集合
     * 
     */
    public List<FriendLink> getPictureFriendLinkList();

    /**
     * 获取所有文字友情链接集合;
     * 
     * @return 图片友情链接集合
     * 
     */
    public List<FriendLink> getTextFriendLinkList();

}

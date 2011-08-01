package com.lxit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lxit.dao.MemberRankDao;
import com.lxit.entity.MemberRank;

/**
 * Dao实现类 - 会员分类
 */

@Repository
public class MemberRankDaoImpl extends BaseDaoImpl<MemberRank, String> implements MemberRankDao {

    @Override
    public MemberRank getDefaultMemberRank() {
        String hql = "from MemberRank as memberRank where memberRank.isDefault = ?";
        MemberRank defaultMemberRank = (MemberRank) getSession().createQuery(hql).setParameter(0, true).uniqueResult();
        if (defaultMemberRank == null) {
            hql = "from MemberRank as memberRank order by memberRank.createDate asc";
            defaultMemberRank = (MemberRank) getSession().createQuery(hql).setMaxResults(1).uniqueResult();
        }
        return defaultMemberRank;
    }

    @Override
    public MemberRank getMemberRankByPoint(Integer point) {
        String hql = "from MemberRank as memberRank where memberRank.point = ?";
        return (MemberRank) getSession().createQuery(hql).setParameter(0, point).uniqueResult();
    }

    @Override
    public MemberRank getUpMemberRankByPoint(Integer point) {
        String hql = "from MemberRank as memberRank where memberRank.point <= ? order by memberRank.point desc";
        return (MemberRank) getSession().createQuery(hql).setParameter(0, point).setMaxResults(1).uniqueResult();
    }

    // 重写方法，保存时若对象isDefault=true，则设置其它对象isDefault值为false
    @Override
    @SuppressWarnings("unchecked")
    public String save(MemberRank memberRank) {
        if (memberRank.getIsDefault()) {
            String hql = "from MemberRank memberRank where memberRank.isDefault = ?";
            List<MemberRank> memberRankList = getSession().createQuery(hql).setParameter(0, true).list();
            if (memberRankList != null) {
                for (MemberRank r : memberRankList) {
                    r.setIsDefault(false);
                }
            }
        }
        return super.save(memberRank);
    }

    // 重写方法，更新时若对象isDefault=true，则设置其它对象isDefault值为false
    @Override
    @SuppressWarnings("unchecked")
    public void update(MemberRank memberRank) {
        if (memberRank.getIsDefault()) {
            String hql = "from MemberRank memberRank where memberRank.isDefault = ? and memberRank != ?";
            List<MemberRank> memberRankList = getSession().createQuery(hql).setParameter(0, true)
                    .setParameter(1, memberRank).list();
            if (memberRankList != null) {
                for (MemberRank r : memberRankList) {
                    r.setIsDefault(false);
                }
            }
        }
        super.update(memberRank);
    }

}
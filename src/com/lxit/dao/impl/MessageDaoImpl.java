package com.lxit.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lxit.bean.Pager;
import com.lxit.dao.MessageDao;
import com.lxit.entity.Member;
import com.lxit.entity.Message;
import com.lxit.entity.Message.DeleteStatus;

/**
 * Dao实现类 - 消息
 */

@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message, String> implements MessageDao {

    @Override
    public Pager getMemberInboxPager(Member member, Pager pager) {
        if (pager == null) {
            pager = new Pager();
            pager.setPageSize(Message.DEFAULT_MESSAGE_LIST_PAGE_SIZE);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
        detachedCriteria.add(Restrictions.eq("toMember", member));
        detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
        detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.toDelete));
        return super.findByPager(pager, detachedCriteria);
    }

    @Override
    public Pager getMemberOutboxPager(Member member, Pager pager) {
        if (pager == null) {
            pager = new Pager();
        }
        if (pager.getPageSize() == null) {
            pager.setPageSize(Message.DEFAULT_MESSAGE_LIST_PAGE_SIZE);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
        detachedCriteria.add(Restrictions.eq("fromMember", member));
        detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
        detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
        return super.findByPager(pager, detachedCriteria);
    }

    @Override
    public Pager getMemberDraftboxPager(Member member, Pager pager) {
        if (pager == null) {
            pager = new Pager();
        }
        if (pager.getPageSize() == null) {
            pager.setPageSize(Message.DEFAULT_MESSAGE_LIST_PAGE_SIZE);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
        detachedCriteria.add(Restrictions.eq("fromMember", member));
        detachedCriteria.add(Restrictions.eq("isSaveDraftbox", true));
        detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
        return super.findByPager(pager, detachedCriteria);
    }

    @Override
    public Pager getAdminInboxPager(Pager pager) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
        detachedCriteria.add(Restrictions.isNull("toMember"));
        detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
        detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.toDelete));
        return super.findByPager(pager, detachedCriteria);
    }

    @Override
    public Pager getAdminOutboxPager(Pager pager) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Message.class);
        detachedCriteria.add(Restrictions.isNull("fromMember"));
        detachedCriteria.add(Restrictions.eq("isSaveDraftbox", false));
        detachedCriteria.add(Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
        return super.findByPager(pager, detachedCriteria);
    }

    @Override
    public Long getUnreadMessageCount(Member member) {
        String hql = "select count(*) from Message as message where message.toMember = ? and message.isRead = ? and message.isSaveDraftbox = ? and message.deleteStatus != ?";
        return (Long) getSession().createQuery(hql).setParameter(0, member).setParameter(1, false)
                .setParameter(2, false).setParameter(3, DeleteStatus.toDelete).uniqueResult();
    }

    @Override
    public Long getUnreadMessageCount() {
        String hql = "select count(*) from Message as message where message.toMember is null and message.isRead = ? and message.isSaveDraftbox = ? and message.deleteStatus != ?";
        return (Long) getSession().createQuery(hql).setParameter(0, false).setParameter(1, false)
                .setParameter(2, DeleteStatus.toDelete).uniqueResult();
    }

}
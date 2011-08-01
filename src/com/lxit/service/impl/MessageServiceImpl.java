package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.bean.Pager;
import com.lxit.dao.MessageDao;
import com.lxit.entity.Member;
import com.lxit.entity.Message;
import com.lxit.service.MessageService;

/**
 * Service实现类 - 消息
 */

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {

    @Resource
    private MessageDao messageDao;

    @Resource
    public void setBaseDao(MessageDao messageDao) {
        super.setBaseDao(messageDao);
    }

    @Override
    public Pager getMemberInboxPager(Member member, Pager pager) {
        return messageDao.getMemberInboxPager(member, pager);
    }

    @Override
    public Pager getMemberOutboxPager(Member member, Pager pager) {
        return messageDao.getMemberOutboxPager(member, pager);
    }

    @Override
    public Pager getMemberDraftboxPager(Member member, Pager pager) {
        return messageDao.getMemberDraftboxPager(member, pager);
    }

    @Override
    public Pager getAdminInboxPager(Pager pager) {
        return messageDao.getAdminInboxPager(pager);
    }

    @Override
    public Pager getAdminOutboxPager(Pager pager) {
        return messageDao.getAdminOutboxPager(pager);
    }

    @Override
    public Long getUnreadMessageCount(Member member) {
        return messageDao.getUnreadMessageCount(member);
    }

    @Override
    public Long getUnreadMessageCount() {
        return messageDao.getUnreadMessageCount();
    }

}
package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.bean.Pager;
import com.lxit.dao.DepositDao;
import com.lxit.entity.Deposit;
import com.lxit.entity.Member;
import com.lxit.service.DepositService;

/**
 * Service实现类 - 预存款记录
 */

@Service
public class DepositServiceImpl extends BaseServiceImpl<Deposit, String> implements DepositService {

    @Resource
    private DepositDao depositDao;

    @Resource
    public void setBaseDao(DepositDao depositDao) {
        super.setBaseDao(depositDao);
    }

    @Override
    public Pager getDepositPager(Member member, Pager pager) {
        return depositDao.getDepositPager(member, pager);
    }

}
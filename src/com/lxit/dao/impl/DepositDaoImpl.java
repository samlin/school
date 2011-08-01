package com.lxit.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lxit.bean.Pager;
import com.lxit.dao.DepositDao;
import com.lxit.entity.Deposit;
import com.lxit.entity.Member;

/**
 * Dao实现类 - 预存款记录
 */

@Repository
public class DepositDaoImpl extends BaseDaoImpl<Deposit, String> implements DepositDao {

    @Override
    public Pager getDepositPager(Member member, Pager pager) {
        if (pager == null) {
            pager = new Pager();
        }
        if (pager.getPageSize() == null) {
            pager.setPageSize(Deposit.DEFAULT_DEPOSIT_LIST_PAGE_SIZE);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Deposit.class);
        detachedCriteria.add(Restrictions.eq("member", member));
        return super.findByPager(pager, detachedCriteria);
    }

}
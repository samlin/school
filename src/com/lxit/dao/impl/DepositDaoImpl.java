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
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXC18C08E26DB83F5F881422AB66E5DC75
 * ============================================================================
 */

@Repository
public class DepositDaoImpl extends BaseDaoImpl<Deposit, String> implements DepositDao {

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
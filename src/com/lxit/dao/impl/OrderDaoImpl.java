package com.lxit.dao.impl;

import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lxit.bean.Pager;
import com.lxit.dao.OrderDao;
import com.lxit.entity.Member;
import com.lxit.entity.Order;
import com.lxit.entity.Order.OrderStatus;
import com.lxit.entity.Order.PaymentStatus;
import com.lxit.entity.Order.ShippingStatus;

/**
 * Dao实现类 - 订单
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX8061830D6037E81F0D148994EFFC9C78
 * ============================================================================
 */

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, String> implements OrderDao {
	
	@SuppressWarnings("unchecked")
	public String getLastOrderSn() {
		String hql = "from Order as order order by order.createDate desc";
		List<Order> orderList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (orderList != null && orderList.size() > 0) {
			return orderList.get(0).getOrderSn();
		} else {
			return null;
		}
	}
	
	public Pager getOrderPager(Member member, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Order.DEFAULT_ORDER_LIST_PAGE_SIZE);
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Order.class);
		detachedCriteria.add(Restrictions.eq("member", member));
		return super.findByPager(pager, detachedCriteria);
	}
	
	public Long getUnprocessedOrderCount() {
		String hql = "select count(*) from Order as order where order.orderStatus = ?";
		return (Long) getSession().createQuery(hql).setParameter(0, OrderStatus.unprocessed).uniqueResult();
	}
	
	public Long getPaidUnshippedOrderCount() {
		String hql = "select count(*) from Order as order where order.paymentStatus = ? and order.shippingStatus = ? and order.orderStatus != ? and order.orderStatus != ?";
		return (Long) getSession().createQuery(hql).setParameter(0, PaymentStatus.paid).setParameter(1, ShippingStatus.unshipped).setParameter(2, OrderStatus.completed).setParameter(3, OrderStatus.invalid).uniqueResult();
	}
	
}
package com.lxit.service;

import com.lxit.bean.Pager;
import com.lxit.entity.Member;
import com.lxit.entity.Order;

/**
 * Service接口 - 订单
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXC25D50D18A27A8E1B4A11F7643DAA055
 * ============================================================================
 */

public interface OrderService extends BaseService<Order, String> {

	/**
	 * 获取最后生成的订单编号
	 * 
	 * @return 订单编号
	 */
	public String getLastOrderSn();
	
	/**
	 * 根据Member、Pager获取订单分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 订单分页对象
	 */
	public Pager getOrderPager(Member member, Pager pager);
	
	/**
	 * 获取未处理订单数
	 *            
	 * @return 未处理订单数
	 */
	public Long getUnprocessedOrderCount();
	
	/**
	 * 获取已支付未发货订单数（不包含已完成或已作废订单）
	 *            
	 * @return 已支付未发货订单数
	 */
	public Long getPaidUnshippedOrderCount();

}
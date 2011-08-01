package com.lxit.dao;

import com.lxit.entity.Refund;

/**
 * Dao接口 - 退款
 */

public interface RefundDao extends BaseDao<Refund, String> {

    /**
     * 获取最后生成的退款编号
     * 
     * @return 收款编号
     */
    public String getLastRefundSn();

    /**
     * 根据退款编号获取对象（若对象不存在，则返回null）
     * 
     * @return 退款对象
     */
    public Refund getRefundByRefundSn(String refundSn);

}
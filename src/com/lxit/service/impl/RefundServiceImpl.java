package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.RefundDao;
import com.lxit.entity.Refund;
import com.lxit.service.RefundService;
import com.lxit.util.SerialNumberUtil;

/**
 * Service实现类 - 退款
 */

@Service
public class RefundServiceImpl extends BaseServiceImpl<Refund, String> implements RefundService {

    @Resource
    private RefundDao refundDao;

    @Resource
    public void setBaseDao(RefundDao refundDao) {
        super.setBaseDao(refundDao);
    }

    @Override
    public String getLastRefundSn() {
        return refundDao.getLastRefundSn();
    }

    @Override
    public Refund getRefundByRefundSn(String refundSn) {
        return refundDao.getRefundByRefundSn(refundSn);
    }

    // 重写对象，保存时自动设置退款编号
    @Override
    public String save(Refund refund) {
        refund.setRefundSn(SerialNumberUtil.buildRefundSn());
        return super.save(refund);
    }

}
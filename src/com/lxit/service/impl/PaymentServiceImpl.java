package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.PaymentDao;
import com.lxit.entity.Payment;
import com.lxit.service.PaymentService;
import com.lxit.util.SerialNumberUtil;

/**
 * Service实现类 - 支付
 */

@Service
public class PaymentServiceImpl extends BaseServiceImpl<Payment, String> implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Resource
    public void setBaseDao(PaymentDao paymentDao) {
        super.setBaseDao(paymentDao);
    }

    @Override
    public String getLastPaymentSn() {
        return paymentDao.getLastPaymentSn();
    }

    @Override
    public Payment getPaymentByPaymentSn(String paymentSn) {
        return paymentDao.getPaymentByPaymentSn(paymentSn);
    }

    // 重写对象，保存时自动设置支付编号
    @Override
    public String save(Payment payment) {
        payment.setPaymentSn(SerialNumberUtil.buildPaymentSn());
        return super.save(payment);
    }

}
package com.lxit.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.lxit.dao.PaymentDao;
import com.lxit.entity.Payment;
import com.lxit.service.PaymentService;
import com.lxit.util.SerialNumberUtil;

/**
 * Service实现类 - 支付
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX84F252BF71A4D877C0285E8086FBE56D
 * ============================================================================
 */

@Service
public class PaymentServiceImpl extends BaseServiceImpl<Payment, String> implements PaymentService {
	
	@Resource
	private PaymentDao paymentDao;

	@Resource
	public void setBaseDao(PaymentDao paymentDao) {
		super.setBaseDao(paymentDao);
	}
	
	public String getLastPaymentSn() {
		return paymentDao.getLastPaymentSn();
	}
	
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
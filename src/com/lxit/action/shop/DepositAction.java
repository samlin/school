package com.lxit.action.shop;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.PaymentConfig;
import com.lxit.service.DepositService;
import com.lxit.service.PaymentConfigService;

/**
 * 前台Action类 - 预存款
 */

@ParentPackage("member")
public class DepositAction extends BaseShopAction {

    private static final long serialVersionUID = -3091246496095700007L;

    @Resource
    private DepositService depositService;
    @Resource
    private PaymentConfigService paymentConfigService;

    // 预存款列表
    public String list() {
        pager = depositService.getDepositPager(getLoginMember(), pager);
        return "list";
    }

    // 预存款充值
    public String recharge() {
        return "recharge";
    }

    // 获取支付配置（不包含预存款、线下支付方式）
    public List<PaymentConfig> getNonDepositOfflinePaymentConfigList() {
        return paymentConfigService.getNonDepositOfflinePaymentConfigList();
    }

}
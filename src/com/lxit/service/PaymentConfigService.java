package com.lxit.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lxit.entity.PaymentConfig;

/**
 * Service接口 - 支付配置
 */

public interface PaymentConfigService extends BaseService<PaymentConfig, String> {

    /**
     * 获取非预存款类型的支付配置
     * 
     * @return 支付配置
     */
    public List<PaymentConfig> getNonDepositPaymentConfigList();

    /**
     * 获取非预存款、线下支付方式的支付配置
     * 
     * @return 支付配置
     */
    public List<PaymentConfig> getNonDepositOfflinePaymentConfigList();

    /**
     * 根据参数集合生成参数字符串
     * 
     * @param parameterMap
     *            参数集合
     * 
     * @return 参数字符串
     */
    public String buildParameterString(Map<String, String> parameterMap);

    /**
     * 生成即时交易支付请求URL
     * 
     * @param paymentConfig
     *            支付类型
     * 
     * @param paymentSn
     *            支付编号
     * 
     * @param totalAmount
     *            总金额
     * 
     * @param description
     *            交易描述
     * 
     * @param ip
     *            客户IP
     * 
     * @return 即时交易支付请求URL
     */
    public String buildTenpayDirectPaymentUrl(PaymentConfig paymentConfig, String paymentSn, BigDecimal totalAmount,
            String description, String ip);

    /**
     * 生成即时交易查询请求URL
     * 
     * @param paymentConfig
     *            支付类型
     * 
     * @param transactionId
     *            交易号
     * 
     * @param paymentSn
     *            支付编号
     * 
     * @return 即时交易查询请求URL
     */
    public String buildTenpayDirectQueryUrl(PaymentConfig paymentConfig, String transactionId, String paymentSn);

    /**
     * 生成担保交易支付请求URL
     * 
     * @param paymentConfig
     *            支付类型
     * 
     * @param paymentSn
     *            支付编号
     * 
     * @param totalAmount
     *            总金额
     * 
     * @param description
     *            交易描述
     * 
     * @return 担保交易支付请求URL
     */
    public String buildTenpayPartnerPaymentUrl(PaymentConfig paymentConfig, String paymentSn, BigDecimal totalAmount,
            String description);

}
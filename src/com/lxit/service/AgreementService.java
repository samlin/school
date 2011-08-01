package com.lxit.service;

import com.lxit.entity.Agreement;

/**
 * Service接口 - 会员注册协议
 */

public interface AgreementService extends BaseService<Agreement, String> {

    /**
     * 获取Agreement对象
     * 
     * @return Agreement对象
     * 
     */
    public Agreement getAgreement();

}
package com.lxit.dao;

import com.lxit.entity.Agreement;

public interface AgreementDao extends BaseDao<Agreement, String> {

    /**
     * 获取Agreement对象
     * 
     * @return Agreement对象
     * 
     */
    public Agreement getAgreement();

}

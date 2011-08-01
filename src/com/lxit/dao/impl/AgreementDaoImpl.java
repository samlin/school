package com.lxit.dao.impl;

import org.springframework.stereotype.Repository;

import com.lxit.dao.AgreementDao;
import com.lxit.entity.Agreement;

/**
 * Dao实现类 - 会员注册协议
 */

@Repository
public class AgreementDaoImpl extends BaseDaoImpl<Agreement, String> implements AgreementDao {

    @Override
    public Agreement getAgreement() {
        return load(Agreement.AGREEMENT_ID);
    }

}
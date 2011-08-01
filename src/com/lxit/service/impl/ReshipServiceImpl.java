package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.ReshipDao;
import com.lxit.entity.Reship;
import com.lxit.service.ReshipService;
import com.lxit.util.SerialNumberUtil;

/**
 * Service实现类 - 退货
 */

@Service
public class ReshipServiceImpl extends BaseServiceImpl<Reship, String> implements ReshipService {

    @Resource
    private ReshipDao reshipDao;

    @Resource
    public void setBaseDao(ReshipDao reshipDao) {
        super.setBaseDao(reshipDao);
    }

    @Override
    public String getLastReshipSn() {
        return reshipDao.getLastReshipSn();
    }

    // 重写对象，保存时自动设置退货编号
    @Override
    public String save(Reship reship) {
        reship.setReshipSn(SerialNumberUtil.buildReshipSn());
        return super.save(reship);
    }

}
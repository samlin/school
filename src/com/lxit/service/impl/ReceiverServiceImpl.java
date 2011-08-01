package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.ReceiverDao;
import com.lxit.entity.Receiver;
import com.lxit.service.ReceiverService;

/**
 * Service实现类 - 收货地址
 */

@Service
public class ReceiverServiceImpl extends BaseServiceImpl<Receiver, String> implements ReceiverService {

    @Resource
    public void setBaseDao(ReceiverDao receiverDao) {
        super.setBaseDao(receiverDao);
    }

}
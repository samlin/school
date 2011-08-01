package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.LogDao;
import com.lxit.entity.Log;
import com.lxit.service.LogService;

@Service
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

    @Resource
    public void setBaseDao(LogDao logDao) {
        super.setBaseDao(logDao);
    }

}

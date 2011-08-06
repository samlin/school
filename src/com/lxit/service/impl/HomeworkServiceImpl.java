package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lxit.dao.HomeworkDao;
import com.lxit.entity.Homework;
import com.lxit.service.HomeworkService;

/**
 * Service实现类 - 会员
 */
@Transactional
@Service
public class HomeworkServiceImpl extends BaseServiceImpl<Homework, String> implements HomeworkService {

    @Resource
    private HomeworkDao dao;

    @Resource
    public void setBaseDao(HomeworkDao dao) {
        super.setBaseDao(dao);
    }

}
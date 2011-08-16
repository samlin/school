package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lxit.dao.InterviewDao;
import com.lxit.entity.Interview;
import com.lxit.service.InterviewService;

@Transactional
@Service
public class InterviewServiceImpl extends BaseServiceImpl<Interview, String> implements InterviewService {

    @Resource
    private InterviewDao dao;

    @Resource
    public void setBaseDao(InterviewDao dao) {
        super.setBaseDao(dao);
    }

}
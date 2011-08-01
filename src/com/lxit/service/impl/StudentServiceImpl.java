package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lxit.dao.StudentDao;
import com.lxit.entity.Student;
import com.lxit.service.StudentService;

/**
 * Service实现类 - 会员
 */
@Transactional
@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, String> implements StudentService {

    @Resource
    private StudentDao studentDao;

    @Resource
    public void setBaseDao(StudentDao studentDao) {
        super.setBaseDao(studentDao);
    }

}
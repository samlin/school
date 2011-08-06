package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lxit.dao.${className}Dao;
import com.lxit.entity.${className};
import com.lxit.service.${className}Service;

@Transactional
@Service
public class ${className}ServiceImpl extends BaseServiceImpl<${className}, String> implements ${className}Service {

    @Resource
    private ${className}Dao dao;

    @Resource
    public void setBaseDao(${className}Dao dao) {
        super.setBaseDao(dao);
    }

}
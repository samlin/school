package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.BrandDao;
import com.lxit.entity.Brand;
import com.lxit.service.BrandService;

/**
 * Service实现类 - 品牌
 */

@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand, String> implements BrandService {

    @Resource
    public void setBaseDao(BrandDao brandDao) {
        super.setBaseDao(brandDao);
    }

}
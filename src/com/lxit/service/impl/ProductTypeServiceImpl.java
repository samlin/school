package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.ProductTypeDao;
import com.lxit.entity.ProductType;
import com.lxit.service.ProductTypeService;

/**
 * Service实现类 - 商品类型
 */

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, String> implements ProductTypeService {

    @Resource
    public void setBaseDao(ProductTypeDao productTypeDao) {
        super.setBaseDao(productTypeDao);
    }

}

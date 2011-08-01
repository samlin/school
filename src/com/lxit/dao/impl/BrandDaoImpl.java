package com.lxit.dao.impl;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lxit.dao.BrandDao;
import com.lxit.entity.Brand;
import com.lxit.entity.Product;

/**
 * Dao实现类 - 品牌
 */

@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand, String> implements BrandDao {

    // 关联处理
    @Override
    public void delete(Brand brand) {
        Set<Product> productSet = brand.getProductSet();
        if (productSet != null) {
            for (Product product : productSet) {
                product.setBrand(null);
            }
        }
        super.delete(brand);
    }

    // 关联处理
    @Override
    public void delete(String id) {
        Brand brand = load(id);
        this.delete(brand);
    }

    // 关联处理
    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            Brand brand = load(id);
            this.delete(brand);
        }
    }

}
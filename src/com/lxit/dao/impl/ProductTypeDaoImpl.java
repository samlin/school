package com.lxit.dao.impl;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lxit.dao.ProductTypeDao;
import com.lxit.entity.Product;
import com.lxit.entity.ProductType;

/**
 * Dao实现类 - 商品类型
 */

@Repository
public class ProductTypeDaoImpl extends BaseDaoImpl<ProductType, String> implements ProductTypeDao {

    // 关联处理
    @Override
    public void delete(ProductType productType) {
        Set<Product> productSet = productType.getProductSet();
        for (Product product : productSet) {
            product.setProductType(null);
            product.setProductAttributeMap(null);
        }
        super.delete(productType);
    }

    // 关联处理
    @Override
    public void delete(String id) {
        ProductType productType = super.load(id);
        this.delete(productType);
    }

    // 关联处理
    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            ProductType productType = super.load(id);
            this.delete(productType);
        }
    }

}
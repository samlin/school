package com.lxit.dao;

import java.util.List;

import com.lxit.entity.ProductCategory;

/**
 * Dao接口 - 商品分类
 */

public interface ProductCategoryDao extends BaseDao<ProductCategory, String> {

    /**
     * 获取所有顶级商品分类集合;
     * 
     * @return 所有顶级商品分类集合
     * 
     */
    public List<ProductCategory> getRootProductCategoryList();

    /**
     * 根据ProductCategory对象获取所有父类集合，若无父类则返回null;
     * 
     * @return 父类集合
     * 
     */
    public List<ProductCategory> getParentProductCategoryList(ProductCategory productCategory);

    /**
     * 根据ProductCategory对象获取所有子类集合，若无子类则返回null;
     * 
     * @return 子类集合
     * 
     */
    public List<ProductCategory> getChildrenProductCategoryList(ProductCategory productCategory);

}

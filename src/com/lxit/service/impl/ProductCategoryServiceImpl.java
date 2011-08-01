package com.lxit.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.lxit.dao.ProductCategoryDao;
import com.lxit.entity.Product;
import com.lxit.entity.ProductCategory;
import com.lxit.service.ProductCategoryService;

/**
 * Service实现类 - 商品分类
 */

@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory, String> implements
        ProductCategoryService {

    @Resource
    private ProductCategoryDao productCategoryDao;

    @Resource
    public void setBaseDao(ProductCategoryDao productCategoryDao) {
        super.setBaseDao(productCategoryDao);
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ProductCategory> getRootProductCategoryList() {
        List<ProductCategory> rootProductCategoryList = productCategoryDao.getRootProductCategoryList();
        if (rootProductCategoryList != null) {
            for (ProductCategory rootProductCategory : rootProductCategoryList) {
                Hibernate.initialize(rootProductCategory);
            }
        }
        return rootProductCategoryList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ProductCategory> getParentProductCategoryList(ProductCategory productCategory) {
        List<ProductCategory> parentProductCategoryList = productCategoryDao
                .getParentProductCategoryList(productCategory);
        if (parentProductCategoryList != null) {
            for (ProductCategory parentProductCategory : parentProductCategoryList) {
                Hibernate.initialize(parentProductCategory);
            }
        }
        return parentProductCategoryList;
    }

    @Override
    public List<ProductCategory> getParentProductCategoryList(Product product) {
        ProductCategory productCategory = product.getProductCategory();
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.addAll(this.getParentProductCategoryList(productCategory));
        productCategoryList.add(productCategory);
        return productCategoryList;
    }

    @Override
    public List<ProductCategory> getProductCategoryPathList(ProductCategory productCategory) {
        List<ProductCategory> productCategoryPathList = new ArrayList<ProductCategory>();
        productCategoryPathList.addAll(this.getParentProductCategoryList(productCategory));
        productCategoryPathList.add(productCategory);
        return productCategoryPathList;
    }

    @Override
    public List<ProductCategory> getProductCategoryPathList(Product product) {
        ProductCategory productCategory = product.getProductCategory();
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.addAll(this.getParentProductCategoryList(productCategory));
        productCategoryList.add(productCategory);
        return productCategoryList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ProductCategory> getChildrenProductCategoryList(ProductCategory productCategory) {
        List<ProductCategory> childrenProductCategoryList = productCategoryDao
                .getChildrenProductCategoryList(productCategory);
        if (childrenProductCategoryList != null) {
            for (ProductCategory childrenProductCategory : childrenProductCategoryList) {
                Hibernate.initialize(childrenProductCategory);
            }
        }
        return childrenProductCategoryList;
    }

    @Override
    public List<ProductCategory> getChildrenProductCategoryList(Product product) {
        ProductCategory productCategory = product.getProductCategory();
        List<ProductCategory> productCategoryList = getChildrenProductCategoryList(productCategory);
        if (productCategoryList == null) {
            productCategoryList = new ArrayList<ProductCategory>();
        }
        productCategoryList.add(productCategory);
        return productCategoryList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ProductCategory> getProductCategoryTreeList() {
        List<ProductCategory> allProductCategoryList = this.getAll();
        return recursivProductCategoryTreeList(allProductCategoryList, null, null);
    }

    // 递归父类排序分类树
    private List<ProductCategory> recursivProductCategoryTreeList(List<ProductCategory> allProductCategoryList,
            ProductCategory p, List<ProductCategory> temp) {
        if (temp == null) {
            temp = new ArrayList<ProductCategory>();
        }
        for (ProductCategory productCategory : allProductCategoryList) {
            ProductCategory parent = productCategory.getParent();
            if ((p == null && parent == null) || (productCategory != null && parent == p)) {
                temp.add(productCategory);
                if (productCategory.getChildren() != null && productCategory.getChildren().size() > 0) {
                    recursivProductCategoryTreeList(allProductCategoryList, productCategory, temp);
                }
            }
        }
        return temp;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ProductCategory> getAll() {
        List<ProductCategory> allProductCategory = productCategoryDao.getAll();
        if (allProductCategory != null) {
            for (ProductCategory productCategory : allProductCategory) {
                Hibernate.initialize(productCategory);
            }
        }
        return allProductCategory;
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(ProductCategory productCategory) {
        productCategoryDao.delete(productCategory);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String id) {
        productCategoryDao.delete(id);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String[] ids) {
        productCategoryDao.delete(ids);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public String save(ProductCategory productCategory) {
        return productCategoryDao.save(productCategory);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void update(ProductCategory productCategory) {
        productCategoryDao.update(productCategory);
    }

}
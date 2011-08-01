package com.lxit.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

/**
 * 实体类 - 商品分类
 */

@Entity
public class ProductCategory extends BaseEntity {

    private static final long serialVersionUID = -5132652107151648662L;

    public static final String PATH_SEPARATOR = ",";// 树路径分隔符

    private String name;// 分类名称
    private String metaKeywords;// 页面关键词
    private String metaDescription;// 页面描述
    private Integer orderList;// 排序
    private String path;// 树路径

    private ProductCategory parent;// 上级分类
    private Set<ProductCategory> children;// 下级分类
    private Set<Product> productSet;// 商品

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 5000)
    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    @Column(length = 5000)
    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    @Column(nullable = false)
    public Integer getOrderList() {
        return orderList;
    }

    public void setOrderList(Integer orderList) {
        this.orderList = orderList;
    }

    @Column(nullable = true, length = 10000)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy("orderList asc")
    public Set<ProductCategory> getChildren() {
        return children;
    }

    public void setChildren(Set<ProductCategory> children) {
        this.children = children;
    }

    @OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    // 获取分类层级（顶级分类：0）
    @Transient
    public Integer getLevel() {
        return path.split(PATH_SEPARATOR).length - 1;
    }

}
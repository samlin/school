package com.lxit.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;

import com.lxit.entity.ArticleCategory;
import com.lxit.entity.Navigation;
import com.lxit.entity.ProductCategory;
import com.lxit.service.ArticleCategoryService;
import com.lxit.service.NavigationService;
import com.lxit.service.ProductCategoryService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("admin")
public class NavigationAction extends BaseAdminAction {

    private static final long serialVersionUID = -7786508966240073537L;

    private Navigation navigation;
    private List<ArticleCategory> articleCategoryTreeList;
    private List<ProductCategory> productCategoryTreeList;

    @Resource
    private NavigationService navigationService;
    @Resource
    private ArticleCategoryService articleCategoryService;
    @Resource
    private ProductCategoryService productCategoryService;

    // 添加
    public String add() {
        return INPUT;
    }

    // 编辑
    public String edit() {
        navigation = navigationService.load(id);
        return INPUT;
    }

    // 列表
    public String list() {
        // 根据位置排序
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Navigation.class);
        detachedCriteria.addOrder(Order.asc("position"));
        detachedCriteria.addOrder(Order.asc("orderList"));
        pager = navigationService.findByPager(pager, detachedCriteria);
        return LIST;
    }

    // 删除
    public String delete() {
        navigationService.delete(ids);
        redirectionUrl = "navigation!list.action";
        return ajaxJsonSuccessMessage("删除成功！");
    }

    // 保存
    @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "navigation.name", message = "导航名称不允许为空!"),
            @RequiredStringValidator(fieldName = "navigation.url", message = "链接地址不允许为空!") }, requiredFields = {
            @RequiredFieldValidator(fieldName = "navigation.orderList", message = "排序不允许为空!"),
            @RequiredFieldValidator(fieldName = "navigation.isVisible", message = "是否显示不允许为空!"),
            @RequiredFieldValidator(fieldName = "navigation.isBlankTarget", message = "在新窗口中打开不允许为空!") }, intRangeFields = { @IntRangeFieldValidator(fieldName = "navigation.orderList", min = "0", message = "排序必须为零或正整数!") })
    @InputConfig(resultName = "error")
    public String save() {
        navigationService.save(navigation);
        redirectionUrl = "navigation!list.action";
        return SUCCESS;
    }

    // 更新
    @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "navigation.name", message = "导航名称不允许为空!"),
            @RequiredStringValidator(fieldName = "navigation.url", message = "链接地址不允许为空!") }, requiredFields = {
            @RequiredFieldValidator(fieldName = "navigation.orderList", message = "排序不允许为空!"),
            @RequiredFieldValidator(fieldName = "navigation.isVisible", message = "是否显示不允许为空!"),
            @RequiredFieldValidator(fieldName = "navigation.isBlankTarget", message = "在新窗口中打开不允许为空!") }, intRangeFields = { @IntRangeFieldValidator(fieldName = "navigation.orderList", min = "0", message = "排序必须为零或正整数!") })
    @InputConfig(resultName = "error")
    public String update() {
        Navigation persistent = navigationService.load(id);
        BeanUtils.copyProperties(navigation, persistent, new String[] { "id", "createDate", "modifyDate" });
        navigationService.update(persistent);
        redirectionUrl = "navigation!list.action";
        return SUCCESS;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    public List<ArticleCategory> getArticleCategoryTreeList() {
        articleCategoryTreeList = articleCategoryService.getArticleCategoryTreeList();
        return articleCategoryTreeList;
    }

    public void setArticleCategoryTreeList(List<ArticleCategory> articleCategoryTreeList) {
        this.articleCategoryTreeList = articleCategoryTreeList;
    }

    public List<ProductCategory> getProductCategoryTreeList() {
        productCategoryTreeList = productCategoryService.getProductCategoryTreeList();
        return productCategoryTreeList;
    }

    public void setProductCategoryTree(List<ProductCategory> productCategoryTreeList) {
        this.productCategoryTreeList = productCategoryTreeList;
    }

}
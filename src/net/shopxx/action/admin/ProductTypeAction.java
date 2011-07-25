package net.shopxx.action.admin;

import javax.annotation.Resource;

import net.shopxx.entity.ProductType;
import net.shopxx.service.ProductTypeService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品类型
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXB9E483EA86B3007D9DF1F8E314D400D2
 * ============================================================================
 */

@ParentPackage("admin")
public class ProductTypeAction extends BaseAdminAction {

	private static final long serialVersionUID = 8895838200173152426L;

	private ProductType productType;

	@Resource
	private ProductTypeService productTypeService;

	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = productType.getName();
		if (productTypeService.isUnique("name", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		productType = productTypeService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = productTypeService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		productTypeService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "productType.name", message = "商品类型不允许为空!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		productType.setProductAttributeList(null);
		productTypeService.save(productType);
		redirectionUrl = "product_type!list.action";
		return SUCCESS;
	}

	// 更新
	@InputConfig(resultName = "error")
	public String update() {
		ProductType persistent = productTypeService.load(id);
		BeanUtils.copyProperties(productType, persistent, new String[] {"id", "createDate", "modifyDate", "productAttributeList", "productSet"});
		productTypeService.update(persistent);
		redirectionUrl = "product_type!list.action";
		return SUCCESS;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

}
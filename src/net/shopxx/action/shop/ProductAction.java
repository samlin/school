package net.shopxx.action.shop;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.bean.Pager;
import net.shopxx.bean.Pager.OrderType;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 商品
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD555C0667F7008EC5F71FFFB78A4F6AB
 * ============================================================================
 */

@ParentPackage("shop")
public class ProductAction extends BaseShopAction {

	private static final long serialVersionUID = -4969421249817468001L;

	private ProductCategory productCategory;
	private String orderType;// 排序类型
	private String viewType;// 查看类型
	
	private List<ProductCategory> rootProductCategoryList;
	
	private List<Product> bestProductList;
	private List<Product> hotProductList;
	private List<Product> newProductList;
	private List<ProductCategory> pathList;
	
	@Resource
	private ProductService productService;
	@Resource
	private ProductCategoryService productCategoryService;

	@InputConfig(resultName = "error")
	public String list() {
		productCategory = productCategoryService.load(id);
		bestProductList = productService.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT);
		hotProductList = productService.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT);
		newProductList = productService.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT);
		pathList = productCategoryService.getProductCategoryPathList(productCategory);
		
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Product.DEFAULT_PRODUCT_LIST_PAGE_SIZE);
		}
		pager.setProperty(null);
		pager.setKeyword(null);
		
		if (StringUtils.equalsIgnoreCase(orderType, "priceAsc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.asc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "priceDesc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.desc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "dateAsc")) {
			pager.setOrderBy("createDate");
			pager.setOrderType(OrderType.asc);
		} else {
			pager.setOrderBy(null);
			pager.setOrderType(null);
		}
		
		pager = productService.getProductPager(productCategory, pager);
		
		if (StringUtils.equalsIgnoreCase(viewType, "tableType")) {
			return "table_list";
		} else {
			return "picture_list";
		}
	}
	
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "pager.keyword", message = "搜索关键词不允许为空!") 
		}
	)
	@InputConfig(resultName = "error")
	public String search() throws Exception {
		bestProductList = productService.getBestProductList(Product.MAX_BEST_PRODUCT_LIST_COUNT);
		hotProductList = productService.getHotProductList(Product.MAX_HOT_PRODUCT_LIST_COUNT);
		newProductList = productService.getNewProductList(Product.MAX_NEW_PRODUCT_LIST_COUNT);
		
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Product.DEFAULT_PRODUCT_LIST_PAGE_SIZE);
		}
		
		if (StringUtils.equalsIgnoreCase(orderType, "priceAsc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.asc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "priceDesc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.desc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "dateAsc")) {
			pager.setOrderBy("createDate");
			pager.setOrderType(OrderType.asc);
		} else {
			pager.setOrderBy(null);
			pager.setOrderType(null);
		}
		
		pager = productService.search(pager);
		
		if (StringUtils.equalsIgnoreCase(viewType, "tableType")) {
			return "table_search";
		} else {
			return "picture_search";
		}
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
	public List<ProductCategory> getRootProductCategoryList() {
		rootProductCategoryList = productCategoryService.getRootProductCategoryList();
		return rootProductCategoryList;
	}

	public void setRootProductCategoryList(List<ProductCategory> rootProductCategoryList) {
		this.rootProductCategoryList = rootProductCategoryList;
	}

	public List<Product> getBestProductList() {
		return bestProductList;
	}

	public void setBestProductList(List<Product> bestProductList) {
		this.bestProductList = bestProductList;
	}

	public List<Product> getHotProductList() {
		return hotProductList;
	}

	public void setHotProductList(List<Product> hotProductList) {
		this.hotProductList = hotProductList;
	}

	public List<Product> getNewProductList() {
		return newProductList;
	}

	public void setNewProductList(List<Product> newProductList) {
		this.newProductList = newProductList;
	}

	public List<ProductCategory> getPathList() {
		return pathList;
	}

	public void setPathList(List<ProductCategory> pathList) {
		this.pathList = pathList;
	}
	
}
package com.lxit.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassQueryBuilder.CompassBooleanQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.springframework.stereotype.Service;

import com.lxit.bean.HtmlConfig;
import com.lxit.bean.Pager;
import com.lxit.bean.Pager.OrderType;
import com.lxit.bean.ProductImage;
import com.lxit.dao.ProductDao;
import com.lxit.entity.Member;
import com.lxit.entity.Product;
import com.lxit.entity.ProductCategory;
import com.lxit.service.HtmlService;
import com.lxit.service.ProductService;
import com.lxit.util.TemplateConfigUtil;

/**
 * Service实现类 - 商品
 */

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {

    @Resource
    private ProductDao productDao;
    @Resource
    private CompassTemplate compassTemplate;
    @Resource
    private HtmlService htmlService;

    @Resource
    public void setBaseDao(ProductDao productDao) {
        super.setBaseDao(productDao);
    }

    @Override
    public List<Product> getProductList(ProductCategory productCategory) {
        return productDao.getProductList(productCategory);
    }

    @Override
    public List<Product> getProductList(int firstResult, int maxResults) {
        return productDao.getProductList(firstResult, maxResults);
    }

    @Override
    public List<Product> getProductList(ProductCategory productCategory, int firstResult, int maxResults) {
        return productDao.getProductList(productCategory, firstResult, maxResults);
    }

    @Override
    public Pager getProductPager(ProductCategory productCategory, Pager pager) {
        return productDao.getProductPager(productCategory, pager);
    }

    @Override
    public List<Product> getBestProductList(int maxResults) {
        return productDao.getBestProductList(maxResults);
    }

    @Override
    public List<Product> getBestProductList(ProductCategory productCategory, int maxResults) {
        return productDao.getBestProductList(productCategory, maxResults);
    }

    @Override
    public List<Product> getHotProductList(int maxResults) {
        return productDao.getHotProductList(maxResults);
    }

    @Override
    public List<Product> getHotProductList(ProductCategory productCategory, int maxResults) {
        return productDao.getHotProductList(productCategory, maxResults);
    }

    @Override
    public List<Product> getNewProductList(int maxResults) {
        return productDao.getNewProductList(maxResults);
    }

    @Override
    public List<Product> getNewProductList(ProductCategory productCategory, int maxResults) {
        return productDao.getNewProductList(productCategory, maxResults);
    }

    @Override
    public List<Product> getProductList(Date beginDate, Date endDate, int firstResult, int maxResults) {
        return productDao.getProductList(beginDate, endDate, firstResult, maxResults);
    }

    @Override
    public Pager search(Pager pager) {
        Compass compass = compassTemplate.getCompass();
        CompassSession compassSession = compass.openSession();
        CompassQueryBuilder compassQueryBuilder = compassSession.queryBuilder();
        CompassBooleanQueryBuilder compassBooleanQueryBuilder = compassQueryBuilder.bool();

        CompassQuery compassQuery = compassBooleanQueryBuilder.addMust(compassQueryBuilder.term("isMarketable", true))
                .addMust(compassQueryBuilder.queryString("name:*" + pager.getKeyword() + "*").toQuery()).toQuery();

        if (StringUtils.isEmpty(pager.getOrderBy()) || pager.getOrderType() == null) {
            compassQuery.addSort("isBest", SortPropertyType.STRING, SortDirection.REVERSE)
                    .addSort("isNew", SortPropertyType.STRING, SortDirection.REVERSE)
                    .addSort("isHot", SortPropertyType.STRING, SortDirection.REVERSE);
        } else {
            if (pager.getOrderType() == OrderType.asc) {
                if (StringUtils.equalsIgnoreCase(pager.getOrderBy(), "price")) {
                    compassQuery.addSort("price", SortPropertyType.FLOAT);
                }
            } else {
                if (StringUtils.equalsIgnoreCase(pager.getOrderBy(), "price")) {
                    compassQuery.addSort("price", SortPropertyType.FLOAT, SortDirection.REVERSE);
                }
            }
        }

        CompassHits compassHits = compassQuery.hits();

        List<Product> productList = new ArrayList<Product>();

        int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();
        int maxReasults = pager.getPageSize();
        int totalCount = compassHits.length();

        int end = Math.min(totalCount, firstResult + maxReasults);
        for (int i = firstResult; i < end; i++) {
            Product product = (Product) compassHits.data(i);
            productList.add(product);
        }
        compassSession.close();
        pager.setList(productList);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public Pager getFavoriteProductPager(Member member, Pager pager) {
        return productDao.getFavoriteProductPager(member, pager);
    }

    @Override
    public Long getStoreAlertCount() {
        return productDao.getStoreAlertCount();
    }

    @Override
    public Long getMarketableProductCount() {
        return productDao.getMarketableProductCount();
    }

    @Override
    public Long getUnMarketableProductCount() {
        return productDao.getUnMarketableProductCount();
    }

    // 重写方法，删除对象的同时删除已生成的HTML静态文件、商品图片文件
    @Override
    public void delete(Product product) {
        File htmlFile = new File(ServletActionContext.getServletContext().getRealPath(product.getHtmlFilePath()));
        if (htmlFile.exists()) {
            htmlFile.delete();
        }
        List<ProductImage> productImageList = product.getProductImageList();
        if (productImageList != null) {
            for (ProductImage productImage : productImageList) {
                File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(
                        productImage.getSourceProductImagePath()));
                if (sourceProductImageFile.exists()) {
                    sourceProductImageFile.delete();
                }
                File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(
                        productImage.getBigProductImagePath()));
                if (bigProductImageFile.exists()) {
                    bigProductImageFile.delete();
                }
                File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(
                        productImage.getSmallProductImagePath()));
                if (smallProductImageFile.exists()) {
                    smallProductImageFile.delete();
                }
                File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(
                        productImage.getThumbnailProductImagePath()));
                if (thumbnailProductImageFile.exists()) {
                    thumbnailProductImageFile.delete();
                }
            }
        }
        productDao.delete(product);
    }

    // 重写方法，删除对象的同时删除已生成的HTML静态文件、商品图片文件
    @Override
    public void delete(String id) {
        Product product = productDao.load(id);
        this.delete(product);
    }

    // 重写方法，删除对象的同时删除已生成的HTML静态文件、商品图片文件
    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            this.delete(id);
        }
    }

    // 重写方法，保存对象的同时处理价格精度并生成HTML静态文件
    @Override
    public String save(Product product) {
        HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.PRODUCT_CONTENT);
        String htmlFilePath = htmlConfig.getHtmlFilePath();
        product.setHtmlFilePath(htmlFilePath);
        String id = productDao.save(product);
        productDao.flush();
        productDao.evict(product);
        product = productDao.load(id);
        if (product.getIsMarketable()) {
            htmlService.productContentBuildHtml(product);
        }
        return id;
    }

    // 重写方法，更新对象的同时处理价格精度并重新生成HTML静态文件
    @Override
    public void update(Product product) {
        String id = product.getId();
        File htmlFile = new File(ServletActionContext.getServletContext().getRealPath(product.getHtmlFilePath()));
        if (htmlFile.exists()) {
            htmlFile.delete();
        }
        productDao.update(product);
        productDao.flush();
        productDao.evict(product);
        product = productDao.load(id);
        if (product.getIsMarketable()) {
            htmlService.productContentBuildHtml(product);
        }
    }

}
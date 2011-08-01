package com.lxit.service;

import java.io.File;

import com.lxit.bean.ProductImage;

public interface ProductImageService {

    /**
     * 生成商品图片（包括原图、大图、小图、缩略图）
     * 
     * @param uploadProductImageFile
     *            上传图片文件
     * 
     */
    public ProductImage buildProductImage(File uploadProductImageFile);

    /**
     * 根据ProductImage对象删除图片文件
     * 
     * @param productImage
     *            ProductImage
     * 
     */
    public void deleteFile(ProductImage productImage);

}
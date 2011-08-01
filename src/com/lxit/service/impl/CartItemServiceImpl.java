package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.CartItemDao;
import com.lxit.entity.CartItem;
import com.lxit.service.CartItemService;

/**
 * Service实现类 - 品牌
 */

@Service
public class CartItemServiceImpl extends BaseServiceImpl<CartItem, String> implements CartItemService {

    @Resource
    public void setBaseDao(CartItemDao cartItemDao) {
        super.setBaseDao(cartItemDao);
    }

}
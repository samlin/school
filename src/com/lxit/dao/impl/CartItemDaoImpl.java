package com.lxit.dao.impl;

import org.springframework.stereotype.Repository;

import com.lxit.dao.CartItemDao;
import com.lxit.entity.CartItem;

/**
 * Dao实现类 - 购物车项
 */

@Repository
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, String> implements CartItemDao {

    // 重写方法，若保存对象的member、product属性值相同，则只更新已有对象的quantity属性值
    @Override
    public String save(CartItem cartItem) {
        String hql = "from CartItem cartItem where cartItem.member = ? and cartItem.product = ?";
        CartItem persistent = (CartItem) getSession().createQuery(hql).setParameter(0, cartItem.getMember())
                .setParameter(1, cartItem.getProduct()).uniqueResult();
        if (persistent == null) {
            return super.save(cartItem);
        } else {
            persistent.setQuantity(persistent.getQuantity() + cartItem.getQuantity());
            super.update(persistent);
            return persistent.getId();
        }
    }

}
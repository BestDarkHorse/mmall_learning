package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.pojo.Cart;
import com.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null) { //说明该用户的购物车里还没有该产品
            Cart cartItem = new Cart();
            cartItem.setUserId(userId);
            cartItem.setUserId(productId);
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);

            cartMapper.insert(cartItem);
        } else {
            //该用户的购物车里有该产品
            count = cart.getQuantity() + count;
            cart.setQuantity(count);

            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return null;
    }

}

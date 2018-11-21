package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.service.ICartService;
import org.springframework.stereotype.Service;

@Service("iCartService")
public class CartServiceImpl implements ICartService {

    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        return null;
    }

}

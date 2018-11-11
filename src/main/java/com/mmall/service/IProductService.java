package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

public interface IProductService {

    /**
     * 新增或更新商品
     * @param product
     * @return
     */
    ServerResponse productSaveOrUpdate(Product product);

    /**
     * 设置产品销售状态
     * @param productId
     * @param status
     * @return
     */
    ServerResponse<String> setSaleStatus(Integer productId,Integer status);


    /**
     * 获取商品列表数据
     * @param page
     * @param pageSizes
     * @return
     */
    ServerResponse<PageInfo> getProductList(Integer page, Integer pageSizes);

}

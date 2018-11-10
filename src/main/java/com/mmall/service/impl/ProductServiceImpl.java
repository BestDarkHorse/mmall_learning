package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    public ServerResponse productSaveOrUpdate(Product product){
        if (product != null){
            if (StringUtils.isBlank(product.getSubImages())){
                String[] split = product.getSubImages().split(",");
                if (split.length > 0){
                    product.setMainImage(split[0]);
                }
            }

            //更新
            if (product.getId() != null){
                int result = productMapper.updateByPrimaryKey(product);
                if (result > 0) {
                    return ServerResponse.createBySuccessMessage("更新成功");
                } else {
                    return ServerResponse.createByErrorMessage("更新失败");
                }
            }

            //保存
            int result = productMapper.insert(product);
            if (result > 0) {
                return ServerResponse.createBySuccessMessage("新增成功");
            } else {
                return ServerResponse.createByErrorMessage("新增失败");
            }

        }

        return ServerResponse.createByErrorMessage("新增或更新参数错误");
    }

    public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);

        int result = productMapper.updateByPrimaryKeySelective(product);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("设置产品销售状态成功");
        } else {
            return ServerResponse.createByErrorMessage("设置产品销售状态失败");
        }

    }

}

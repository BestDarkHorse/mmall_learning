package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

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


    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }

        //将pojo 转化为 vo
        return null;

    }

    private ProductDetailVo assemblProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setName(product.getName());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());

        //setImageHost
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://image.imooc.com/"));

        //setparentCategoryId
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            productDetailVo.setParentCategoryId(0);//默认根节点
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        //setCreateTime
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        //setupdateTime
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    public ServerResponse<PageInfo> getProductList(Integer page,Integer pageSizes){
        //开启分页
        PageHelper.startPage(page, pageSizes);
        //查询sql
        List<Product> productList = productMapper.selectList();

        //组装返回的数据
        List<ProductListVo> productListVoList = new ArrayList<>();
        for (Product item : productList) {
            ProductListVo productListVo = assembleProductListVo(item);
            productListVoList.add(productListVo);
        }

        //分页结尾
        PageInfo info = new PageInfo<>(productList);
        info.setList(productListVoList);

        return ServerResponse.createBySuccess(info);
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    public ServerResponse<PageInfo> searchProduct(String productName,Integer productId,Integer pageNum,Integer pageSizes){
        //开始分页
        PageHelper.startPage(pageNum, pageSizes);
        List<Product> productList = productMapper.selectProductByNameAndId(productName, productId);

        //组装返回的数据
        List<ProductListVo> productListVoList = new ArrayList<>();
        for (Product item : productList) {
            ProductListVo productListVo = assembleProductListVo(item);
            productListVoList.add(productListVo);
        }

        //返回分页数据
        PageInfo productPageInfo = new PageInfo<>(productList);
        productPageInfo.setList(productListVoList);

        return ServerResponse.createBySuccess(productPageInfo);
    }



}

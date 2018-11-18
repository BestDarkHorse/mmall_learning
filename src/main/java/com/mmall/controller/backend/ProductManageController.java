package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户已过期,请重新登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //admin权限,执行相应的逻辑操作
            return iProductService.productSaveOrUpdate(product);
        }else{
            return ServerResponse.createByErrorMessage("无权操作");
        }

    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户无权限,请重新登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //执行相应的逻辑
            return iProductService.setSaleStatus(productId, status);
        } else {
            return ServerResponse.createByErrorMessage("用户无权限,请重新登录");
        }

    }

    @RequestMapping("detail.do")
    @ResponseBody
    public  ServerResponse getDetail(HttpSession session,Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请重新登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //执行获取详情的逻辑
            return null;
        } else {
            return ServerResponse.createByErrorMessage("用户无权限,请登录管理员");
        }

    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer pageSizes) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请重新登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //执行获取列表的逻辑
            return iProductService.getProductList(page, pageSizes);
        } else {
            return ServerResponse.createByErrorMessage("用户权限,请联系联系管理员");
        }
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session,String productName,Integer productId,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer pageSizes){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请重新登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //执行相应的逻辑
            return iProductService.searchProduct(productName,productId,page,pageSizes);
        } else {
            return ServerResponse.createByErrorMessage("用户无权限,请联系管理员");
        }
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, HttpServletRequest request, @RequestParam(value = "upload_file", required = false) MultipartFile file) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

}

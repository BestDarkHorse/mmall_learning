package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * created by dongkui
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    /**
     * 校验用户名 和 邮箱
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str,String type);

    /**
     * 根据用户名 找到问题
     * @param username
     * @return
     */
    ServerResponse selectQuestion(String username);

}

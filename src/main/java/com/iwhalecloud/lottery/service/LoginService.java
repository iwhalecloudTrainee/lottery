package com.iwhalecloud.lottery.service;

/**
 * @title: LoginService
 * @description: 用户登录相关
 * @author: pei.hongjun@iwhalecloud.com
 * @date: 2021-1-18 14：12
 */

import com.iwhalecloud.lottery.entity.UserInfo;

public interface LoginService {

    /**
     * 根据用户ID查询用户信息
     *
     * @param password
     * @return
     */
    UserInfo getUserInfoByPassword (String password);



}

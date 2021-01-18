package com.iwhalecloud.lottery.service;

/**
 * @title: LoginService
 * @description: 用户登录相关
 * @author: pei.hongjun@iwhalecloud.com
 * @date: 2021-1-18 14：12
 */

import com.iwhalecloud.lottery.params.req.LoginReq;
import com.iwhalecloud.lottery.params.vo.Result;

public interface LoginService {


    Result login(LoginReq loginReq);


}

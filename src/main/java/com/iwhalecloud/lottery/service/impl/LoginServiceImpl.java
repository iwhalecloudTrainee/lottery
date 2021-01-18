package com.iwhalecloud.lottery.service.impl;


import com.iwhalecloud.lottery.entity.UserInfo;
import com.iwhalecloud.lottery.mapper.UserInfoMapper;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @title: LoginServiceImpl
 * @description:
 * @author: pei.hongjun@iwhalecloud.com
 * @date: 2021-1-18 14:26
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserInfoMapper userInfoMapper;



    @Override
    public UserInfo getUserInfoByPassword(String password) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(password);
        userInfo.setPassword(password);
        return userInfo;
    }




}

package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.mapper.LotteryMapper;
import com.iwhalecloud.lottery.params.req.LoginReq;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.LoginService;
import com.iwhalecloud.lottery.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @title: LoginServiceImpl
 * @description:
 * @author: pei.hongjun@iwhalecloud.com
 * @date: 2021-1-18 14:26
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LotteryMapper lotteryMapper;

	@Override
	public Result login(LoginReq loginReq) {
		if (null == loginReq.getLotteryId() || !StringUtils.hasText(loginReq.getPassword())) {
			return Result.getFalse("输入有误");
		}
		Lottery lottery = new Lottery();
		BeanUtils.copyProperties(loginReq, lottery);
		lottery.setPassword(MD5Util.getMD5String(lottery.getPassword()));
		if (lotteryMapper.selectCount(lottery) < 1) {
			return Result.getFalse("密码错误");
		}
		return Result.getSuccess();
	}

	public static void main(String[] args) {
		System.out.println(		MD5Util.getMD5String("asd")
		);
	}
}

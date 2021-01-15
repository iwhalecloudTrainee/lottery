package com.iwhalecloud.lottery.controller;

import com.iwhalecloud.lottery.entity.UserInfo;
import com.iwhalecloud.lottery.params.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于提供与用户有关的接口（登录、导入员工……）
 *
 * @author by W4i
 * @date 2021/1/15 19:53
 */
@RestController
@RequestMapping("user")
public class UserController {
	/**
	 * 登录
	 * @param userInfo
	 * @return
	 */
	@PostMapping("login")
	public Result login(UserInfo userInfo){
		return null;
	}
	@RequestMapping("staffUpload")
	public Result staffUpload(){
		return null;
	}
}

package com.iwhalecloud.lottery.controller;

import com.iwhalecloud.lottery.params.req.LoginReq;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by W4i
 * @date 2021/1/15 19:53
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Resource
	private LoginService loginService;

	/**
	 * 登录（通过lotteryId和password匹配，判断是否拥有操作抽奖的权限）
	 *
	 * @param loginReq
	 * @return
	 */
	@PostMapping(value = "login")
	public Result login(@RequestBody LoginReq loginReq) {
		return loginService.login(loginReq);

	}
}

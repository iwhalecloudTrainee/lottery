package com.iwhalecloud.lottery.controller;

import com.iwhalecloud.lottery.entity.UserInfo;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.LoginService;
import com.iwhalecloud.lottery.utils.MD5Util;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

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

	@Resource
	private LoginService loginService;

	@PostMapping(value="login")
	public Result login(@RequestBody Map<String, String> params) {
		String password = params.get("password");
		UserInfo userInfo = loginService.getUserInfoByPassword(password);

		/*//查询用户信息
		UserInfo userInfo = loginService.getUserInfoByUserId(password);
		if (StringUtils.isEmpty(userInfo)) {
			return Result.getFalse("用户不存在!");
		}*/
		//对密码进行加密
		String pwd = "";
		if (StringUtils.isEmpty(userInfo.getPassword())) {
//            pwd = StringUtil.getMd5Pwd(password,"");
			pwd = MD5Util.getMD5String(password);
		} else {
			pwd = MD5Util.getMD5String(password, userInfo.getPassword().trim());
//            pwd = StringUtil.getMd5Pwd(password, userInfoDto.getSalt().trim());
		}
		if (!pwd.equals(userInfo.getPassword())) {
			return Result.getFalse("密码错误！");
		}
		return Result.getSuccess(userInfo);

	}
}

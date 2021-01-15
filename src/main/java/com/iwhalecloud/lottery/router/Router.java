package com.iwhalecloud.lottery.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 拿来返回到页面
 *
 * @author by W4i
 * @date 2021/1/15 20:23
 */
@Controller("/")
public class Router {
	@RequestMapping("index")
	public String index() {
		return "index/index.html";
	}
	@RequestMapping("login")
	public String login(){
		return "login/login.html";
	}
}

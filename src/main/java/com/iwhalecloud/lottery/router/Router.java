package com.iwhalecloud.lottery.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 拿来返回到页面
 * index lottery lotteryList这三个应该要带modelAndView
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
	public String login() {
		return "login/login.html";
	}

	@RequestMapping("lottery")
	public String lottery() {
		return "lottery/lottery.html";
	}

	@RequestMapping("lotteryList")
	public String lotteryList() {
		return "lotteryList/lotteryList.html";
	}
}

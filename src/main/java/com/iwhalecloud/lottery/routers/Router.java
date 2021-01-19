package com.iwhalecloud.lottery.routers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 拿来返回到页面
 * index lottery lotteryList这三个应该要带modelAndView
 *
 * @author by W4i
 * @date 2021/1/15 20:23
 */
@Controller("/")
public class Router {

	@RequestMapping("newLottery")
	public String index() {
		return "newLottery/newLottery.html";
	}

	/**
	 * 登录界面返回
	 *
	 * @return
	 */
	@RequestMapping("login")
	public String login() {
		return "redirect:login/login.html";
	}

	/**
	 * 单个抽奖查询
	 * 预计入参 lotteryId，session
	 *
	 * @return
	 */
	@RequestMapping("lottery")
	public String lottery() {
		return "lottery/lottery.html";
	}

	/**
	 * 用户创建的所有抽奖列表
	 * 预计入参：userId，session
	 *
	 * @return
	 */
	@RequestMapping("lotteryList")
	public String lotteryList() {
		return "lotteryList/lotteryList.html";
	}
}

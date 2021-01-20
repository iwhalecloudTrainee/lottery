package com.iwhalecloud.lottery.routers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 拿来返回到页面
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

	@RequestMapping("lottery")
	public String lottery() {
		return "lottery/lottery.html";
	}

	@RequestMapping("lotteryPE")
	public String lotteryPE() {
		return "lotteryPE/lotteryPE.html";
	}

}

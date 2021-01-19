package com.iwhalecloud.lottery.controller;

import com.alibaba.excel.EasyExcel;
import com.iwhalecloud.lottery.entity.Form;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.req.LoginReq;
import com.iwhalecloud.lottery.params.req.LotteryReq;
import com.iwhalecloud.lottery.params.vo.LotteryVO;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用用于提供与抽奖事件有关的接口（抽奖、设置奖项……）
 *
 * @author by W4i
 * @date 2021/1/15 19:53
 */
@RestController
@RequestMapping("lottery")
public class LotteryController {
	@Autowired
	LotteryService lotteryService;

	/**
	 * 导入
	 *
	 * @param file
	 * @return
	 */
	@PostMapping("getUploadExcel")
	public Result getUploadExcel(@RequestParam MultipartFile file, Integer lotteryId) {
		Result result = null;
		List<Staff> staffList = new ArrayList<Staff>();
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
			staffList = EasyExcel.read(inputStream).sheet(0).headRowNumber(1).head(Staff.class).doReadSync();
			result = lotteryService.batchUploadExcel(staffList, lotteryId);
		} catch (Exception e) {
			System.out.println(e);
			result = Result.getFalse("文件解析失败");
		}
		return result;
	}

	/**
	 * 创建抽奖
	 *
	 * @param form
	 * @return
	 */
	@ResponseBody
	@PostMapping("createPrize")
	public Result createPrize(@RequestBody Form form) {
		return lotteryService.createPrize(form);
	}

	/**
	 * 更新抽奖
	 *
	 * @param form
	 * @return
	 */
	@ResponseBody
	@PostMapping("updatePrize")
	public Result updatePrize(@RequestBody Form form) {
		return lotteryService.updatePrize(form);
	}

	/**
	 * 查询奖品列表
	 *
	 * @param loginReq
	 * @return
	 */
	@RequestMapping("getPrizeList")
	public Result getPrizeList(@RequestBody LoginReq loginReq) {
		return lotteryService.getPrizeList(loginReq.getLotteryId());
	}

	/**
	 * 抽奖
	 * @param lotteryReq
	 * @return
	 */
	@RequestMapping("getLottery")
	public Result getLottery(@RequestBody LotteryReq lotteryReq){
		return lotteryService.getLottery(lotteryReq);
	}

	@RequestMapping("roll")
	public Result roll(@RequestBody LotteryReq lotteryReq){
		return lotteryService.roll(lotteryReq);
	}
}


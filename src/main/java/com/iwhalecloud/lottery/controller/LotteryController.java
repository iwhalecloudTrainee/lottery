package com.iwhalecloud.lottery.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.req.FormReq;
import com.iwhalecloud.lottery.params.req.LoginReq;
import com.iwhalecloud.lottery.params.req.LotteryReq;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.params.vo.StaffVO;
import com.iwhalecloud.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

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
	 * 通过excel导入员工
	 *
	 * @param file
	 * @return
	 */
	@PostMapping("getUploadExcel")
	public Result getUploadExcel(@RequestParam MultipartFile file, Integer lotteryId) {
		List<Staff> staffList;
		InputStream inputStream;
		try {
			inputStream = file.getInputStream();
			staffList = EasyExcel.read(inputStream).sheet(0).headRowNumber(1).head(Staff.class).doReadSync();
			return lotteryService.batchUploadExcel(staffList, lotteryId);
		} catch (Exception e) {
			return Result.getFalse("文件解析失败");
		}
	}

	/**
	 * 创建抽奖
	 *
	 * @param formReq
	 * @return
	 */
	@ResponseBody
	@PostMapping("createPrize")
	public Result createPrize(@RequestBody FormReq formReq) {
		return lotteryService.createPrize(formReq);
	}

	/**
	 * 更新抽奖
	 *
	 * @param formReq
	 * @return
	 */
	@ResponseBody
	@PostMapping("updatePrize")
	public Result updatePrize(@RequestBody FormReq formReq) {
		return lotteryService.updatePrize(formReq);
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
	 * 通过lotteryId获取员工数据（等待抽奖）
	 *
	 * @param lotteryReq
	 * @return
	 */
	@RequestMapping("getStaffList")
	public Result getStaffList(@RequestBody LotteryReq lotteryReq) {
		return lotteryService.getStaffList(lotteryReq);
	}

	/**
	 * 抽奖完成，写表
	 *
	 * @param lotteryReq
	 * @return
	 */
	@RequestMapping("setLottery")
	public Result setLottery(@RequestBody LotteryReq lotteryReq) {
		return lotteryService.setLottery(lotteryReq);
	}

	/**
	 * 刷新所有员工为未中奖状态
	 *
	 * @param lotteryReq
	 * @return
	 */
	@RequestMapping("updateStaff")
	public Result updateStaff(@RequestBody LotteryReq lotteryReq) {
		return lotteryService.updateStaff(lotteryReq);
	}

	/**
	 * 下载获奖数据
	 *
	 * @param response
	 * @param lotteryId
	 * @throws IOException
	 */
	@RequestMapping("downloadAward")
	public void downloadAward(HttpServletResponse response, @RequestParam Integer lotteryId) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		Map<String ,Object> map = lotteryService.downloadAward(lotteryId);
		List<Prize> prizeList= (List<Prize>) map.get("prizeList");
		List<StaffVO> staffVOList= (List<StaffVO>) map.get("staffVOList");
		Lottery lottery = lotteryService.getPrizeByLotteryId(lotteryId);
		String fileName = URLEncoder.encode(lottery.getLotteryName() + "获奖数据", "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).autoCloseStream(Boolean.FALSE).build();
		WriteSheet writeSheet = EasyExcel.writerSheet(0, "获奖名单（奖品版）").head(Prize.class).build();
		WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "获奖名单（员工版）").head(StaffVO.class).build();
		excelWriter.write(prizeList, writeSheet);
		excelWriter.write(staffVOList, writeSheet1);
		excelWriter.finish();
	}
}


package com.iwhalecloud.lottery.service;

import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.req.FormReq;
import com.iwhalecloud.lottery.params.req.LotteryReq;
import com.iwhalecloud.lottery.params.vo.Result;

import java.util.List;
import java.util.Map;

public interface LotteryService {
	/**
	 * 批量导入Excel
	 *
	 * @param
	 * @return
	 */
	Result batchUploadExcel(List<Staff> staff, Integer lotteryId);

	/**
	 * 创建奖品
	 *
	 * @param formReq
	 * @return
	 */
	Result createPrize(FormReq formReq);

	/**
	 * 更新抽奖
	 *
	 * @param formReq
	 * @return
	 */
	Result updatePrize(FormReq formReq);

	/**
	 * 查询奖品列表
	 *
	 * @param lotteryId
	 * @return
	 */
	Result getPrizeList(Integer lotteryId);

	Result getStaffList(LotteryReq lotteryReq);

	Result setLottery(LotteryReq lotteryReq);

	Result updateStaff(LotteryReq lotteryReq);

	Map<String ,Object> downloadAward(Integer lotteryId);

	Lottery getPrizeByLotteryId(Integer lotteryId);

	Result getLotteryDic(LotteryReq lotteryReq);

    Result getPrize(LotteryReq lotteryReq);
}

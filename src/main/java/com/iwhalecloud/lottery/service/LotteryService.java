package com.iwhalecloud.lottery.service;

import com.iwhalecloud.lottery.entity.Form;
import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.vo.Result;

import javax.servlet.http.HttpServletRequest;
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
	 * @param form
	 * @return
	 */
	Result createPrize(Form form);

	/**
	 * 更新抽奖
	 *
	 * @param form
	 * @return
	 */
	Result updatePrize(Form form);
}

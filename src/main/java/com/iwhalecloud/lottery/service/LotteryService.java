package com.iwhalecloud.lottery.service;

import com.iwhalecloud.lottery.entity.Form;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.vo.Result;

import java.util.List;

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

    /**
     * 查询奖品列表
     *
     * @param lotteryId
     * @return
     */
    Result getPrizeList(Integer lotteryId);
}

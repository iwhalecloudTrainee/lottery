package com.iwhalecloud.lottery.service;

import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.params.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UploadExcelService {
    /**
     * 批量导入Excel
     * @param request
     * @return
     */
    Result batchUploadExcel(HttpServletRequest request);

    /**
     * 创建奖品
     * @param prize
     * @return
     */
    Result createPrize(Prize prize);
}

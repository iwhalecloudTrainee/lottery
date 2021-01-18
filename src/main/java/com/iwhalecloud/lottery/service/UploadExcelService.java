package com.iwhalecloud.lottery.service;

import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UploadExcelService {
    /**
     * 批量导入Excel
     * @param
     * @return
     */
    Result batchUploadExcel( List<Staff> staff);

    /**
     * 创建奖品
     * @param prize
     * @return
     */
    Result createPrize(Prize prize);
}

package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.mapper.LotteryMapper;
import com.iwhalecloud.lottery.mapper.PrizeMapper;
import com.iwhalecloud.lottery.mapper.StaffMapper;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.UploadExcelService;
import com.iwhalecloud.lottery.utils.ExcelData;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadExcelServiceImpl implements UploadExcelService {
    @Autowired
    LotteryMapper lotteryMapper;
    @Autowired
    PrizeMapper prizeMapper;
    @Autowired
    StaffMapper staffMapper;

    /**
     * 批量上传文件
     * @param staff
     * @return
     */
    @Override
    public Result batchUploadExcel(List<Staff> staff) {
        Result result=null;
        // 默认未中奖
        for (Staff staffMap : staff) {
            staffMap.setState(0);
        }
        //先删除再导入
        staffMapper.deleteBatchData(staff);
        staffMapper.insertBatchExcel(staff);
        result =Result.getSuccess("成功导入"+staff.size()+"条");
        return  result;
    }

    /**
     * 创建奖品
     * @param prize
     * @return
     */
    @Override
    public Result createPrize(Prize prize) {
        try {
            prizeMapper.insertPrize(prize);
        }catch (Exception e){
         return Result.getFalse(e);
        }
        return Result.getSuccess("创建成功");
    }






}

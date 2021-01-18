package com.iwhalecloud.lottery.controller;

import com.alibaba.excel.EasyExcel;
import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.UploadExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
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
    UploadExcelService uploadExcelService;
    /**
     * 导入
     * @param request
     * @return
     */
    @PostMapping("upload")
    public Result getUploadExcel(HttpServletRequest request, @RequestParam MultipartFile file){
        Result result=null;
        List<Staff> staffList = new ArrayList<Staff>();
        InputStream inputStream=null;
        try{
            inputStream=file.getInputStream();
            staffList= EasyExcel.read(inputStream).sheet(0).headRowNumber(1).head(Staff.class).doReadSync();
            result=uploadExcelService.batchUploadExcel(staffList);
        }catch (Exception e){
            System.out.println(e);
            result=Result.getFalse("文件解析失败");
        }
        return result;
    }

    /**
     * 创建奖品
     * @param prize
     * @return
     */
    @ResponseBody
    @GetMapping("prize")
    public  Result createPrize(Prize prize){
        return uploadExcelService.createPrize(prize);
    }
}

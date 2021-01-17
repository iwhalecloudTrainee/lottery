package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.mapper.LotteryMapper;
import com.iwhalecloud.lottery.mapper.PrizeMapper;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.UploadExcelService;
import com.iwhalecloud.lottery.util.ExcelData;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * 批量上传文件
     * @param request
     * @return
     */
    @Override
    public Result batchUploadExcel(HttpServletRequest request) {
        //判断是否为文件上传模式，只判断excel
        List<Map<String, String>> upList = getUploadExcel(request, 2);
        if (upList==null){
            return  Result.getFalse("未检测到数据文件");
        }
        for (int i = 0; i < upList.size(); i++) {
            Map dataList=upList.get(i);
            // copy upList里面的数据
            Lottery lottery=new Lottery();
            BeanUtils.copyProperties(dataList,lottery);
            // 保证每个员工在数据库中只能存在一条数据 先删除再插入
            try {
                lotteryMapper.deleteLottery(lottery);
                lotteryMapper.insertLottery(lottery);
            }catch (Exception e){
                return  Result.getFalse(e);
            }
        }
        return Result.getSuccess("成功导入"+upList.size());

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



    /**
     * 获取excel数据
     * @param request
     * @param rowNumOfColumnKey
     * @return
     */
    private List<Map<String, String>> getUploadExcel(HttpServletRequest request, int rowNumOfColumnKey) {
        RequestContext requestContext = new ServletRequestContext(request);
        if (!ServletFileUpload.isMultipartContent(requestContext)) {
            return null;
        }
        // 获取参数和文件
        Map<String, String> params = new HashMap<String, String>();
        List<String> files = new ArrayList<String>();
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(10 * 1000 * 1000);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(20 * 1000 * 1000);
            List items = upload.parseRequest(request);
            System.out.println(items);
            for (int i = 0; i < items.size(); i++) {
                FileItem fi = (FileItem) items.get(i);
                if (fi.isFormField()) {
                    String filedValue = fi.getString();
                    try {
                        filedValue = URLDecoder.decode(filedValue, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                    }
                    params.put(fi.getFieldName(), filedValue);
                } else {
                    if (fi.getSize() == 0) {
                        continue;
                    }
                    // 格式只能为xls
                    File f = File.createTempFile("temp", ".xls");
                    fi.write(f);
                    files.add(f.getPath());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
        request.setAttribute("UPFILE_PARAMS", params);
        if (files.size() == 0) {
            return null;
        } else {
            String filePath = files.get(0);
            request.setAttribute("UPFILE_PATH", filePath);
            return ExcelData.getSheetData(filePath, rowNumOfColumnKey);
        }
    }


}

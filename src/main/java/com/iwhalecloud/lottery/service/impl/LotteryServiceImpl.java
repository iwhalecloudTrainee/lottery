package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Form;
import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.mapper.LotteryMapper;
import com.iwhalecloud.lottery.mapper.PrizeMapper;
import com.iwhalecloud.lottery.mapper.StaffMapper;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.LotteryService;
import com.iwhalecloud.lottery.utils.EncryptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryServiceImpl implements LotteryService {
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
    public Result batchUploadExcel(List<Staff> staff,Integer lotteryId) {
        Result result=null;
        // 默认未中奖
        for (Staff staffMap : staff) {
            staffMap.setLotteryId(lotteryId);
            staffMap.setState(0);
        }
//        //先删除再导入
//        staffMapper.deleteBatchData(staff);
        staffMapper.insertBatchExcel(staff);
        result =Result.getSuccess("成功导入"+staff.size()+"条");
        return  result;
    }

    /**
     * 创建奖品
     * @param form
     * @return
     */
    @Override
    public Result createPrize(Form form) {
        Lottery lottery=new Lottery();
        //copy lottery数据
        BeanUtils.copyProperties(form, lottery);
        lottery.setState(1);
        int lotteryId = lotteryMapper.insertLottery(lottery);
        // 获取奖品list
        List<Prize> prize = form.getPrize();
        for (Prize prizeMap : prize) {
            prizeMap.setLotteryId(lotteryId);
        }
        prizeMapper.insertPrize(prize);
        return Result.getSuccess("创建成功");
    }

    /**
     * 更新奖品
     * @param form
     * @return
     */
    @Override
    public Result updatePrize(Form form) {
        Lottery lottery=new Lottery();
        //copy lottery数据
        BeanUtils.copyProperties(form, lottery);
        Integer lotteryId = lottery.getLotteryId();
        Lottery lotteryData = lotteryMapper.selectByPrimaryKey(lotteryId);
        Integer state = lotteryData.getState();
        // 判断state如果state为1则可以修改 为0 return
        if (state==0){
            return Result.getFalse("已抽奖：无法修改！！");
        }
        // 更新抽奖表
        lotteryMapper.updateByPrimaryKey(lottery);
        List<Prize> prize = form.getPrize();
        for (Prize prizeMap : prize) {
            prizeMap.setLotteryId(lotteryId);
        }
        // 先删除后insert达到更新奖品的目的
        prizeMapper.deleteBatch(prize);
        prizeMapper.insertPrize(prize);
        return Result.getSuccess("更新成功！！");
    }
}

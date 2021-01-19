package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.*;
import com.iwhalecloud.lottery.mapper.LotteryMapper;
import com.iwhalecloud.lottery.mapper.PrizeMapper;
import com.iwhalecloud.lottery.mapper.StaffMapper;
import com.iwhalecloud.lottery.params.req.FormReq;
import com.iwhalecloud.lottery.params.req.LotteryReq;
import com.iwhalecloud.lottery.params.vo.LotteryVO;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.LotteryService;
import com.iwhalecloud.lottery.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     *
     * @param staff
     * @return
     */
    @Override
    public Result batchUploadExcel(List<Staff> staff, Integer lotteryId) {
        Result result = null;
        // 默认未中奖
        for (Staff staffMap : staff) {
            staffMap.setLotteryId(lotteryId);
            staffMap.setState(0);
        }
//        //先删除再导入
//        staffMapper.deleteBatchData(staff);
        staffMapper.insertBatchExcel(staff);
        result = Result.getSuccess("成功导入" + staff.size() + "条");
        return result;
    }

    /**
     * 创建奖品
     *
     * @param formReq
     * @return
     */
    @Override
    public Result createPrize(FormReq formReq) {
        Lottery lottery = new Lottery();
        //copy lottery数据
        BeanUtils.copyProperties(formReq, lottery);
        lottery.setState(1);
        // md5加密
        lottery.setPassword(MD5Util.getMD5String(lottery.getPassword()));
        lotteryMapper.insertLottery(lottery);
        // 获取奖品list
        List<Prize> prizes = formReq.getPrizes();
        for (Prize prize : prizes) {
            prize.setLotteryId(lottery.getLotteryId());
        }
        prizeMapper.insertPrize(prizes);
        return Result.getSuccess(lottery.getLotteryId());
    }

    /**
     * 更新奖品
     *
     * @param formReq
     * @return
     */
    @Override
    public Result updatePrize(FormReq formReq) {
        Lottery lottery = new Lottery();
        //copy lottery数据
        BeanUtils.copyProperties(formReq, lottery);
        Integer lotteryId = lottery.getLotteryId();
        Lottery lotteryData = lotteryMapper.selectByPrimaryKey(lotteryId);
        Integer state = lotteryData.getState();
        // 判断state如果state为1则可以修改 为0 return
        if (state == 0) {
            return Result.getFalse("已抽奖：无法修改！！");
        }
        // 更新抽奖表
        lotteryMapper.updateByPrimaryKeySelective(lottery);
        List<Prize> prizes = formReq.getPrizes();
        for (Prize prizeMap : prizes) {
            prizeMap.setLotteryId(lotteryId);
        }
        // 先删除后insert达到更新奖品的目的
        prizeMapper.deleteBatch(prizes);
        prizeMapper.insertPrize(prizes);
        return Result.getSuccess("更新成功！！");
    }

    @Override
    public Lottery selectLottery(Integer id) {
        return lotteryMapper.selectLottery(id);
    }

    @Override
    public Result getPrizeList(Integer lotteryId) {
        Prize prize = new Prize();
        prize.setLotteryId(lotteryId);
        List<Prize> prizeList = prizeMapper.select(prize);
        Lottery lottery = lotteryMapper.selectByPrimaryKey(lotteryId);
        if (null == lottery) {
            return Result.getFalse();
        }
        LotteryVO lotteryVO = new LotteryVO();
        lotteryVO.setLotteryId(lottery.getLotteryId());
        lotteryVO.setLotteryName(lottery.getLotteryName());
        lotteryVO.setPrizeList(prizeList);
        return Result.getSuccess(lotteryVO);
    }

    @Override
    public Result getLottery(LotteryReq lotteryReq) {
        if (null == lotteryReq.getLotteryId()) {
            return Result.getFalse("输入有误");
        }
        //设置查询条件
        Staff staff = new Staff();
        staff.setLotteryId(lotteryReq.getLotteryId());
        staff.setState(0);
        //查出符合条件的所有人员
        List<Staff> staffList = staffMapper.select(staff);
        //随机取出一个幸运观众中奖
        int count = staffList.size();
        Random random = new Random();
        int awardIndex = random.nextInt(count);
        BeanUtils.copyProperties(staffList.get(awardIndex), staff);
        //准备假数据制造节目效果
        Staff staffFake = new Staff();
        staffFake.setLotteryId(lotteryReq.getLotteryId());
        List<Staff> staffFakeList = staffMapper.select(staffFake);
        List<Staff> staffRollData = new ArrayList<>();
        int i = 0;
        //写99个假数据
        while (i < 73) {
            for (Staff staff1 : staffFakeList) {
                Staff staff2=new Staff();
                BeanUtils.copyProperties(staff1,staff2);
                staff2.setStaffName(staff2.getStaffCode()+" "+staff2.getStaffName());
                staffRollData.add(staff2);
                if (staffRollData.size() == 74) {
                    break;
                }
                i++;
            }
        }
        System.out.println(staff);
        staff.setStaffName(staff.getStaffCode()+" "+staff.getStaffName());
        staffRollData.add(staff);
        return Result.getSuccess(staffRollData);
    }


}

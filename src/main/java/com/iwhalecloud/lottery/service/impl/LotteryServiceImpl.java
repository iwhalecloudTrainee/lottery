package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.mapper.LotteryMapper;
import com.iwhalecloud.lottery.mapper.PrizeMapper;
import com.iwhalecloud.lottery.mapper.StaffMapper;
import com.iwhalecloud.lottery.params.req.FormReq;
import com.iwhalecloud.lottery.params.req.LotteryReq;
import com.iwhalecloud.lottery.params.vo.LotteryVO;
import com.iwhalecloud.lottery.params.vo.PrizeVO;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.LotteryService;
import com.iwhalecloud.lottery.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
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
     *
     * @param staff
     * @return
     */
    @Override
    public Result batchUploadExcel(List<Staff> staff, Integer lotteryId) {
        if (null==lotteryId){
            return Result.getFalse();
        }
        Result result = null;
        // 默认未中奖
        for (Staff staffMap : staff) {
            staffMap.setLotteryId(lotteryId);
            staffMap.setState(0);
        }
        //先删除再导入
        Staff staff1 = new Staff();
        staff1.setLotteryId(lotteryId);
        staffMapper.delete(staff1);
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
        if (null==formReq.getLotteryId()){
            return Result.getFalse();
        }
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
        lottery.setPassword(MD5Util.getMD5String(lottery.getPassword()));

        // 更新抽奖表
        lotteryMapper.updateByPrimaryKeySelective(lottery);
        List<Prize> prizes = formReq.getPrizes();
        for (Prize prizeMap : prizes) {
            prizeMap.setLotteryId(lotteryId);
        }
        // 先删除后insert达到更新奖品的目的
        prizeMapper.deleteBatch(lotteryId);
        prizeMapper.insertPrize(prizes);
        return Result.getSuccess(lotteryId);
    }

    /**
     * 通过lotteryId获取抽奖抽奖信息
     *
     * @param lotteryId
     * @return
     */
    @Override
    public Result getPrizeList(Integer lotteryId) {
        Prize prize = new Prize();
        //设置条件
        prize.setLotteryId(lotteryId);
        List<Prize> prizeList = prizeMapper.select(prize);
        List<PrizeVO> prizeVOList = new ArrayList<>();
        for (Prize prize1 : prizeList) {
            PrizeVO prizeVO = new PrizeVO();
            BeanUtils.copyProperties(prize1, prizeVO);
            if (prizeVO.getNum() < 1) {
                prizeVO.setDisable(true);
            } else {
                prizeVO.setDisable(false);
            }
            prizeVOList.add(prizeVO);
        }
        //获取抽奖基本信息
        Lottery lottery = lotteryMapper.selectByPrimaryKey(lotteryId);
        if (null == lottery) {
            return Result.getFalse();
        }
        //设置奖品信息
        LotteryVO lotteryVO = new LotteryVO();
        lotteryVO.setLotteryId(lottery.getLotteryId());
        lotteryVO.setLotteryName(lottery.getLotteryName());
        lotteryVO.setPrizeList(prizeVOList);
        return Result.getSuccess(lotteryVO);
    }

    /**
     * 通过lotteryId获取符合要求的staff
     *
     * @param lotteryReq
     * @return
     */
    @Override
    public Result getStaffList(LotteryReq lotteryReq) {
        if (null == lotteryReq.getLotteryId()) {
            return Result.getFalse("输入有误");
        }
        //设置条件
        Staff staff = new Staff();
        staff.setLotteryId(lotteryReq.getLotteryId());
        staff.setState(0);
        //打乱顺序
        List<Staff> staffList = staffMapper.select(staff);
//        for (Staff staff1 : staffList) {
//            staff1.setStaffName(staff1.getStaffCode() + " " + staff1.getStaffName());
//        }
        Collections.shuffle(staffList);
        return Result.getSuccess(staffList);
    }

    /**
     * 抽奖完成，写表
     *
     * @param lotteryReq
     * @return
     */
    @Override
    public Result setLottery(LotteryReq lotteryReq) {
        Staff staff = new Staff();

        if (null == lotteryReq.getLotteryId() || null == lotteryReq.getPrizeId() || !StringUtils.hasText(lotteryReq.getStaffName())) {
            return Result.getFalse("输入有误");
        } else {
            try {
                //拆分staffName，通过staffId和lotteryId查询staff，为防止格式报错，放在tryCatch里面
                String staffNameStr[] = lotteryReq.getStaffName().split("\n");
                String staffCode = staffNameStr[0];
                staff.setStaffCode(staffCode);
                staff.setLotteryId(lotteryReq.getLotteryId());
                staff.setState(0);
                staff = staffMapper.selectOne(staff);
                if (null == staff) {
                    return Result.getFalse("输入有误");
                }
            } catch (Exception e) {
            }
        }
        //通过入参获取staff，prize，lottery信息
        Prize prize = prizeMapper.selectByPrimaryKey(lotteryReq.getPrizeId());
        Lottery lottery = lotteryMapper.selectByPrimaryKey(lotteryReq.getLotteryId());
        if (null == staff || null == prize || null == lottery) {
            return Result.getFalse("输入有误");
        }
        //更新staff
        staff.setState(1);
        staffMapper.updateByPrimaryKey(staff);
        //更新prize
        if (null == prize.getStaffName()) {
            prize.setStaffName(staff.getStaffCode() + " " + staff.getStaffName());
        } else {
            prize.setStaffName(staff.getStaffCode() + " " + staff.getStaffName() + " , " + prize.getStaffName());
        }
        prize.setNum(prize.getNum() - 1);
        prizeMapper.updateByPrimaryKey(prize);
        //更新lottery
        lottery.setState(0);
        lotteryMapper.updateByPrimaryKey(lottery);
        return Result.getSuccess();
    }
}

package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Award;
import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.mapper.AwardMapper;
import com.iwhalecloud.lottery.mapper.StaffMapper;
import com.iwhalecloud.lottery.params.req.LoginReq;
import com.iwhalecloud.lottery.params.vo.AwardVO;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.AwardService;
import com.iwhalecloud.lottery.service.LotteryService;
import com.iwhalecloud.lottery.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wz
 * @date 2021/1/19 15:51
 * @description
 */
@Service
public class AwardServiceImpl implements AwardService {
    @Resource
    private AwardMapper awardMapper;

    @Autowired
    PrizeService prizeService;

    @Autowired
    LotteryService lotteryService;

    @Autowired
    StaffMapper staffMapper;

    @Override
    public Result getAward(LoginReq loginReq) {
        List<Award> awardList = awardMapper.selectAwardById(loginReq.getLotteryId());
        List<AwardVO> awardVoList = new ArrayList<>();
        for (Award award : awardList) {
            Prize p = prizeService.selectPrizeById(award.getPrizeId());
            Lottery lottery = lotteryService.selectLottery(award.getLotteryId());
            Staff staff = staffMapper.selectByPrimaryKey(award.getStaffId());
            AwardVO awardVo = new AwardVO(p.getPrizeId(), p.getPrizeName(), lottery.getLotteryId(), lottery.getLotteryName(), staff.getStaffId(), staff.getStaffName());
            awardVoList.add(awardVo);
        }
        return Result.getSuccess(awardVoList);
    }
}

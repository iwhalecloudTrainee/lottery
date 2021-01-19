package com.iwhalecloud.lottery.service;


import com.iwhalecloud.lottery.params.req.AwardReq;
import com.iwhalecloud.lottery.params.vo.Result;

public interface AwardService {

    Result getAward(AwardReq awardReq);
}

package com.iwhalecloud.lottery.service;


import com.iwhalecloud.lottery.params.req.LoginReq;
import com.iwhalecloud.lottery.params.vo.Result;

public interface AwardService {

    Result getAward(LoginReq loginReq);
}

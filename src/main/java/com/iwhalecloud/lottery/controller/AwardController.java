package com.iwhalecloud.lottery.controller;

import com.iwhalecloud.lottery.params.req.LoginReq;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.AwardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("award")
public class AwardController {

    @Resource
    private AwardService awardService;

    @PostMapping("getAward")
    public Result getAward(@RequestBody LoginReq loginReq) {
        return awardService.getAward(loginReq);
    }
}

package com.iwhalecloud.lottery.controller;

import com.iwhalecloud.lottery.params.req.AwardReq;
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
    public Result getAward(@RequestBody AwardReq awardReq) {

        Result result = awardService.getAward(awardReq);
        return result;
    }
}

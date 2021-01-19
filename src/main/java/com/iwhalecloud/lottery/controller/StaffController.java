package com.iwhalecloud.lottery.controller;

import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.req.StaffReq;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.StaffService;
import com.iwhalecloud.lottery.utils.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author: pei.hongjun@iwhalecloud.com
 * @date: 2021/1/18 23：46
 */
@RestController
@RequestMapping("staff")
@Slf4j
public class StaffController {

    @Resource
    private StaffService staffService;

    @PostMapping("getStaff")
    public Result getStaff(@RequestBody StaffReq staffReq) {

        Result result = staffService.getStaff(staffReq);
        return result;
    }

}

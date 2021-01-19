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

    /**
     * 抽奖的功能  随机抽三个staffid作为中奖人，就相当于从staff表里的staffid随机抽三个数，然后把staff表里的staffstate从0改为1
     */
    @GetMapping("staffLottery")
    public Result staffLottery() {

        List<Staff> staffList = staffService.selectStaffByStatus(0);

       int[] r = radm(3,staffList.size());

       for(int i=0; i < r.length; i++) {

           System.out.println(staffList.get(r[i]).getStaffName());
       }



        return Result.getSuccess();
    }

    public int[] radm(int n, int k) { //需要整数数量(n必须小于等于k) //整数随机的取值范围

        int[] r = new int[n];
        int index = 0;
        Random rand = new Random();//新建一个随机类
        boolean[] bool = new boolean[k + 1];
        int randInt = 0;
        for (int i = 0; i < n; i++) {
            do {
                randInt = 1 + rand.nextInt(k);//产生一个随机数
            } while (bool[randInt]);
            bool[randInt] = true;
            r[index++] = randInt;
        }

        return r;
    }
}

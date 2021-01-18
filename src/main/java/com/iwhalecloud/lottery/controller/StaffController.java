package com.iwhalecloud.lottery.controller;

import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.StaffService;
import com.iwhalecloud.lottery.utils.JSONResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: pei.hongjun@iwhalecloud.com
 * @date: 2021/1/18 23：46
 */
@RestController
@RequestMapping("staff")
public class StaffController {

    @Resource
    private StaffService staffService;

    @GetMapping("getAll")
    public JSONResult getAll() {
        List<Staff> list = staffService.getAll();
        return new JSONResult(0, "获得所有用户信息成功！",list.size(), list);
    }
}

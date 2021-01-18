package com.iwhalecloud.lottery.service;

import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.params.vo.Result;

import java.util.List;

/**
 * @title: StaffService
 * @description: 员工查看奖品
 * @author: pei.hongjun@iwhalecloud.com
 * @date: 2021-1-18 23:24
 */

public interface StaffService {

    List<Staff> getAll();

}

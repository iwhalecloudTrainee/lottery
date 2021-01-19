package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.mapper.StaffMapper;
import com.iwhalecloud.lottery.params.req.StaffReq;
import com.iwhalecloud.lottery.params.vo.Result;
import com.iwhalecloud.lottery.service.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Resource
    private StaffMapper staffMapper;

    @Override
    public Result getStaff(StaffReq staffReq) {
       // staffReq.getLotteryId();
         List<Staff>  staffList= staffMapper.selectStaff(staffReq.getLotteryId());

        return Result.getSuccess(staffList);

    }

    @Override
    public Staff selectStaffById(Integer id) {
        return staffMapper.selectStaffById(id);
    }

    @Override
    public List<Staff> allStaff() {
        return staffMapper.selectAll();
    }

    @Override
    public List<Staff> selectStaffByStatus(Integer status) {
        return staffMapper.selectStaffByStatus(status);
    }


}

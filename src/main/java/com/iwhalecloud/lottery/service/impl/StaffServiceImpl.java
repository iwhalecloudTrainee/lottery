package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.mapper.StaffMapper;
import com.iwhalecloud.lottery.service.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class StaffServiceImpl implements StaffService {

    @Resource
    private StaffMapper staffMapper;

    @Override
    public List<Staff> getStaff() {

        return staffMapper.selectStaff();
    }
}

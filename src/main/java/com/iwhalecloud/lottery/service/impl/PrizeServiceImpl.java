package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.mapper.PrizeMapper;
import com.iwhalecloud.lottery.service.PrizeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wz
 * @date 2021/1/19 16:11
 * @description
 */
@Service
public class PrizeServiceImpl implements PrizeService {

    @Resource
    private PrizeMapper prizeMapper;

    @Override
    public Prize selectPrizeById(Integer prizeId) {
        return prizeMapper.selectPrizeById(prizeId);
    }
}

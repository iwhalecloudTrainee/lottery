package com.iwhalecloud.lottery.mapper;

import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Prize;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface PrizeMapper extends Mapper<Prize> {
    // 插入insert
    int insertPrize(Prize prize);
}
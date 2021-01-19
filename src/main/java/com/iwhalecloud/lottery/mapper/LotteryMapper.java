package com.iwhalecloud.lottery.mapper;

import com.iwhalecloud.lottery.entity.Lottery;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface LotteryMapper extends Mapper<Lottery> {
	// 插入insert
	int insertLottery(Lottery lottery);

	Lottery selectLottery(Integer id);

}
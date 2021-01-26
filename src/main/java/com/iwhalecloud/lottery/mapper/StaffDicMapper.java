package com.iwhalecloud.lottery.mapper;

import com.iwhalecloud.lottery.entity.StaffDic;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface StaffDicMapper extends Mapper<StaffDic> {
	void updateStaff(@Param("lotteryId") Integer lotteryId);
}
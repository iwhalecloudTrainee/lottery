package com.iwhalecloud.lottery.mapper;

import com.iwhalecloud.lottery.entity.Award;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface AwardMapper extends Mapper<Award> {

    List<Award> selectAwardById(Integer lotteryId);
}
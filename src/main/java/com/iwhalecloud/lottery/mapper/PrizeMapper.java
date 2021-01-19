package com.iwhalecloud.lottery.mapper;

import com.iwhalecloud.lottery.entity.Prize;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface PrizeMapper extends Mapper<Prize> {
	// 插入insert
	int insertPrize(List<Prize> prize);

	// 批量更新
	int updateBatch(List<Prize> prize);

	// 批量删除
	int deleteBatch(List<Prize> prize);

	Prize selectPrizeById(Integer id);
}
package com.iwhalecloud.lottery.mapper;

import com.iwhalecloud.lottery.entity.Staff;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface StaffMapper extends Mapper<Staff> {
	//批量导入
	int insertBatchExcel(List<Staff> staff);

	// 批量删除
	int deleteBatchData(List<Staff> staff);

	List<Staff> selectStaff(Integer lotteryId);

	List<Staff> selectStaffByStatus(Integer status);

	int updateStaff(@Param("lotteryId") Integer lotteryId);
}

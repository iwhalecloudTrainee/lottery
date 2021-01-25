package com.iwhalecloud.lottery.params.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author by W4i
 * @date 2021/1/25 22:10
 */
@Data
public class AwardVO {
	@ExcelProperty(value = "奖项",index = 0)
	private String prizeLevel;
	@ExcelProperty(value = "奖品",index = 1)
	private String prizeName;
	@ExcelProperty(value = "数量",index = 2)
	private Integer num;
	@ExcelProperty(value = "获奖员工名单",index = 3)
	private String staffName;
}

package com.iwhalecloud.lottery.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "prize")
public class Prize {
	/**
	 * 奖品id
	 */
	@Id
	@Column(name = "prize_id")
	@ExcelIgnore
	private Integer prizeId;

	/**
	 * 奖品名字
	 */
	@Column(name = "prize_name")
	@ColumnWidth(15)
	@ExcelProperty(value="奖品", index=1)
	private String prizeName;

	/**
	 * 数量
	 */
	@ColumnWidth(15)
	@ExcelProperty(value="余量", index=2)
	private Integer num;

	/**
	 * 抽奖id
	 */
	@Column(name = "lottery_id")
	@ExcelIgnore
	private Integer lotteryId;

	@Column(name = "staff_name")
	@ColumnWidth(255)
	@ExcelProperty(value="获奖员工名单", index=4)
	private String staffName;

	@ColumnWidth(15)
	@ExcelProperty(value="总量", index=3)
	@Column(name = "count")
	private Integer count;

	/**
	 * 奖项
	 */
	@Column(name = "prize_level")
	@ColumnWidth(15)
	@ExcelProperty(value="奖项", index=0)
	private String prizeLevel;
}

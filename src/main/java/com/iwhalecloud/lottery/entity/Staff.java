package com.iwhalecloud.lottery.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "staff")
public class Staff {
	/**
	 * id
	 */
	@Id
	@Column(name = "staff_id")
	private String staffId;
	/**
	 * 工号
	 */
	@Id
	@Column(name = "staff_code")
	@ExcelProperty("工号")
	private String staffCode;

	/**
	 * 名字
	 */
	@Column(name = "staff_name")
	@ExcelProperty("员工姓名")
	private String staffName;

	/**
	 * 所属抽奖
	 */
	@Column(name = "lottery_id")
	private Integer lotteryId;

	/**
	 * 0：未中奖，任可继续抽奖，1：已中奖，移除奖池
	 */
	@Column(name = "state")
	private Integer state;

}
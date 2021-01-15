package com.iwhalecloud.lottery.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "staff")
public class Staff {
	/**
	 * 工号
	 */
	@Id
	@Column(name = "staff_code")
	private String staffCode;

	/**
	 * 名字
	 */
	@Column(name = "staff_name")
	private String staffName;

	/**
	 * 所属抽奖
	 */
	@Column(name = "lottery_id")
	private Integer lotteryId;

}
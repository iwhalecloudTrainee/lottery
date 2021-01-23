package com.iwhalecloud.lottery.entity;

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
	private Integer prizeId;

	/**
	 * 奖品名字
	 */
	@Column(name = "prize_name")
	private String prizeName;

	/**
	 * 数量
	 */
	private Integer num;

	/**
	 * 抽奖id
	 */
	@Column(name = "lottery_id")
	private Integer lotteryId;

	@Column(name = "staff_name")
	private String staffName;

	@Column(name = "count")
	private Integer count;

	/**
	 * 奖项
	 */
	@Column(name = "prize_level")
	private String prizeLevel;
}
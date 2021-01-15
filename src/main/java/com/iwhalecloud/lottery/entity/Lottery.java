package com.iwhalecloud.lottery.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "lottery")
public class Lottery {
	/**
	 * 抽奖id
	 */
	@Id
	@Column(name = "lottery_id")
	private Integer lotteryId;

	/**
	 * 抽奖名字
	 */
	@Column(name = "lottery_name")
	private String lotteryName;

	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Integer userId;

}
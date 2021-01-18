package com.iwhalecloud.lottery.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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
	 * 1：还可以修改，0：不能再修改
	 * 当改lottery进行了第一次抽奖后，state改为0（或者每次抽奖都更新一下lottery.state）
	 */
	@Column(name = "state")
	private Integer state;
	/**
	 * 用户密码
	 */
	@Column(name = "password")
	private String password;

}
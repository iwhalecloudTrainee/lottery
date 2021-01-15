package com.iwhalecloud.lottery.entity;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

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

	/**
	 * 0：未中奖，任可继续抽奖，1：已中奖，移除奖池
	 */
	@Column(name = "state")
	private Integer state;

}
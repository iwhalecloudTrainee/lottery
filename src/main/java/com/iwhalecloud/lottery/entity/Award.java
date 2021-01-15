package com.iwhalecloud.lottery.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "award")
public class Award {
	/**
	 * 获奖记录id
	 */
	@Id
	@Column(name = "award_id")
	private Integer awardId;

	/**
	 * 奖品id
	 */
	@Column(name = "prize_id")
	private Integer prizeId;

	/**
	 * 工号
	 */
	@Column(name = "staff_code")
	private String staffCode;

	/**
	 * 时间
	 */
	private Date time;

}
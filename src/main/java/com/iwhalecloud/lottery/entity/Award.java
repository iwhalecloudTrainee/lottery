package com.iwhalecloud.lottery.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
	@Column(name = "staff_id")
	private Integer staffId;

	/**
	 * 时间
	 */
	private Date time;

	private Integer lotteryId;

}

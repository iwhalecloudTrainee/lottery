package com.iwhalecloud.lottery.params.req;

import com.iwhalecloud.lottery.entity.Prize;
import lombok.Data;

import java.util.List;

@Data
public class FormReq {
	/**
	 * 抽奖id
	 */
	private Integer lotteryId;

	/**
	 * 抽奖名字
	 */
	private String lotteryName;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 1：还可以修改，0：不能再修改
	 * 当改lottery进行了第一次抽奖后，state改为0（或者每次抽奖都更新一下lottery.state）
	 */
	private Integer state;

	/**
	 * 奖品
	 */
	private List<Prize> prizes;
}

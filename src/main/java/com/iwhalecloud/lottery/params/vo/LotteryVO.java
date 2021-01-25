package com.iwhalecloud.lottery.params.vo;

import lombok.Data;

import java.util.List;

/**
 * @author W4i
 * @date 2021/1/19 16:09
 */
@Data
public class LotteryVO {
	private Integer lotteryId;
	private String lotteryName;
	private Integer state;
	private List<PrizeVO> prizeList;
}

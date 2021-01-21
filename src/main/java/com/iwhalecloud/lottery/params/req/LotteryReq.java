package com.iwhalecloud.lottery.params.req;

import lombok.Data;

/**
 * @author W4i
 * @date 2021/1/19 18:36
 */
@Data
public class LotteryReq {
	private Integer lotteryId;
	private Integer prizeId;
	private String staffName;
}

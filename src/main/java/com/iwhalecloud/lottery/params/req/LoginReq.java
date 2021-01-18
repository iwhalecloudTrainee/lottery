package com.iwhalecloud.lottery.params.req;

import lombok.Data;

/**
 * @author by W4i
 * @date 2021/1/18 20:42
 */
@Data
public class LoginReq {
	private Integer lotteryId;
	private String password;
}

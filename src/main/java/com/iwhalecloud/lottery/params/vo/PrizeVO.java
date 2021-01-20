package com.iwhalecloud.lottery.params.vo;

import lombok.Data;

/**
 * @author W4i
 * @date 2021/1/20 11:19
 */
@Data
public class PrizeVO {

    private Integer prizeId;
    private String prizeName;
    private String num;
    private Integer lotteryId;
    private String staffName;
    private boolean disable;
}

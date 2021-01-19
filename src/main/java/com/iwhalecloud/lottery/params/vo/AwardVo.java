package com.iwhalecloud.lottery.params.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class AwardVo {

    private Integer prizeId;

    private String prizeName;

    private Integer lotteryId;

    private String lotteryName;

    private String staffId;

    private String staffName;

    public AwardVo(Integer prizeId, String prizeName, Integer lotteryId, String lotteryName, String staffId, String staffName) {
        this.prizeId = prizeId;
        this.prizeName = prizeName;
        this.lotteryId = lotteryId;
        this.lotteryName = lotteryName;
        this.staffId = staffId;
        this.staffName = staffName;
    }
}

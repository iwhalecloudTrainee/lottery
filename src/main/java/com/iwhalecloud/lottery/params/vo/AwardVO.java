package com.iwhalecloud.lottery.params.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class AwardVO {

    private Integer prizeId;

    private String prizeName;

    private Integer lotteryId;

    private String lotteryName;

    private Integer staffId;

    private String staffName;

    public AwardVO(Integer prizeId, String prizeName, Integer lotteryId, String lotteryName, Integer staffId, String staffName) {
        this.prizeId = prizeId;
        this.prizeName = prizeName;
        this.lotteryId = lotteryId;
        this.lotteryName = lotteryName;
        this.staffId = staffId;
        this.staffName = staffName;
    }
}

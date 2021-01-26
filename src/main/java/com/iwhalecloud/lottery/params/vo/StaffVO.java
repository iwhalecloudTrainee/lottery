package com.iwhalecloud.lottery.params.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author W4i
 * @date 2021/1/26 9:18
 */
@Data
public class StaffVO {
    @ColumnWidth(20)
    @ExcelProperty(value = "工号", index = 0)
    private String staffCode;

    @ColumnWidth(20)
    @ExcelProperty(value = "名字", index = 1)
    private String staffName;

    @ColumnWidth(20)
    @ExcelProperty(value = "奖项", index = 2)
    private String prizeLevel;

    @ColumnWidth(20)
    @ExcelProperty(value = "奖品", index = 3)
    private String prizeName;
}

package com.iwhalecloud.lottery.params.vo;

import lombok.Data;

import java.util.List;

/**
 * @author by W4i
 * @date 2021/1/26 20:01
 */
@Data
public class StaffDicVO {
	private List<String> staffNameList1;
	private List<String> staffNameList2;
	private List<String> staffNameList3;
	private List<String> staffCodeList1;
	private List<String> staffCodeList2;
	private List<String> staffCodeList3;
	private List<String> staffAwardName;
	private List<String> staffAwardCode;
}

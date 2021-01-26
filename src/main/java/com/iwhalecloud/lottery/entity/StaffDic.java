package com.iwhalecloud.lottery.entity;

import javax.persistence.*;

@Table(name = "staff_dic")
public class StaffDic {
    @Id
    @Column(name = "dic_id")
    private Integer dicId;

    @Column(name = "lottery_id")
    private Integer lotteryId;

    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "staff_name1")
    private String staffName1;

    @Column(name = "staff_name2")
    private String staffName2;

    @Column(name = "staff_name3")
    private String staffName3;

    @Column(name = "staff_code1")
    private String staffCode1;

    @Column(name = "staff_code2")
    private String staffCode2;

    @Column(name = "staff_code3")
    private String staffCode3;

    /**
     * 0未中奖，1已中奖
     */
    private Integer state;

    /**
     * @return dic_id
     */
    public Integer getDicId() {
        return dicId;
    }

    /**
     * @param dicId
     */
    public void setDicId(Integer dicId) {
        this.dicId = dicId;
    }

    /**
     * @return lottery_id
     */
    public Integer getLotteryId() {
        return lotteryId;
    }

    /**
     * @param lotteryId
     */
    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    /**
     * @return staff_id
     */
    public Integer getStaffId() {
        return staffId;
    }

    /**
     * @param staffId
     */
    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    /**
     * @return staff_name1
     */
    public String getStaffName1() {
        return staffName1;
    }

    /**
     * @param staffName1
     */
    public void setStaffName1(String staffName1) {
        this.staffName1 = staffName1;
    }

    /**
     * @return staff_name2
     */
    public String getStaffName2() {
        return staffName2;
    }

    /**
     * @param staffName2
     */
    public void setStaffName2(String staffName2) {
        this.staffName2 = staffName2;
    }

    /**
     * @return staff_name3
     */
    public String getStaffName3() {
        return staffName3;
    }

    /**
     * @param staffName3
     */
    public void setStaffName3(String staffName3) {
        this.staffName3 = staffName3;
    }

    /**
     * @return staff_code1
     */
    public String getStaffCode1() {
        return staffCode1;
    }

    /**
     * @param staffCode1
     */
    public void setStaffCode1(String staffCode1) {
        this.staffCode1 = staffCode1;
    }

    /**
     * @return staff_code2
     */
    public String getStaffCode2() {
        return staffCode2;
    }

    /**
     * @param staffCode2
     */
    public void setStaffCode2(String staffCode2) {
        this.staffCode2 = staffCode2;
    }

    /**
     * @return staff_code3
     */
    public String getStaffCode3() {
        return staffCode3;
    }

    /**
     * @param staffCode3
     */
    public void setStaffCode3(String staffCode3) {
        this.staffCode3 = staffCode3;
    }

    /**
     * 获取0未中奖，1已中奖
     *
     * @return state - 0未中奖，1已中奖
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置0未中奖，1已中奖
     *
     * @param state 0未中奖，1已中奖
     */
    public void setState(Integer state) {
        this.state = state;
    }
}
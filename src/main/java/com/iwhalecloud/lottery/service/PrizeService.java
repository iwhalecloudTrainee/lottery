package com.iwhalecloud.lottery.service;


import com.iwhalecloud.lottery.entity.Prize;

public interface PrizeService {

   Prize selectPrizeById(Integer prizeId);
}

package com.iwhalecloud.lottery.service.impl;

import com.iwhalecloud.lottery.entity.Lottery;
import com.iwhalecloud.lottery.entity.Prize;
import com.iwhalecloud.lottery.entity.Staff;
import com.iwhalecloud.lottery.entity.StaffDic;
import com.iwhalecloud.lottery.mapper.LotteryMapper;
import com.iwhalecloud.lottery.mapper.PrizeMapper;
import com.iwhalecloud.lottery.mapper.StaffDicMapper;
import com.iwhalecloud.lottery.mapper.StaffMapper;
import com.iwhalecloud.lottery.params.req.FormReq;
import com.iwhalecloud.lottery.params.req.LotteryReq;
import com.iwhalecloud.lottery.params.vo.*;
import com.iwhalecloud.lottery.service.LotteryService;
import com.iwhalecloud.lottery.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class LotteryServiceImpl implements LotteryService {
	@Autowired
	LotteryMapper lotteryMapper;
	@Autowired
	PrizeMapper prizeMapper;
	@Autowired
	StaffMapper staffMapper;
	@Autowired
	StaffDicMapper staffDicMapper;

	/**
	 * 批量上传文件
	 *
	 * @param staff
	 * @return
	 */
	@Override
	public Result batchUploadExcel(List<Staff> staff, Integer lotteryId) {
		if (null == lotteryId) {
			return Result.getFalse();
		}
		Result result = null;
		List<StaffDic> staffDicList = new ArrayList<>();
		// 默认未中奖
		for (Staff staffMap : staff) {
			staffMap.setLotteryId(lotteryId);
			staffMap.setState(0);
			//staff数据整理完成后，准备staff_dic数据
			StaffDic staffDic = new StaffDic();
			String staffName = staffMap.getStaffName();
			if (staffName.length() < 2) {
				//确实没见过只有姓没有名的
				return Result.getFalse();
			}
			if (staffName.length() == 2) {
				//两个字名字的，中间给哦她空起
				staffDic.setStaffName1(staffName.substring(0, 1));
				staffDic.setStaffName2(" ");
				staffDic.setStaffName3(staffName.substring(1, 2));
			}
			if (staffName.length() > 2) {
				//大于两个字名字的，第一二位取一个，最后一位全取
				// eg：巴斯光年->巴 斯 光年，泰罗奥特曼->泰 罗，奥特曼->奥 特 曼
				staffDic.setStaffName1(staffName.substring(0, 1));
				staffDic.setStaffName2(staffName.substring(1, 2));
				staffDic.setStaffName3(staffName.substring(2));
			}
			String staffCode = staffMap.getStaffCode();
			if (staffCode.length() < 3) {
				//工号太短就用一个表示
				staffDic.setStaffCode1(staffCode);
				staffDic.setStaffCode2(" ");
				staffDic.setStaffCode3(" ");
			}
			if (staffCode.length() > 2) {
				//大于等于3就假三等分，前面两个占两份，剩下的都在第三个
				int num = staffCode.length() / 3;
				staffDic.setStaffCode1(staffCode.substring(0, num));
				staffDic.setStaffCode2(staffCode.substring(num, num * 2));
				staffDic.setStaffCode3(staffCode.substring(num * 2));
			}
			staffDic.setLotteryId(lotteryId);
			staffDic.setState(0);
			staffDicList.add(staffDic);
		}
		//先删除再导入
		Staff staff1 = new Staff();
		staff1.setLotteryId(lotteryId);
		staffMapper.delete(staff1);
		StaffDic staffDic = new StaffDic();
		staffDic.setLotteryId(lotteryId);
		staffDicMapper.delete(staffDic);
		for (int i = 0; i < staffDicList.size(); i++) {
			//因为要通过staff.staffId链接两个表的数据，又不想写那个批量insert的sql，就你一条我一条
			staffMapper.insert(staff.get(i));
			staffDicList.get(i).setStaffId(staff.get(i).getStaffId());
			staffDicMapper.insert(staffDicList.get(i));
		}
		result = Result.getSuccess("成功导入" + staff.size() + "条");
		return result;
	}

	/**
	 * 创建奖品
	 *
	 * @param formReq
	 * @return
	 */
	@Override
	public Result createPrize(FormReq formReq) {
		Lottery lottery = new Lottery();
		//copy lottery数据
		BeanUtils.copyProperties(formReq, lottery);
		lottery.setState(1);
		// md5加密
		lottery.setPassword(MD5Util.getMD5String(lottery.getPassword()));
		lotteryMapper.insertLottery(lottery);
		// 获取奖品list
		List<Prize> prizes = formReq.getPrizes();
		for (Prize prize : prizes) {
			prize.setNum(prize.getCount());
			prize.setLotteryId(lottery.getLotteryId());
		}
		prizeMapper.insertPrize(prizes);
		return Result.getSuccess(lottery.getLotteryId());
	}

	/**
	 * 更新奖品
	 *
	 * @param formReq
	 * @return
	 */
	@Override
	public Result updatePrize(FormReq formReq) {
		if (null == formReq.getLotteryId()) {
			return Result.getFalse();
		}
		Lottery lottery = new Lottery();
		//copy lottery数据
		BeanUtils.copyProperties(formReq, lottery);
		Integer lotteryId = lottery.getLotteryId();
		Lottery lotteryData = lotteryMapper.selectByPrimaryKey(lotteryId);
		Integer state = lotteryData.getState();
		// 判断state如果state为1则可以修改 为0 return
		if (state == 0) {
			return Result.getFalse("已抽奖：无法修改！！");
		}
		lottery.setPassword(MD5Util.getMD5String(lottery.getPassword()));

		// 更新抽奖表
		lotteryMapper.updateByPrimaryKeySelective(lottery);
		List<Prize> prizes = formReq.getPrizes();
		for (Prize prizeMap : prizes) {
			prizeMap.setNum(prizeMap.getCount());
			prizeMap.setLotteryId(lotteryId);
		}
		// 先删除后insert达到更新奖品的目的
		prizeMapper.deleteBatch(lotteryId);
		prizeMapper.insertPrize(prizes);
		return Result.getSuccess(lotteryId);
	}

	/**
	 * 通过lotteryId获取抽奖抽奖信息
	 *
	 * @param lotteryId
	 * @return
	 */
	@Override
	public Result getPrizeList(Integer lotteryId) {
		if (null == lotteryId) {
			return Result.getFalse();
		}
		Prize prize = new Prize();
		//设置条件
		prize.setLotteryId(lotteryId);
		List<Prize> prizeList = prizeMapper.select(prize);
		List<PrizeVO> prizeVOList = new ArrayList<>();
		for (Prize prize1 : prizeList) {
			PrizeVO prizeVO = new PrizeVO();
			BeanUtils.copyProperties(prize1, prizeVO);
			prizeVO.setNum((prize1.getCount() - prize1.getNum()) + "/" + prize1.getCount());
			if (prize1.getNum() < 1) {
				prizeVO.setDisable(true);
			} else {
				prizeVO.setDisable(false);
			}
			prizeVOList.add(prizeVO);
		}
		//获取抽奖基本信息
		Lottery lottery = lotteryMapper.selectByPrimaryKey(lotteryId);
		if (null == lottery) {
			return Result.getFalse();
		}
		//设置奖品信息
		LotteryVO lotteryVO = new LotteryVO();
		lotteryVO.setState(lottery.getState());
		lotteryVO.setLotteryId(lottery.getLotteryId());
		lotteryVO.setLotteryName(lottery.getLotteryName());
		lotteryVO.setPrizeList(prizeVOList);
		return Result.getSuccess(lotteryVO);
	}

	/**
	 * 通过lotteryId获取符合要求的staff
	 * 这坨代码有问题，for循环那里，但是不影响使用，先不求管
	 * @param lotteryReq
	 * @return
	 */
	@Override
	public Result getStaffList(LotteryReq lotteryReq) {
		if (null == lotteryReq.getLotteryId()) {
			return Result.getFalse("输入有误");
		}
		//设置条件
		Staff staff = new Staff();
		staff.setLotteryId(lotteryReq.getLotteryId());
		staff.setState(0);
		//打乱顺序
		List<Staff> staffList = staffMapper.select(staff);
		if (staffList.size() < 10 && staffList.size() > 0) {
			//让滚动看起来逼真一点
			for (int i = 0; i < 3; i++) {
				Collections.shuffle(staffList);
				staffList.addAll(staffList);
			}
		}
		if (staffList.size() > 0) {
			return Result.getSuccess(staffList);
		}
		return Result.getFalse();
	}

	/**
	 * 抽奖完成，写表
	 *
	 * @param lotteryReq
	 * @return
	 */
	@Override
	public Result setLottery(LotteryReq lotteryReq) {
		Staff staff = new Staff();
		if (null == lotteryReq.getLotteryId() || null == lotteryReq.getPrizeId() || !StringUtils.hasText(lotteryReq.getStaffName())) {
			return Result.getFalse("输入有误");
		} else {
			try {
				//拆分staffName，通过staffId和lotteryId查询staff，为防止格式报错，放在tryCatch里面
				String staffNameStr[] = lotteryReq.getStaffName().split("\n");
				String staffCode = staffNameStr[0];
				staff.setStaffCode(staffCode);
				staff.setLotteryId(lotteryReq.getLotteryId());
				staff.setState(0);
				staff = staffMapper.selectOne(staff);
				if (null == staff) {
					return Result.getFalse("输入有误");
				}
			} catch (Exception e) {
			}
		}
		//通过入参获取staff，prize，lottery信息
		Prize prize = prizeMapper.selectByPrimaryKey(lotteryReq.getPrizeId());
		Lottery lottery = lotteryMapper.selectByPrimaryKey(lotteryReq.getLotteryId());
		if (null == staff || null == prize || null == lottery) {
			return Result.getFalse("输入有误");
		}
		//更新staff
		staff.setState(1);
		staffMapper.updateByPrimaryKey(staff);
		StaffDic staffDic=new StaffDic();
		staffDic.setStaffId(staff.getStaffId());
		staffDic.setStaffId(1);
		staffDicMapper.updateByPrimaryKeySelective(staffDic);
		//更新prize
		if (null == prize.getStaffName()) {
			prize.setStaffName(staff.getStaffCode() + " " + staff.getStaffName());
		} else {
			prize.setStaffName(staff.getStaffCode() + " " + staff.getStaffName() + " , " + prize.getStaffName());
		}
		prize.setNum(prize.getNum() - 1);
		prizeMapper.updateByPrimaryKey(prize);
		//更新lottery
		lottery.setState(0);
		lotteryMapper.updateByPrimaryKey(lottery);
		return Result.getSuccess();
	}

	/**
	 * 奖项还没抽完，每个员工都中奖了，刷新员工状态抽完剩下的奖
	 * @param lotteryReq
	 * @return
	 */
	@Override
	public Result updateStaff(LotteryReq lotteryReq) {
		if (null == lotteryReq.getLotteryId()) {
			return Result.getFalse("输入有误");
		}
		staffMapper.updateStaff(lotteryReq.getLotteryId());
		staffDicMapper.updateStaff(lotteryReq.getLotteryId());
		return Result.getSuccess();
	}

	/**
	 * 下载中奖员工数据，数据从prize.staffName取
	 * @param lotteryId
	 * @return
	 */
	@Override
	public Map<String, Object> downloadAward(Integer lotteryId) {
		Prize prize = new Prize();
		prize.setLotteryId(lotteryId);
		List<Prize> prizeList = prizeMapper.select(prize);
		List<StaffVO> staffVOList = new ArrayList<>();
		for (Prize prize1 : prizeList) {
			String staffName = prize1.getStaffName();
			//奖没抽完的判断
			if (StringUtils.hasText(staffName)) {
				String staffs[] = staffName.split(" , ");
				for (String staff : staffs) {
					StaffVO staffVO = new StaffVO();
					staffVO.setPrizeName(prize1.getPrizeName());
					staffVO.setPrizeLevel(prize1.getPrizeLevel());
					int index = staff.indexOf(" ");
					staffVO.setStaffCode(staff.substring(0, index));
					staffVO.setStaffName(staff.substring(index));
					staffVOList.add(staffVO);
				}
			}
		}
		Map<String, Object> map = new HashMap();
		map.put("prizeList", prizeList);
		map.put("staffVOList", staffVOList);
		return map;
	}

	/**
	 * 通过lotteryId获取奖品数据
	 * @param lotteryId
	 * @return
	 */
	@Override
	public Lottery getPrizeByLotteryId(Integer lotteryId) {
		Lottery lottery = lotteryMapper.selectByPrimaryKey(lotteryId);
		return lottery;
	}

	/**
	 * 这一坨代码看起焦人，绝对可以优化至少一半
	 *
	 * @param lotteryReq
	 * @return
	 */
	@Override
	public Result getLotteryDic(LotteryReq lotteryReq) {
		//入参判断
		if (null == lotteryReq.getPrizeId() || null == lotteryReq.getPrizeId()) {
			return Result.getFalse("请先选择奖项");
		}
		Prize prize = new Prize();
		prize.setPrizeId(lotteryReq.getPrizeId());
		prize = prizeMapper.selectOne(prize);
		if (prize.getNum() == 0) {
			//奖抽完了
			return Result.getFalseCode(99);
		}
		//通过lotteryId获取所有员工信息staff_dic表
		StaffDic staffDic = new StaffDic();
		staffDic.setLotteryId(lotteryReq.getLotteryId());
		List<StaffDic> staffDicList = staffDicMapper.select(staffDic);
		List<String> staffNameList1 = new ArrayList<>();
		List<String> staffNameList2 = new ArrayList<>();
		List<String> staffNameList3 = new ArrayList<>();
		List<String> staffCodeList1 = new ArrayList<>();
		List<String> staffCodeList2 = new ArrayList<>();
		List<String> staffCodeList3 = new ArrayList<>();
		for (StaffDic dic : staffDicList) {
			//把拆分好的名字、工号写入list
			staffCodeList1.add(dic.getStaffCode1());
			staffCodeList2.add(dic.getStaffCode2());
			staffCodeList3.add(dic.getStaffCode3());
			staffNameList1.add(dic.getStaffName1());
			staffNameList2.add(dic.getStaffName2());
			staffNameList3.add(dic.getStaffName3());
		}
		//打乱顺序
		Collections.shuffle(staffCodeList1);
		Collections.shuffle(staffCodeList2);
		Collections.shuffle(staffCodeList3);
		Collections.shuffle(staffNameList1);
		Collections.shuffle(staffNameList2);
		Collections.shuffle(staffNameList3);
		//丢进实体类准备返回
		StaffDicVO staffDicVO = new StaffDicVO();
		staffDicVO.setStaffCodeList1(staffCodeList1);
		staffDicVO.setStaffCodeList2(staffCodeList2);
		staffDicVO.setStaffCodeList3(staffCodeList3);
		staffDicVO.setStaffNameList1(staffNameList1);
		staffDicVO.setStaffNameList2(staffNameList2);
		staffDicVO.setStaffNameList3(staffNameList3);
		//获取未中奖员工数据
		StaffDic staffDic1 = new StaffDic();
		staffDic1.setState(0);
		staffDic1.setLotteryId(lotteryReq.getLotteryId());
		List<StaffDic> lotteryStaffs = staffDicMapper.select(staffDic1);
		if (lotteryStaffs.size() == 0) {
			//都中奖了
			return Result.getFalseCode(88);
		}
		//后台完成抽奖
		int index = (int) (Math.random() * (lotteryStaffs.size() - 1));
		StaffDic prizeStaff = lotteryStaffs.get(index);
		//将拆分好的员工信息写入list
		List<String> staffAwardName = new ArrayList<>();
		List<String> staffAwardCode = new ArrayList<>();
		staffAwardCode.add(prizeStaff.getStaffCode1());
		staffAwardCode.add(prizeStaff.getStaffCode2());
		staffAwardCode.add(prizeStaff.getStaffCode3());
		staffAwardName.add(prizeStaff.getStaffName1());
		staffAwardName.add(prizeStaff.getStaffName2());
		staffAwardName.add(prizeStaff.getStaffName3());
		//入实体类准备返回
		staffDicVO.setStaffAwardCode(staffAwardCode);
		staffDicVO.setStaffAwardName(staffAwardName);
		//通过对应的staff_id更新staff表
		Staff staff = new Staff();
		staff.setStaffId(prizeStaff.getStaffId());
		staff = staffMapper.selectOne(staff);
		staff.setState(1);
		staffMapper.updateByPrimaryKeySelective(staff);
		//更新staff_dic表
		prizeStaff.setState(1);
		staffDicMapper.updateByPrimaryKeySelective(prizeStaff);
		//prize表数据更新
		prize.setNum(prize.getNum() - 1);
		if (null == prize.getStaffName()) {
			prize.setStaffName(staff.getStaffCode() + " " + staff.getStaffName());
		} else {
			prize.setStaffName(staff.getStaffCode() + " " + staff.getStaffName() + " , " + prize.getStaffName());
		}
		prizeMapper.updateByPrimaryKeySelective(prize);
		return Result.getSuccess(staffDicVO);
	}
}

# lottery
# 端口8089
# pc端地址
- newLottery 创建新抽奖
- lottery?lotteryId=? 根据id访问抽奖
- newLottery?lotteryId 根据id修改抽奖
# 手机端地址
- lotteryPE?lotteryId=? 根据id访问抽奖
# 分支介绍
- main
  抽奖存在两个动画，通过右上角橙色小方块点击进行管理
  点击抽奖按钮将直接刷入中将数据
  单字跳动形式为单个名字跳动，5秒出第一个字，7秒出第二个字，9秒出第三个字，10秒停止
  最大抽奖时间为10s
- lotteryStyleI
  抽奖只有滚动抽奖一个动画
  抽奖滚动停止时再取前端展示数据入库
- lotteryStyleII
  抽奖存在两个动画，通过右上角橙色小方块点击进行管理
  点击抽奖按钮将直接刷入中将数据
  抽奖最大时间为20s
  单字跳动形式为整个名字一起跳，同时出现

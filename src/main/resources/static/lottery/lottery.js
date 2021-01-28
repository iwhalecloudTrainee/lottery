new Vue({
    el: "#lottery",
    data: {
        prizeReadStart: '恭喜',
        prizeReadEnd: '获得',
        isOld: false,
        staffName1: '好',
        staffName2: '运',
        staffName3: '来',
        staffCode1: '000',
        staffCode2: '000',
        staffCode3: '0000',
        staffCodeList1: [],
        staffCodeList2: [],
        staffCodeList3: [],
        staffNameList1: [],
        staffNameList2: [],
        staffNameList3: [],
        staffAwardName: [],
        staffAwardCode: [],
        mergeVisible:false,
        rollType:'单字跳动',
        size: 0,
        sizeCount: 0,
        staffEnd: false,
        more: false,
        sec: 30,
        isLottery: "开始抽奖",
        timeOutNu: "",
        countDownSetIntervalNub: '',
        rollStyle2: '',
        rollStyle2Count: '',
        prizeList: [],
        lotteryName: '',
        prizeId: '',
        prizeName:'',
        prizeLevel:'',
        autoplay: false,
        staffList: [{staffId: '0000', staffCode: "0000000000", staffName: "好运来"}],
        speed: 250,
        admin: false,
        password: '',
        lotteryId: 0,
        ip: '',
        isRolling: false,
        awardData: {
            password: '',
            lotteryId: 0,
            prizeId: 0,
            staffName: '',
        },
        bgmText:'开启',
        isBgmOn:true,
        isAwardOn:true,
        awardText:'开启',
        audio: new Audio("resources/bgmShorter.mp3"),
        synth: window.speechSynthesis,
        msg: new SpeechSynthesisUtterance(),
        allowClick: true,
        //太大一堆data了，一人加点，导致现在不知道那些有用哪些没用
    },

    //初始化
    created: function () {
        this.getLotteryId();
        this.getPrizeList();
        //通过入参判断是新版抽奖还是旧版
        if (this.isOld) {
            this.sec = 30;
        } else {
            this.sec = 10;
        }
    },

    methods: {

        //获取奖品列表(通用)
        getPrizeList() {
            const parma = {
                lotteryId: this.lotteryId
            };
            axios.post('lottery/getPrizeList', parma, null).then(res => {
                if (res.data.success) {
                    this.lotteryName = res.data.data.lotteryName;
                    this.prizeList = res.data.data.prizeList;
                }
            })
        },


        //抽奖
        lottery: function () {
            if (this.isOld) {
                //老版30秒时间
                this.sec = 30;
                this.lotteryOld();
            } else {
                if (!this.allowClick){
                    return ;
                }
                this.allowClick=false;
                //新版10秒
                this.sec = 10;
                if (this.isRolling == false) {
                    this.lotteryNew();
                } else {
                    //正在抽奖时点击（强行停止）
                    //把显示数据变为中奖数据
                    this.staffName1 = this.staffAwardName[0];
                    this.staffCode1 = this.staffAwardCode[0];
                    this.staffName2 = this.staffAwardName[1];
                    this.staffCode2 = this.staffAwardCode[1];
                    //弄一个back防止第二个字变成\u3000后读不出来
                    let staffName2Backup = this.staffAwardName[1];
                    this.staffName3 = this.staffAwardName[2];
                    this.staffCode3 = this.staffAwardCode[2];
                    if (this.staffName2 === ' ') {
                        this.staffName2 = '\u3000'
                    }
                    this.allowClick=true;
                    //重置定时器
                    clearInterval(this.rollStyle2);
                    clearInterval(this.rollStyle2Count);
                    //停止音乐
                    this.audio.pause();
                    this.audio.currentTime = 0;
                    //改变按钮值和状态
                    this.isLottery = "开始抽奖";
                    this.isRolling = false;
                    //读出中奖信息
                    const text = this.prizeReadStart + " " + this.staffName1 + staffName2Backup + this.staffName3 + " " + this.prizeReadEnd + " " + this.prizeLevel + " " + this.prizeName;
                    this.prizeRead(text);
                    //刷新获奖名单
                    this.getPrizeList();
                }
            }
        },

        //新版抽奖
        lotteryNew: function () {
            var that = this;
            const parma = {
                //其实可以直传一个prizeId，prize表中存在lotteryId，懒得改了
                lotteryId: this.lotteryId,
                prizeId: this.prizeId,
                password: this.password
            };
            //发送请求获取滚动数据和中将数据
            axios.post('lottery/getLotteryData', parma, null).then(res => {
                if (res.data.success) {
                    //状态修改、数据赋值
                    this.isRolling = true;
                    that.staffCodeList1 = res.data.data.staffCodeList1;
                    that.staffCodeList2 = res.data.data.staffCodeList2;
                    that.staffCodeList3 = res.data.data.staffCodeList3;
                    that.staffNameList1 = res.data.data.staffNameList1;
                    that.staffNameList2 = res.data.data.staffNameList2;
                    that.staffNameList3 = res.data.data.staffNameList3;
                    that.staffAwardName = res.data.data.staffAwardName;
                    that.staffAwardCode = res.data.data.staffAwardCode;
                    that.size = res.data.data.staffCodeList1.length;
                    that.sizeCount = res.data.data.staffCodeList1.length;
                    //播放音乐，改变按钮值
                    if (that.isBgmOn) {
                        that.audio.play();
                    }
                    that.isLottery = "抽奖中";
                    let count = 0;
                    //备用数据用于读
                    let staffName2Backup = '';
                    //倒计时每秒-1
                    that.rollStyle2Count = setInterval(function () {
                        that.sec = parseInt(that.sec) - 1;
                    }, 1000);
                    that.allowClick=true;
                    //100ms改变一次人员信息
                    that.rollStyle2 = setInterval(function () {
                        //刷新
                        that.staffName1 = that.staffNameList1[that.sizeCount - 1];
                        that.staffCode1 = that.staffCodeList1[that.sizeCount - 1];
                        //空白用3000表示，看起整齐
                        if (that.staffNameList2[that.sizeCount - 1] == ' ') {
                            that.staffName2 = '\u3000'
                        } else {
                            that.staffName2 = that.staffNameList2[that.sizeCount - 1];
                        }
                        that.staffCode3 = that.staffCodeList3[that.sizeCount - 1];
                        that.staffName3 = that.staffNameList3[that.sizeCount - 1];
                        that.sizeCount = that.sizeCount - 1;
                        if (that.sizeCount == 0) {
                            that.sizeCount = that.size;
                        }
                        if (count > 99) {
                            that.staffName1 = that.staffAwardName[0];
                            that.staffCode1 = that.staffAwardCode[0];
                            that.staffName2 = that.staffAwardName[1];
                            that.staffCode2 = that.staffAwardCode[1];
                            staffName2Backup = that.staffAwardName[1];
                            if (that.staffName2 === ' ') {
                                that.staffName2 = '\u3000'
                            }
                            that.staffName3 = that.staffAwardName[2];
                            that.staffCode3 = that.staffAwardCode[2];
                            //时间到，清空定时器
                            clearInterval(that.rollStyle2);
                            clearInterval(that.rollStyle2Count);
                            //音乐停止
                            that.audio.pause();
                            that.audio.currentTime = 0;
                            //按钮值和状态修改
                            that.isLottery = "开始抽奖";
                            that.isRolling = false;
                            //机器人朗读
                            const text = that.prizeReadStart + " " + that.staffName1 + staffName2Backup + that.staffName3 + " " + that.prizeReadEnd + " " + that.prizeLevel + " " + that.prizeName;
                            that.prizeRead(text);
                            //刷新获奖数据
                            that.getPrizeList();
                        }
                        count++;
                    }, 100);
                } else if (res.data.code == 99) {
                    this.prizeId = '';
                    this.getPrizeList();
                    alert("该奖项已抽完");
                } else if (res.data.code == 88) {
                    this.staffEnd = true
                } else {
                    alert(res.data.data);
                }
                that.allowClick=true;
                //以上else，遇到了一些奇奇怪怪的问题
            })
        },
        selectChange: function () {
            if (this.prizeId) {
                const parma = {
                    prizeId: this.prizeId
                }
                axios.post('lottery/getPrize', parma, null).then(res => {
                    if (res.data.success) {
                        this.prizeName = res.data.data.prizeName;
                        this.prizeLevel = res.data.data.prizeLevel;
                    }
                })
            }
        },
        //抽奖滚动（老版）
        lotteryOld: function () {
            var that = this;
            //先进行一系列是否合法的判断
            if (!this.prizeId) {
                return;
            }
            //初步判断是否抽完
            for (var index = 0; index < this.prizeList.length; index++) {
                if (this.prizeList[index].prizeId === this.prizeId) {
                    if (this.prizeList[index].disable == true) {
                        this.prizeId = '';
                        alert("该奖项已抽完");
                        return;
                    }
                }
            }
            //当前为未抽奖（我觉得用this.autoplay来判断好点）
            if (this.isLottery == "开始抽奖") {
                const parma = {
                    lotteryId: this.lotteryId,
                    prizeId: this.prizeId
                }
                //获取待滚动员工信息
                axios.post('lottery/getStaffList', parma, null).then(res => {
                        if (res.data.success) {
                            //请求成功
                            that.staffList = res.data.data;
                            if (that.isBgmOn) {
                                that.audio.play();
                            }
                            //开始倒计时
                            that.countDownSetIntervalNub = setInterval(this.countDown, 1000);
                            //30s时自动停止
                            that.timeOutNub = setTimeout(function stopLottery() {
                                that.autoplay = false;
                                that.isLottery = "开始抽奖";
                                that.audio.pause();
                                that.audio.currentTime = 0;
                                //清除定时器 设置初始倒计时间
                                clearInterval(that.countDownSetIntervalNub);
                                that.sec = '30';
                                let staffName = document.getElementsByClassName('is-active')[0].outerText;
                                that.awardData.staffName = staffName
                                const index = staffName.indexOf('\n');
                                staffName = staffName.substr(index)
                                const text = that.prizeReadStart + " " + staffName + " " + that.prizeReadEnd + " " + that.prizeLevel + " " + that.prizeName;
                                that.prizeRead(text);
                                that.setLottery();
                            }, 30000);
                            this.isLottery = "抽奖中";
                            that.autoplay = true;
                        } else if (res.data.code == 99) {
                            alert("你的输入有误")
                        } else if (res.data.code == 88) {
                            alert("该奖项已抽完")
                            this.prizeId = ''
                            this.getPrizeList();
                        } else {
                            this.staffEnd = true;
                        }
                    }
                )
            } else {
                //抽到一半强行停止
                that.audio.pause();
                that.audio.currentTime = 0;
                that.sec = '30';
                that.isLottery = "开始抽奖";
                that.autoplay = false;
                let staffName = document.getElementsByClassName('is-active')[0].outerText;
                that.awardData.staffName = staffName
                const index = staffName.indexOf('\n');
                staffName = staffName.substr(index)
                const text = that.prizeReadStart + " " + staffName + " " + that.prizeReadEnd + " " + that.prizeLevel + " " + that.prizeName;
                that.prizeRead(text);
                that.setLottery();
                clearTimeout(that.timeOutNub);
                clearInterval(that.countDownSetIntervalNub);
            }
        },

        //30s倒计时
        countDown() {
            this.sec = parseInt(this.sec) - 1;
        },

        //右上角的隐形button切换新老版本抽奖事件
        mergeDialog() {
            if (this.mergeVisible) {
                this.mergeVisible = false
            } else {
                this.mergeVisible = true
            }
        },
        typeChange() {
            if (this.isOld) {
                this.isOld = false;
                this.rollType = '单字跳动模式';
                this.sec = '15';

            } else {
                this.isOld = true;
                this.rollType = '转盘模式';
                this.sec = '30';
            }
        },
        bgm() {
            if (this.isBgmOn) {
                this.isBgmOn = false;
                this.bgmText = "关闭";
            } else {
                this.isBgmOn = true;
                this.bgmText = "开启";
            }
        },
        award() {
            if (this.isAwardOn) {
                this.isAwardOn = false;
                this.awardText = "关闭";
            } else {
                this.isAwardOn = true;
                this.awardText = "开启";
            }
        },
        //抽奖完成写表调用
        setLottery: function () {
            this.awardData.lotteryId = this.lotteryId;
            this.awardData.prizeId = this.prizeId;
            this.awardData.password = this.password;
            axios.post('lottery/setLottery', this.awardData, null).then(res => {
                if (res.data.success) {
                    this.getPrizeList();
                }
            })
        },

        //下载获奖数据
        downloadAward: function () {
            if (this.admin) {
                const url = 'lottery/downloadAward?lotteryId=' + this.lotteryId;
                window.open(
                    url,
                    '_self'
                );
            }
        },

        //密码验证
        login: function () {
            const param = {
                lotteryId: this.lotteryId,
                password: this.password,
            }
            axios.post('user/login', param, null).then(res => {
                if (res.data.success) {
                    this.admin = true;
                }
            })
        },
        //跳转编辑页面
        update: function () {
            const parma = 'newLottery?lotteryId=' + this.lotteryId
            self.location.href = parma;
        },
        //从url中获取lotteryId
        getLotteryId: function () {
            var lotteryId = this.getUrlRequestParam("lotteryId");
            if (!lotteryId) {
                alert("输入有误")
                window.close();
            } else {
                this.lotteryId = lotteryId;
            }
        },
        //获奖名单详情
        showMore: function () {
            if (this.more) {
                this.more = false;
            } else {
                this.more = true;
            }
        },
        //所有人都中奖了，刷表
        updateStaff: function () {
            const param = {
                lotteryId: this.lotteryId
            }
            axios.post('lottery/updateStaff', param, null).then(res => {
                if (!res.data.success) {
                    alert(res.data.msg);
                }
                this.staffEnd = false;
            })
        },

        //斑马线table
        tableRowClassName({row, rowIndex}) {
            const line = rowIndex % 2;
            if (line === 1) {
                return 'success-row';
            }
            return '';
        },

        //机器朗读（声音有点像
        prizeRead: function (text) {
            if (this.isAwardOn) {
                // new Audio("http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&text=" + encodeURI(text)).play();
                this.msg.text = text;     // 文字内容: 小朋友，你是否有很多问号
                this.msg.lang = "zh-CN";  // 使用的语言:中文
                this.msg.volume = 2;      // 声音音量：1
                this.msg.rate = 1;        // 语速：1
                this.msg.pitch = 1;       // 音高：1
                this.synth.speak(this.msg);    // 播放
            }
        },

        //获取链接中的值
        getUrlRequestParam: function (name) {
            var paramUrl = window.location.search.substr(1);
            this.ip = window.location.origin;
            var paramStrs = paramUrl.split('&');
            var params = {};
            for (var index = 0; index < paramStrs.length; index++) {
                params[paramStrs[index].split('=')[0]] = decodeURI(paramStrs[index].split('=')[1]);
            }
            return params[name];
        }
        ,
    },
    beforeDestroy() {
        clearInterval(this.countDownSetIntervalNub);
        clearInterval(this.timeOutNub);
        this.countDownSetIntervalNub = "";
        this.timeOutNub = "";
    }

})


new Vue({
    el: "#lottery",
    data: {
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
        size: 0,
        sizeCount: 0,
        staffEnd: false,
        allowUpdate: true,
        more: false,
        sec: 30,
        isLottery: "开始抽奖",
        timeOutNu: "",
        countDownSetIntervalNub: '',
        prizeList: [],
        lotteryName: '',
        prizeId: '',
        autoplay: false,
        staffList: [{staffId: '0000', staffCode: "0000000000", staffName: "好运来"}],
        speed: 250,
        admin: false,
        password: '',
        lotteryId: 0,
        ip: '',
        isRolling: false,
        awardData: {
            lotteryId: 0,
            prizeId: 0,
            staffName: '',
        },
        audio: new Audio("resources/bgmShorter.mp3"),//这里的路径写上mp3文件在项目中的绝对路径
    },
    created: function () {
        this.getLotteryId();
        this.getPrizeList();
        if (this.isOld) {
            this.getStaffData();
        }
    },

    methods: {

        //获取奖品列表
        getPrizeList() {
            const parma = {
                lotteryId: this.lotteryId
            };
            axios.post('lottery/getPrizeList', parma, null).then(res => {
                if (res.data.success) {
                    this.lotteryName = res.data.data.lotteryName;
                    this.prizeList = res.data.data.prizeList;
                    if (res.data.data.state == 1) {
                        this.allowUpdate = true
                    } else {
                        this.allowUpdate = false
                    }
                }
            })
        },


        //抽奖滚动
        //todo 需要通过isOld属性对两种抽奖方式进行拆分，isOld=true使用第一版滚动，isOld=false使用新版
        lottery: function () {
            if (this.isOld) {
                this.lotteryOld();
            } else {
                if (this.isRolling==false) {
                    this.lotteryNew();
                }
            }
        },

        lotteryNew: function () {
            var that = this;
            const parma = {
                lotteryId: this.lotteryId,
                prizeId: this.prizeId
            };
            axios.post('lottery/getLotteryData', parma, null).then(res => {
                if (res.data.success) {
                    this.isRolling=true;
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
                    that.audio.play();
                    that.isLottery = "抽奖中";
                    let count = 0;
                    let roll1 = true;
                    let roll2 = true;
                    let roll3 = true;
                    var interval =setInterval(function () {
                        //todo 循环10s
                        if (roll1) {
                            that.staffName1 = that.staffNameList1[that.sizeCount - 1];
                            that.staffCode1 = that.staffCodeList1[that.sizeCount - 1];
                        }
                        if (roll2) {
                            if (that.staffNameList2[that.sizeCount - 1]==""){
                                that.staffName2='\u3000'
                            }else{
                            that.staffName2 = that.staffNameList2[that.sizeCount - 1];
                            }

                            that.staffCode2 = that.staffCodeList2[that.sizeCount - 1];
                        }
                        if (roll3) {
                            that.staffCode3 = that.staffCodeList3[that.sizeCount - 1];
                            that.staffName3 = that.staffNameList3[that.sizeCount - 1];
                        }
                        that.sizeCount = that.sizeCount - 1;
                        if (that.sizeCount == 0) {
                            that.sizeCount = that.size;
                        }
                        if (count > 70) {
                            roll1 = false;
                            that.staffName1 = that.staffAwardName[0];
                            that.staffCode1 = that.staffAwardCode[0];
                        }
                        if (count > 90) {
                            roll2 = false;
                            that.staffName2 = that.staffAwardName[1];
                            that.staffCode2 = that.staffAwardCode[1];
                        }
                        if (count > 120) {
                            roll3 = false;
                            that.staffName3 = that.staffAwardName[2];
                            that.staffCode3 = that.staffAwardCode[2];
                        }
                        if (that.staffName2 === ''){
                            that.staffName2 = '\u3000'
                        }
                        count++;
                        if (count==125){
                            //结束
                            clearInterval(interval);
                            that.audio.pause();
                            that.audio.currentTime = 0;
                            that.isLottery = "开始抽奖";
                            that.isRolling=false;
                            that.getPrizeList();
                        }
                    }, 100);
                } else if (res.data.code == 99) {
                    alert("该奖项已抽完");
                } else if (res.data.code == 88) {
                    this.staffEnd = true
                } else {
                    alert(res.data.data);
                }
            })
        },

        //抽奖滚动
        lotteryOld: function () {
            var that = this;
            if (!this.prizeId) {
                return;
            }
            for (var index = 0; index < this.prizeList.length; index++) {
                if (this.prizeList[index].prizeId === this.prizeId) {
                    if (this.prizeList[index].disable == true) {
                        this.prizeId = '';
                        alert("该奖项已抽完");
                        return;
                    }
                }
            }
            if (this.isLottery == "开始抽奖") {
                const parma = {
                    lotteryId: this.lotteryId
                }
                axios.post('lottery/getStaffList', parma, null).then(res => {
                    if (res.data.success) {
                        that.staffList = res.data.data;
                        that.audio.play();
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
                            that.awardData.staffName = document.getElementsByClassName('is-active')[0].outerText;
                            that.setLottery();
                        }, 30000);
                        this.isLottery = "停止抽奖";
                        that.autoplay = true;
                    } else {
                        this.staffEnd = true;
                    }
                })
            } else {
                that.audio.pause();
                that.audio.currentTime = 0;
                that.sec = '30';
                that.isLottery = "开始抽奖";
                that.autoplay = false;
                that.awardData.staffName = document.getElementsByClassName('is-active')[0].outerText;
                that.setLottery();
                clearTimeout(that.timeOutNub);
                clearInterval(that.countDownSetIntervalNub);
            }
        },
        //30s倒计时
        countDown() {
            this.sec = parseInt(this.sec) - 1;
        },

        jump(){
            if (this.isOld){
                this.isOld=false;
            }else {
                this.isOld=true;
            }
        },
        //抽奖完成写表调用
        setLottery: function () {
            this.awardData.lotteryId = this.lotteryId;
            this.awardData.prizeId = this.prizeId;
            axios.post('lottery/setLottery', this.awardData, null).then(res => {
                if (res.data.success) {
                    this.getPrizeList();
                }
            })
        },
        downloadAward: function () {
            if (this.admin) {
                const url = 'lottery/downloadAward?lotteryId=' + this.lotteryId;
                window.open(
                    url,
                    '_self'
                );
            }
        },
        //获取员工列表
        getStaffData: function () {
            var that = this;
            const parma = {
                lotteryId: this.lotteryId
            }
            axios.post('lottery/getStaffList', parma, null).then(res => {
                if (res.data.success) {
                    that.staffList = res.data.data
                }
            })
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
            var type = this.getUrlRequestParam("type");
            if (!lotteryId) {
                alert("输入有误")
                window.close();
            } else {
                this.lotteryId = lotteryId;
                if (type == 1) {
                    this.isOld = true;
                } else {
                    this.isOld = false;
                }
            }
        },
        showMore: function () {
            if (this.more) {
                this.more = false;
            } else {
                this.more = true;
            }
        },
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
        tableRowClassName({row, rowIndex}) {
            const line = rowIndex % 2;
            if (line === 1) {
                return 'success-row';
            }
            return '';
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
        },
    },
    beforeDestroy() {
        clearInterval(this.countDownSetIntervalNub);
        clearInterval(this.timeOutNub);
        this.countDownSetIntervalNub = "";
        this.timeOutNub = "";
    }

})


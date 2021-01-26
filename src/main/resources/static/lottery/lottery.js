new Vue({
    el: "#lottery",
    data: {
        isOld:false,
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
        continue: false,
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
        staffList: [],
        speed: 250,
        admin: false,
        password: '',
        lotteryId: 0,
        ip: '',
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
        this.getStaffData();
        this.open();
    },

    methods: {

        open() {
            this.$message('抽奖自动停止时间为30秒');
        },
        getStaffDic: function () {
            const parma = {
                lotteryId: this.lotteryId,
                prizeId: this.prizeId
            };
            axios.post('lottery/getLotteryData', parma, null).then(res => {
                if (res.data.success) {
                    this.staffCodeList1 = res.data.data.staffCodeList1;
                    this.staffCodeList2 = res.data.data.staffCodeList2;
                    this.staffCodeList3 = res.data.data.staffCodeList3;
                    this.staffNameList1 = res.data.data.staffNameList1;
                    this.staffNameList2 = res.data.data.staffNameList2;
                    this.staffNameList3 = res.data.data.staffNameList3;
                    this.staffAwardName = res.data.data.staffAwardName;
                    this.staffAwardCode = res.data.data.staffAwardCode;
                    this.size = res.data.data.staffCodeList1.length;
                    this.sizeCount = res.data.data.staffCodeList1.length;
                    this.continue = true;
                } else if (res.data.code == 99) {
                    alert("该奖项已抽完");
                    this.prizeId = '';
                    this.continue = false;
                } else if (res.data.code == 88) {
                    this.staffEnd = true
                    this.continue = false;
                } else {
                    alert(res.data.data);
                    this.continue = false;
                }
            })
        },
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
            var that = this;
            if (!that.autoplay) {
                this.getStaffDic()
                //todo 这个判断方式要不得，要改
                if (this.continue) {
                    that.audio.play();
                    //开始倒计时
                    that.countDownSetIntervalNub = setInterval(this.countDown, 1000);
                    this.isLottery = "停止抽奖";
                    that.autoplay = true;
                    console.log(this.staffCodeList1)
                    that.countDownSetIntervalNub = setInterval(this.staffNameRoll, 150);
                }
            } else {
                // setTimeout(function (){
                this.staffName1 = this.staffAwardName[0];
                this.staffCode1 = this.staffAwardCode[0];
                // }, 1000);
                // setTimeout(function (){
                this.staffName2 = this.staffAwardName[1];
                this.staffCode2 = this.staffAwardCode[1];
                // }, 3000);

                // setTimeout(function stopLottery() {
                this.staffName3 = this.staffAwardName[2];
                this.staffCode3 = this.staffAwardCode[2];
                that.audio.pause();
                that.audio.currentTime = 0;
                that.sec = '30';
                that.isLottery = "开始抽奖";
                that.autoplay = false;
                // }, 5000);
                this.getPrizeList();
                clearTimeout(that.timeOutNub);
                clearInterval(that.countDownSetIntervalNub);
            }
        },

        //30s倒计时
        countDown() {
            this.sec = parseInt(this.sec) - 1;
        },
        staffNameRoll() {
            this.staffName1 = this.staffNameList1[this.sizeCount - 1];
            this.staffName2 = this.staffNameList2[this.sizeCount - 1];
            this.staffName3 = this.staffNameList3[this.sizeCount - 1];
            this.staffCode1 = this.staffCodeList1[this.sizeCount - 1];
            this.staffCode2 = this.staffCodeList2[this.sizeCount - 1];
            this.staffCode3 = this.staffCodeList3[this.sizeCount - 1];
            this.sizeCount = this.sizeCount - 1;
            if (this.sizeCount == 0) {
                this.sizeCount = this.size;
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
            const url = 'lottery/downloadAward?lotteryId=' + this.lotteryId;
            window.open(
                url,
                '_self'
            );
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
                if (type==1){
                    this.isOld=true;
                }else {
                    this.isOld=false;
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


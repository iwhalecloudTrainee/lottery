new Vue({
    el: "#lottery",
    data: {
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
        speed: 300,
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
        lottery: function () {
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
                clearTimeout(that.timeOutNub);
                clearInterval(that.countDownSetIntervalNub);
                that.sec = '30';
                that.isLottery = "开始抽奖";
                that.autoplay = false;
                that.awardData.staffName = document.getElementsByClassName('is-active')[0].outerText;
                that.setLottery();
            }
        },

        //30s倒计时
        countDown() {
            this.sec = parseInt(this.sec) - 1;
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
            if (!lotteryId) {
                alert("输入有误")
                window.close();
            } else {
                this.lotteryId = lotteryId;
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
})


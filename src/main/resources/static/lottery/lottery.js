new Vue({
    el: "#lottery",
    data: {
        awardList: [],
        prizeList: [],
        lotteryName: '',
        prizeId: '',
        autoplay: false,
        staffList: [],
        speed: 200,
        admin: false,
        password: '',
        lotteryId: 0,
        awardData: {
            lotteryId: 0,
            prizeId: 0,
            staffName: '',
        }
    },
    mounted() {
        this.getLotteryId();
        this.getPrizeList();
        this.getStaffData();
    },

    methods: {
        //获取奖品列表
        getPrizeList() {
            const parma = {
                lotteryId: this.lotteryId
            }
            axios.post('lottery/getPrizeList', parma, null).then(res => {
                if (res.data.success) {
                    this.lotteryName = res.data.data.lotteryName;
                    this.prizeList = res.data.data.prizeList;
                    console.log(this.prizeList)
                }
            })
        },


        //抽奖滚动
        lottery: function () {
            var that = this;
            if (!this.prizeId) {
                alert("请选择抽奖项目")
                return;
            } else {
                //判断是否正在滚动
                if (that.autoplay) {
                    that.autoplay = false;
                    console.log(this.staffList)
                    this.awardData.staffName = document.getElementsByClassName('is-active')[0].outerText
                    this.setLottery();
                } else {
                    that.autoplay = true;
                }
                // var audio = new Audio("resources/bgm.mp3");//这里的路径写上mp3文件在项目中的绝对路径
                // audio.play();//播放
                // this.autoplay = true;
                setTimeout(function () {
                    //15s后自动停止滚动
                    that.autoplay = false
                }, 15000);
                // setTimeout(function () {
                //     audio.pause();
                // }, 16000);
                //
                // //抽奖结束后，给this.awardData赋值,然后调用
                // this.setLottery();
            }
        },

        //抽奖完成写表调用
        setLottery: function () {
            this.awardData.lotteryId = this.lotteryId;
            this.awardData.prizeId = this.prizeId;
            axios.post('lottery/setLottery', this.awardData, null).then(res => {
                if (res.data.success) {
                    this.getPrizeList();
                    this.getStaffData();
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
        //获取链接中的值
        getUrlRequestParam: function (name) {
            var paramUrl = window.location.search.substr(1);
            var paramStrs = paramUrl.split('&');
            var params = {};
            for (var index = 0; index < paramStrs.length; index++) {
                params[paramStrs[index].split('=')[0]] = decodeURI(paramStrs[index].split('=')[1]);
            }
            return params[name];
        },
    },
})


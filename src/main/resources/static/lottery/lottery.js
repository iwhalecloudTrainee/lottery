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
        awardData:{
            lotteryId: 0,
            prizeId: 0,
            staffId: 0,
        }
    },
    mounted() {
        this.getPrizeList();
        this.getStaffData();
    },

    methods: {
        //获取奖品列表
        getPrizeList() {
            var lotteryId = this.getUrlRequestParam("lotteryId");
            if (!lotteryId) {
                return;
            }
            const parma = {
                lotteryId: lotteryId
            }
            axios.post('lottery/getPrizeList', parma, null).then(res => {
                if (res.data.success) {
                    this.prizeList = res.data.data.prizeList;
                    this.lotteryName = res.data.data.lotteryName;
                }
            })
        },


        //抽奖滚动
        lottery: function () {
            if (!this.prizeId) {
                alert("请选择抽奖项目")
                return;
            } else {
                var audio = new Audio("resources/bgm.mp3");//这里的路径写上mp3文件在项目中的绝对路径
                audio.play();//播放
                this.autoplay = true;
                setTimeout(function () {
                    this.autoplay = false
                }, 15000);
                setTimeout(function () {
                    audio.pause();
                }, 16000);

                //抽奖结束后，给this.awardData赋值,然后调用
                this.setLottery();
            }
        },

        //抽奖完成写表调用
        setLottery:function (){
            var lotteryId = this.getUrlRequestParam("lotteryId");
            if (!lotteryId) {
                return;
            }
            axios.post('award/awardInsert', this.awardData, null).then(res => {
                if (res.data.success) {
                    this.getPrizeList();
                }
            })
        },

        //获取员工列表
        getStaffData: function () {
            var that = this;
            var lotteryId = this.getUrlRequestParam("lotteryId");
            if (!lotteryId) {
                return;
            }
            const parma = {
                lotteryId: lotteryId
            }
            axios.post('lottery/getStaffList', parma, null).then(res => {
                if (res.data.success) {
                    that.staffList = res.data.data
                }
            })
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


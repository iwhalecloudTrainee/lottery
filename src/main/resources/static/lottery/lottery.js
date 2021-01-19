new Vue({
    el: "#lottery",
    data: {
        awardList: [],
        prizeList: [],
        lotteryName: '',
        prizeId: '',
        autoplay: false,
        staffList: [
            {
                staffId: 1,
                staffName: '快来抽奖',
            }
        ],
        speed: 200
    },
    mounted() {
        this.getAwardList();
        this.getPrizeList();
        this.getStaffData();
    },

    methods: {
        getAwardList() {
            var lotteryId = this.getUrlRequestParam("lotteryId");
            if (!lotteryId) {
                return;
            }
            const parma = {
                lotteryId: lotteryId
            }
            axios.post('award/getAward', parma, null).then(res => {
                if (res.data.success) {
                    this.awardList = res.data.data;
                }
            })
        },
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
        getUrlRequestParam: function (name) {
            var paramUrl = window.location.search.substr(1);
            var paramStrs = paramUrl.split('&');
            var params = {};
            for (var index = 0; index < paramStrs.length; index++) {
                params[paramStrs[index].split('=')[0]] = decodeURI(paramStrs[index].split('=')[1]);
            }
            return params[name];
        },
        lottery: function () {
            var that=this
            if (!that.prizeId) {
                alert("请选择抽奖项目")
                return;
            } else {
                var lotteryId = this.getUrlRequestParam("lotteryId");
                if (!lotteryId) {
                    return;
                }
                const parma={
                    lotteryId: lotteryId,
                    prizeId: that.prizeId,
                };
                axios.post('lottery/getLottery', parma, null).then(res => {
                    if (res.data.success) {
                        that.staffList = res.data.data
                    }
                })
                var that = this;
                var audio = new Audio("resources/bgm.mp3");//这里的路径写上mp3文件在项目中的绝对路径
                audio.play();//播放
                console.log(that.prizeId)
                this.autoplay = true;
                setTimeout(function () {
                    that.autoplay = false
                }, 15000);
                setTimeout(function () {
                    audio.pause();
                }, 16000);
            }
        },
        getStaffData: function () {
            var that = this;
            var lotteryId = this.getUrlRequestParam("lotteryId");
            if (!lotteryId) {
                return;
            }
            const parma = {
                lotteryId: lotteryId
            }
            axios.post('lottery/getLottery', parma, null).then(res => {
                console.log(res)
                if (res.data.success) {
                    that.staffList = res.data.data
                }
            })
        },
        endLottery: function () {
            this.autoplay = false;

        }
    },
})


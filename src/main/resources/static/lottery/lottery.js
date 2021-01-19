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
                staffName: 'asd',
            }, {
                staffId: 2,
                staffName: 'assd',
            }, {
                staffId: 3,
                staffName: 'asad',
            }, {
                staffId: 4,
                staffName: 'awsd',
            }, {
                staffId: 5,
                staffName: 'asdq',
            }, {
                staffId: 6,
                staffName: 'asfd',
            }, {
                staffId: 7,
                staffName: 'asqd',
            }, {
                staffId: 8,
                staffName: 'asqwfd',
            }, {
                staffId: 9,
                staffName: 'adssdqqqqqqqqqqqqqqq',
            }, {
                staffId: 10,
                staffName: 'asdsd',
            }, {
                staffId: 11,
                staffName: 'assdgd',
            },
        ]
    },
    mounted() {
        this.getAwardList();
        this.getPrizeList();
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
            var that=this;
            if (!that.prizeId) {
                alert("请选择抽奖项目")
                return;
            } else {
                var lotteryId = this.getUrlRequestParam("lotteryId");
                if (!lotteryId) {
                    return;
                }
                const parma={
                    prizeId:that.prizeId,
                    lotteryId: lotteryId
                }
                axios.post('lottery/getLottery', parma, null).then(res => {
                    if (res.data.success) {

                    }
                })
                // var audio = new Audio("resources/bgm.mp3");//这里的路径写上mp3文件在项目中的绝对路径
                // audio.play();//播放
                //
                // console.log(that.prizeId)
                // this.autoplay = true;
                // setTimeout(function () {
                //     that.autoplay=false
                //     audio.pause();
                // }, 2250);
            }
        },
        endLottery: function () {
            this.autoplay = false;

        }
    },
})


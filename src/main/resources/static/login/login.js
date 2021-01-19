// import {urlParamsToJSON} from '../utils/utils'
new Vue({
    el: '#app',
    data() {
        return {
            password: "",
            lotterId: "",
            loginInfo: {},
        };
    },
    created: function () {
        let parames = window.location.search;
        this.loginInfo = this.urlParamsToJSON(parames);
    },
    methods: {
        onSubmit(values) {
            console.log("lotteryId:",this.loginInfo[0].value);
            console.log("password:",this.password);
            this.lotteryId = this.loginInfo[0].value;
            const data = {
                    lotteryId: this.lotteryId,  //url里面的参数
                    password: this.password,    //输入框里面的密码
                };
            this
            $.ajax({
                contentType:"application/json",
                url: 'http://localhost:8089/user/login',
                type: 'post',
                data: JSON.stringify(data),
                success: function (data) {
                    console.log("data", data)
                },
                error: function (xhr, errorType, error) {
                    console.log("xhr", xhr);
                    console.log("errorType", errorType);
                    console.log("error", error);
                }
            });
        },
        urlParamsToJSON(urlParams) {
            let parames = urlParams.substring(1).split("&");
            let arr = [];
            for (let i = 0; i < parames.length; i++) {
                let person = {
                    key: parames[i].split("=")[0],
                    value: parames[i].split("=")[1],
                };
                arr.push(person);
            }
            return arr;
        }
    },
});

Vue.use(vant.Lazyload);
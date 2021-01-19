// import {urlParamsToJSON} from '../utils/utils'
new Vue({
    el: '#app',
    data() {
        return {
            password: "",
            loginInfo: {},
        };
    },
    created: function () {
        let parames = window.location.search;
        this.loginInfo = this.urlParamsToJSON(parames);
        console.log(this.loginInfo[0].value);
    },
    methods: {
        onSubmit(values) {
            console.log(this.loginInfo);
            $.ajax({
                url: '',
                type: 'post',
                data: {
                    lotteryId: this.loginInfo[0].value,
                    password: this.password,
                },
                success: function (data) {
                    console.log("data", data)
                },
                error: function (xhr, errorType, error) {
                    console.log("xhr", xhr);
                    console.log("errorType", errorType);
                    console.log("error", error);
                }
            });
            console.log('√‹¬Î', values);
            console.log(this.password)
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
// 在 #app 标签下渲染一个按钮组件
new Vue({
    el: '#app',
    data() {
        return {
            lotteryId: '',
            password: '',
        };
    },
    methods: {
        onSubmit(values) {
            $.ajax({
                url: '',
                type: 'post',
                data: {
                    lotteryId: this.lotteryId,
                    password: this.password,
                },
                success: function (data) {
                    console.log("data",data)
                },
                error: function (xhr, errorType, error) {
                    console.log("xhr", xhr);
                    console.log("errorType", errorType);
                    console.log("error", error);
                }
            })
            console.log('密码', values);
            console.log(this.password)
        },
    },
});

// 调用函数组件，弹出一个 Toast
vant.Toast('提示');

// 通过 CDN 引入时不会自动注册 Lazyload 组件
// 可以通过下面的方式手动注册
Vue.use(vant.Lazyload);
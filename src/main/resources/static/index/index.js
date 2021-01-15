
var app =new Vue({
    // 绑定html id
    el: '#app',
    // 全局变量存储
    data: {},
    // 初始化方法
    created: function () {
        this.init();
    },
    // function都写这里
    method:{
        init:function (){
            var params={};
            // 封装ajx方法 调用后端
            // 此处并没有进行测试到时候报错进去打log查看
            lotteryServ.execute("user", "login", params, function (data) {

            })
        },
    },
 })
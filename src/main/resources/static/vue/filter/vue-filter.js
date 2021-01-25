// 百分比过滤
Vue.filter('leaderRateFilter', function (val) {
    if (val == undefined || val == '') {
        return '--';
    }
    // 占比保留一位小数
    var numValue = Number(val).toFixed(2);
    return numValue + '%';
})
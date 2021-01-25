var npsCommon = function () {
    return {
        post: function (url, param, callback) {
            if (typeof (contextPath) != 'undefined') {
                url = contextPath + url;
            }
            Vue.http.post(url, JSON.stringify(param), {emulateJSON: true}).then(function (response) {
                    var data = response['data'];
                    if (callback) {
                        callback(data);
                    }
                },
                function (res) {
                    if (callback) {
                        callback({code: '999', msg: '请求失败'});
                    }
                }
            );
        },
        get: function (url, param, callback) {
            Vue.http.get(contextPath + url, param).then(function (response) {
                    var data = response['data'];
                    if (callback) {
                        callback(data);
                    }
                },
                function (res) {
                    if (callback) {
                        callback({code: '999', msg: '请求失败'});
                    }
                }
            );
        },
        // 获取get请求的url参数
        getUrlParam: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        },
        msgAlert: function (me, message) {
            if (message == undefined || message == '') {
                message = '处理成功';
            }
            me.$message({
                type: 'success',
                message: message
            });
        },
        alert: function (me, message) {
            if (message == undefined || message == '') {
                message = '处理成功';
            }
            me.$alert(message, {center: true});
        },
        // 校验文本是否为空
        validTxtIsNull: function (txt) {
            if (txt == undefined || txt == '') {
                return true;
            }
            return false;
        },
        // 获得url中所带参数 返回json格式
        getUrlParams: function () {
            var urlParmas = {}
            var url = location.search;
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    var strArray = strs[i].split("=");
                    if (strArray.length <= 1) {
                        urlParmas[strArray[0]] = '';
                    } else {
                        urlParmas[strArray[0]] = strArray[1];
                    }
                }
            }
            return urlParmas;
        }
    }
}();

if (typeof (Vue) == "function") {
    Vue.prototype.npsCommon = npsCommon;
}
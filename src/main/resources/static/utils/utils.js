var requestMethod = function () {
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

    }();
}

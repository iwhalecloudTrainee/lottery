new Vue({
    el: '#newLottery',
    data: {
        dynamicValidateForm: {
            prizes: [
                {
                    prizeName: '',
                    count: 1,
                    prizeLevel: '',
                }
            ],
            password: '',
            lotteryName: '',
            lotteryId: {},
        },
        uploadLoading: false,
        updatePassword: '',
        isUpdate: true,
        uploadVisible: false,
        createSuccess: false,
        updateSuccess: false,
        url: '',
        lotteryId: 0,
        ip: '',
    },
    created: function () {
        this.initUpdate();
    },

    methods: {
        initUpdate: function () {
            var lotteryId = this.getUrlRequestParam("lotteryId");
            if (lotteryId) {
                this.updateSuccess = true;
            }
            var params = {lotteryId: lotteryId}
            var m = this;
            axios.post('lottery/getPrizeList', params, null).then(res => {
                if (res.data.success) {
                    m.isUpdate = false;
                    var result = res.data.data;
                    m.dynamicValidateForm.lotteryId = result.lotteryId;
                    m.dynamicValidateForm.lotteryName = result.lotteryName;
                    m.dynamicValidateForm.prizes = result.prizeList;
                    for (var i = 0; i < result.prizeList.length; i++) {
                        result.prizeList[i].num = 0
                    }
                }
            })
        },
        submitForm: function () {
            const param = {
                lotteryName: this.dynamicValidateForm.lotteryName,
                password: this.dynamicValidateForm.password,
                prizes: this.dynamicValidateForm.prizes,
            }
            var url = 'lottery/createPrize';
            if (this.isUpdate == false) {
                this.uploadVisible = true;
                var lotteryId = this.getUrlRequestParam("lotteryId");
                url = 'lottery/updatePrize'
                param["lotteryId"] = lotteryId;
                param["password"] = this.updatePassword;
            }
            this.$refs['dynamicValidateForm'].validate((valid) => {
                if (valid) {
                    axios.post(url, param, null).then(res => {
                        var data = res.data;
                        const parma = 'lottery?lotteryId=' + lotteryId
                        if (data.success) {
                            this.lotteryId = {'lotteryId': data.data};
                            this.url = this.ip + "/lottery?lotteryId=" + data.data;
                            if (this.isUpdate == true) {
                                this.createSuccess = true;
                            }
                            // this.uploadVisible=true;
                        } else {
                            this.uploadVisible = false;
                            alert(data.data)
                            self.location.href = parma;

                        }
                    })
                }
            })
        },
        uploadChange: function (file, fileList) {
            if (file.status == "success") {
                this.uploadLoading=false
                if (file.response.success == true) {
                    this.createEnd();
                } else {
                    alert(file.response.data)
                }
            }else {
                this.uploadLoading=true
            }
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        removeDomain(item) {
            var index = this.dynamicValidateForm.prizes.indexOf(item)
            if (index !== -1) {
                this.dynamicValidateForm.prizes.splice(index, 1)
            }
        },
        addDomain() {
            this.dynamicValidateForm.prizes.push({
                prizeName: '',
                count: 1,
                prizeLevel: ''
            });
        },
        createEnd() {
            this.createSuccess = false;
            this.uploadVisible = false;
            const parma = 'lottery?lotteryId=' + this.lotteryId["lotteryId"]
            self.location.href = parma;
        },
        getUrlRequestParam: function (name) {
            this.ip = window.location.origin;
            var paramUrl = window.location.search.substr(1);
            var paramStrs = paramUrl.split('&');
            var params = {};
            for (var index = 0; index < paramStrs.length; index++) {
                params[paramStrs[index].split('=')[0]] = decodeURI(paramStrs[index].split('=')[1]);
            }
            return params[name];
        },
        checkPassword: function () {
            var lotteryId = this.getUrlRequestParam("lotteryId");
            const param = {
                lotteryId: lotteryId,
                password: this.updatePassword
            }
            axios.post("user/login", param, null).then(res => {
                if (res.data.success) {
                    alert("登录成功");
                    this.updateSuccess = false
                } else {
                    var message = res.data
                    alert(message.data)
                }
            })
        },
        loading() {
            if (this.loading = true) {
                this.loading = false;
            } else {
                this.loading = true
            }
        },
    },
})


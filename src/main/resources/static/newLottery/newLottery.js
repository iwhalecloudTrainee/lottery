new Vue({
    // 绑定html id
    el: '#newLottery',
    // 全局变量存储
    data: {
        dynamicValidateForm: {
            prizes: [
                {
                    prizeName: '',
                    num: 0
                }
            ],
            password: '',
            lotteryName: '',
            lotteryId: {},
            url: '',
        },
        uploadVisible: false,
        createSuccess: false
    },

    // function都写这里
    methods: {
        submitForm: function () {
            const param = {
                lotteryName: this.dynamicValidateForm.lotteryName,
                password: this.dynamicValidateForm.password,
                prize: this.dynamicValidateForm.prizes,
            }
            this.$refs['dynamicValidateForm'].validate((valid) => {
                if (valid) {
                    axios.post('lottery/createPrize', param, null).then(res => {
                        if (res.data.success) {
                            this.lotteryId = {'lotteryId': res.data.data};
                            this.url = "localhost:8089/lottery?lotteryId=" + res.data.data;
                            this.createSuccess = true;
                            // this.uploadVisible=true;
                        }
                    })
                }
            })
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
                num: 0,
            });
        },
        createEnd() {
            this.uploadVisible = false;
            self.location.href=this.url;
        }
    },
})


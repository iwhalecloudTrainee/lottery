new Vue({
    // 绑定html id
    el: '#newLottery',
    // 全局变量存储
    data: {
        dynamicValidateForm: {
            prizes: [
                {
                    prizeName: null,
                    num: 0
                }
            ],

            lotteryName: '',
        }
    },

    // function都写这里
    methods: {
        submitForm: function () {
            const param = {
                lotteryName: this.dynamicValidateForm.lotteryName,
                prize: this.dynamicValidateForm.prizes,
            }
            axios.post('lottery/createPrize',param,function (response){
                console.log("sad")
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
    },
})


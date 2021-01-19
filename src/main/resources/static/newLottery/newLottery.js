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
            url:'',
        },
        updatePassword:'',
        isUpdate:true,
        uploadVisible:false,
        createSuccess:false,
        updateSuccess:false
    },
    created:function (){
        this.initUpdate();
    },

    // function都写这里
    methods: {
        initUpdate:function (){
            this.isUpdate=false;
            var lotteryId=this.getUrlRequestParam("lotteryId");
            var params={lotteryId:lotteryId}
            console.log(lotteryId);
            var m = this;
            axios.post('lottery/getPrizeList', params, null).then(res => {
                if (res.data.success){
                    var result=res.data.data;
                    console.log(result);
                    m.dynamicValidateForm.lotteryId=result.lotteryId;
                    m.dynamicValidateForm.lotteryName=result.lotteryName;
                    m.dynamicValidateForm.prizes=result.prizeList;
                }
            })

        },
        updateForm:function (){


        },
        submitForm: function () {
            const param = {
                lotteryName: this.dynamicValidateForm.lotteryName,
                password: this.dynamicValidateForm.password,
                prize: this.dynamicValidateForm.prizes,
            }
            // 判断修改或新增
            var url='lottery/createPrize';
            if (this.isUpdate==false){
                var lotteryId=this.getUrlRequestParam("lotteryId");
                url='lottery/updatePrize'
                param["lotteryId"]=lotteryId;
                this.updateSuccess=true;
                param["password"]=this.updatePassword;
            }
            this.$refs['dynamicValidateForm'].validate((valid) => {
                if (valid) {
                    axios.post(url, param, null).then(res => {
                        if (res.data.success){
                            this.lotteryId={'lotteryId':res.data.data};
                            this.url="localhost:8089/lottery?lotteryId="+res.data.data;
                            if (this.isUpdate==true){
                                this.createSuccess=true;
                            }
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
            this.createSuccess=false;
            this.uploadVisible = false;
            const parma = 'lottery?lotteryId=' + this.lotteryId
            self.location.href=parma;
        },
        getUrlRequestParam:function (name) {
            var paramUrl = window.location.search.substr(1);
            var paramStrs = paramUrl.split('&');
            var params = {};
            for (var index = 0; index < paramStrs.length; index++) {
                params[paramStrs[index].split('=')[0]] = decodeURI(paramStrs[index].split('=')[1]);
            }
            console.log( params[name]);
            return params[name];
        }
    },
})


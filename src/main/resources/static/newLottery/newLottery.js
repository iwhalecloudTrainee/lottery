new Vue({
    // ��html id
    el: '#newLottery',
    // ȫ�ֱ����洢
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
        },
        updatePassword:'',
        isUpdate:true,
        uploadVisible:false,
        createSuccess:false,
        updateSuccess:false,
        url:'',
        lotteryId: 0,
    },
    created:function (){
        this.initUpdate();
    },

    // function��д����
    methods: {
        initUpdate:function (){
            var lotteryId=this.getUrlRequestParam("lotteryId");
            if (lotteryId){
                this.updateSuccess=true;
            }
            var params={lotteryId:lotteryId}
            var m = this;
            axios.post('lottery/getPrizeList', params, null).then(res => {
                console.log(res.data.data);
                if (res.data.success){
                    m.isUpdate=false;
                    var result=res.data.data;
                    m.dynamicValidateForm.lotteryId=result.lotteryId;
                    m.dynamicValidateForm.lotteryName=result.lotteryName;
                    m.dynamicValidateForm.prizes=result.prizeList;
                }
            })
        },
        submitForm: function () {
            const param = {
                lotteryName: this.dynamicValidateForm.lotteryName,
                password: this.dynamicValidateForm.password,
                prizes: this.dynamicValidateForm.prizes,
            }
            // �ж��޸Ļ�����
            var url='lottery/createPrize';
            if (this.isUpdate==false){
                this.uploadVisible = true;
                var lotteryId=this.getUrlRequestParam("lotteryId");
                url='lottery/updatePrize'
                param["lotteryId"]=lotteryId;
                param["password"]=this.updatePassword;
            }
            this.$refs['dynamicValidateForm'].validate((valid) => {
                if (valid) {
                    axios.post(url, param, null).then(res => {
                        console.log(res);
                        var data=res.data;
                        if (data.success){
                            this.lotteryId={'lotteryId':data.data};
                            this.url="localhost:8089/lottery?lotteryId="+data.data;
                            if (this.isUpdate==true){
                                this.createSuccess=true;
                            }
                            // this.uploadVisible=true;
                        }else {
                            this.uploadVisible = false;
                            alert(data.data)
                            const parma = 'lottery?lotteryId=' + lotteryId
                            self.location.href=parma;

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

            const parma = 'lottery?lotteryId=' + this.lotteryId["lotteryId"]
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
        },
        checkPassword:function (){
            var lotteryId=this.getUrlRequestParam("lotteryId");
            const param = {
                lotteryId: lotteryId,
                password: this.updatePassword
            }
            axios.post("user/login", param, null).then(res => {
                if (res.data.success){
                    alert("登录成功");
                    this.updateSuccess=false
                }else {
                    var message=res.data
                    alert(message.data)
                }
            })
        }
    },
})


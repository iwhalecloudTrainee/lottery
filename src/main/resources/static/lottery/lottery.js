var app = new Vue({
    el : "#root",
    data : {
        data : [

        ],
        title : true,
        setTime: null,
        res : "",
        nameList: [],
        numberList: []
    },
    methods : {
        begin(){
            var i = 0
            if(this.title){
                this.setTime = setInterval(()=>{
                    this.res = this.data[i]
                    i = i+1
                    if(i===this.data.length){
                        i=0
                    }

                },100)
            }else{
                clearInterval(this.setTime)
            }
            this.title = !this.title
        },
        begin: function(){
            var serverurl="http://localhost";
            axios.get(serverUrl)
                .then(function (res) {
                    var result=res.data;
                    this.vue.numberist = result;
                }).catch();
            var serverurl="http://";
            axios.get(serverUrl)
                .then(function (res1) {
                    var  resule=res1.data;
                    this.vue.nameList = result;
                }).catch();
        }
    },
})


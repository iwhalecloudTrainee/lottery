function getContextPath() {
	
    var path = document.URL;
    
    var index = path.split("/");
    
    var result = "/"+index[3];
    
    return result;
}
(function(window){
   var SERVER_ADDRESS=getContextPath();
    var lotteryServ = {
        execute: function(serv, method, param, call_back){

            $.extend(param,{"actionId":serv,"method":method,"source":"mobile"});
            $.ajax({
                type: 'GET',
                url: this.posturl(serv, method,param),
//                dataType: "jsonp",
                timeout:30000,
                success: function(data) {
                    if(data){
                        var result = eval("("+data+")");
                        call_back(result);
                    }
                },
                error: function(xhr, errorType, error) {
                }
            });
        },
        posturl:function(serv, method,param){
            var uri = SERVER_ADDRESS +serv+method +"?";
            for(key in param){
                uri = uri + key + "=" + param[key] + "&";
            }
            uri += "rmd="+new Date().getTime();
            
            return uri;
        }
    };

    window.lotteryServ = lotteryServ;
})(window);


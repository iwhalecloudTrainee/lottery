function getContextPath() {
	
    var path = document.URL;
    
    var index = path.split("/");
    
    var result = "/"+index[3];
    
    return result;
}
(function(window){
   var SERVER_ADDRESS=getContextPath();
    var serv = {
        execute: function(method, param, call_back){
            console.log(param)
            $.extend(param);
            $.ajax({
                type: 'POST',
                // url: this.posturl( method),
                url: method,
                params:param,
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
        posturl:function( method,param){
            var uri = SERVER_ADDRESS +method +"?";
            for(key in param){
                uri = uri + key + "=" + param[key] + "&";
            }
            return uri;
        }
    };

    window.serv = serv;
})(window);


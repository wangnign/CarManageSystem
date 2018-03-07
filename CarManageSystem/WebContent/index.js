

$(function(){
	//一开始，装载login.htm
	$.icekingutil.loadHtml('login.htm','mainContainer',login_init);
});
$(function(){
	//一开始，根据session中判断是否已经登入，去调用不同的页面
	$.post('cs',{cls:'TDengluController',mtd:'isLogged'},function(data){
		if(data.succ){
			$('#loginUser_name').text(data.loginUserName);
			$('#ul_topright').show();
			$('#ul_topleft').show();
			$('#ul_topleft3').show();
			if(data.type==99){
				$('#ul_topleft2').show();
			}
			$.icekingutil.loadHtml('routelist.htm','mainContainer',rou_init);
		}else{
			//未登入，跳转到登入页面
			$.icekingutil.loadHtml('login.htm','mainContainer',login_init);
		}
	});
});

var route = function(){
	//alert("route");
	$.icekingutil.loadHtml('routelist.htm','mainContainer',rou_init);

};
var routem = function(){
	//alert("routem");
	$.icekingutil.loadHtml('routemlist.htm','mainContainer',roum_init);

};
var bus = function(){
	//alert("bus");
	$.icekingutil.loadHtml('buslist.htm','mainContainer',bus_init);

};
var person= function(){
	$.icekingutil.loadHtml('personlist.htm','mainContainer',per_init);
};
var user= function(){
	$.icekingutil.loadHtml('userlist.htm','mainContainer');
};
var zhuce= function(){
	$.icekingutil.loadHtml('zhuce.htm','mainContainer');
};

var test1 = function(){
	alert('test1');
};
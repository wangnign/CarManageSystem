var fnlogin = function(){
	//登入之前，需要判断表单的有效性
	var obj = $.icekingutil.formDataObj('form_login');
	if(obj.username==''){
		alert('请填写用户名');
		return;
	}
	if(obj.password==''){
		alert('请填写密码');
		return;
	}
	
	//提交数据
	$.post('cs',obj,function(data){//发送请求接收数据
		if(data.succ){
			//alert('登入成功: '+data.loginUserName);
			$('#loginUser_name').text(data.loginUserName);
			$('#ul_topright').show();
			$('#ul_topleft').show();
			$('#ul_topleft3').show();
			if(data.type==99){
				$('#ul_topleft2').show();
			}
			$.icekingutil.loadHtml('routelist.htm','mainContainer',rou_init);
		}else{
			alert(data.stmt);
		}
	});
};

var fnLogout = function(){
	if(!confirm('确实要退出吗？'))
		return;
	$.post('cs',{cls:'TDengluController',mtd:'logout'},function(data){
		$('#ul_topright').hide();
		$('#ul_topleft').hide();
		$('#ul_topleft2').hide();
		$('#ul_topleft3').hide();
		$.icekingutil.loadHtml('login.htm','mainContainer',login_init);
	});
};

var login_init = function(){
	$.icekingutil.formItem('password','form_login').keydown(function(e){
		if(e.which == 13){
			fnlogin();
		}
	});
	
	$.icekingutil.formItem('username','form_login').focus();
};
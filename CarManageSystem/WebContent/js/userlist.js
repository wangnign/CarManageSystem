

var user_add = function(){
	var obj = $.icekingutil.formDataObj('form_user_add');
	if(obj.name==''){
		alert('请填写姓名');
		return;
	}
	if(obj.username==''){
		alert('请填写账号');
		return;
	}
	if(obj.password==''){
		alert('请填写密码');
		return;
	}
	
	if(!confirm('确实要提交数据吗？'))
		return;
	
	
	$.post('cs',obj,function(data){
		if(!data.succ){
			alert(data.stmt);
			return;
		}
		alert('注册成功');
		
		$.icekingutil.loadHtml('login.htm','mainContainer',login_init);
	});
	
};

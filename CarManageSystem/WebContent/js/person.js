//打开新增课程资料的dialog
var per_openDlgAdd = function(){
	$('#form_per_am')[0].reset();
	$.icekingutil.formItem('id','form_per_am').val(0);
	$('#dialog_peram').modal('show');
};

var per_openDlgModify = function(id){
	$.post('cs',{cls:'PersonController',mtd:'preModify',id:id},function(data){
		if(!data.succ){
			alert(data.stmt);
			return;
		}
		
		alert(data.stmt);
		
		$.icekingutil.formItem('id','form_per_am').val(data.cou.id);
		$.icekingutil.formItem('idcard','form_per_am').val(data.cou.idcard);
		$.icekingutil.formItem('name','form_per_am').val(data.cou.name);
		
		$.icekingutil.formItem('sex','form_per_am').val(data.cou.sex);
		$.icekingutil.formItem('xianluhao','form_per_am').val(data.cou.xianluhao);
		$.icekingutil.formItem('chufashijian','form_per_am').val(data.cou.chufashijian);
		$.icekingutil.formItem('chehao','form_per_am').val(data.cou.chehao);
		$.icekingutil.formItem('password','form_per_am').val(data.cou.password);
		$('#dialog_peram').modal('show');
	});
};


//新增一个车辆信息
var per_add = function(){
	var obj = $.icekingutil.formDataObj('form_per_am');
	if(obj.idcard==''){
		alert('请填写身份证');
		return;
	}
	if(obj.name==''){
		alert('请填写姓名');
		return;
	}
	if(obj.password==''){
		alert('请填写密码');
		return;
	}
	if(obj.sex==''){
		alert('请填写性别');
		return;
	}
	if(obj.xianluhao==''){
		alert('请填写线路号');
		return;
	}
	if(obj.chufashijian==''){
		alert('请填写出发时间');
		return;
	}
	if(obj.chehao==''){
		alert('请填写车号');
		return;
	}
	
	
	if(!confirm('确实要提交数据吗？'))
		return;
	
	$.post('cs',obj,function(data){
		alert(data.succ);
		if(!data.succ){
			alert(data.stmt);
			return;
		}
		$('#dialog_peram').modal('hide');
		
		per_init();
	});
	
};

//根据id删除乘客
var per_confirmDlgDelete=function(id,idcard){

	if(!confirm('确实要删除身份证为'+idcard+'的车辆吗？'))
		return;
	$.post('cs',{cls:'PersonController',mtd:'delete',id:id},function(data){
		if(!data.succ){
			alert(data.stmt);
			return;
		}
		per_init();

	});
	

};

var per_init  = function(){
	$.post('cs',{cls:'PersonController',mtd:'allPersonList'},function(data){
		if(!data.succ){
			alert('获取乘客：'+data.stmt);
			return;
		}
		
		$.icekingutil.loadHtmlFromTmpl('per_list_tmpl.htm',data.list,'tbody_perlist');
	});
};
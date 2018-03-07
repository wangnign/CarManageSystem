//打开新增课程资料的dialog
var roum_openDlgAdd = function(){
	$('#form_roum_am')[0].reset();
	$.icekingutil.formItem('id','form_roum_am').val(0);
	$('#dialog_roumam').modal('show');
};

var roum_openDlgModify = function(id){
	$.post('cs',{cls:'RoutemController',mtd:'preModify',id:id},function(data){
		if(!data.succ){
			alert(data.stmt);
			return;
		}
		
		alert(data.stmt);
		
		$.icekingutil.formItem('id','form_roum_am').val(data.cou.id);
		$.icekingutil.formItem('xianluhao','form_roum_am').val(data.cou.xianluhao);
		$.icekingutil.formItem('chufadi','form_roum_am').val(data.cou.chufadi);
		$.icekingutil.formItem('mudidi','form_roum_am').val(data.cou.mudidi);
		$.icekingutil.formItem('jiage','form_roum_am').val(data.cou.jiage);
		$.icekingutil.formItem('zongpiaoshu','form_roum_am').val(data.cou.zongpiaoshu);
		$.icekingutil.formItem('suoxushijian','form_roum_am').val(data.cou.suoxushijian);
		
		$('#dialog_roumam').modal('show');
	});
};


//新增一个课程信息
var roum_add = function(){
	var obj = $.icekingutil.formDataObj('form_roum_am');
	if(obj.xianluhao==''){
		alert('请填写线路号');
		return;
	}
	if(obj.chufadi==''){
		alert('请填写出发地');
		return;
	}
	if(obj.mudidi==''){
		alert('请填写目的地');
		return;
	}
	if(obj.jiage==''){
		alert('请填写价格');
		return;
	}
	if(obj.zongpiaoshu==''){
		alert('请填写总票数');
		return;
	}
	if(obj.suoxushijian==''){
		alert('请填写所需时间');
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
		$('#dialog_roumam').modal('hide');
		
		roum_init();
	});
	
};

//根据id删除课程
var roum_confirmDlgDelete=function(id,xianluhao){

	if(!confirm('确实要删除线路号为'+xianluhao+'的线路吗？'))
		return;
	$.post('cs',{cls:'RoutemController',mtd:'delete',id:id},function(data){
		if(!data.succ){
			alert(data.stmt);
			return;
		}
		roum_init();

	});
	

};

var roum_init  = function(){
	$.post('cs',{cls:'RoutemController',mtd:'allRoutemList'},function(data){
		if(!data.succ){
			alert('获取路线：'+data.stmt);
			return;
		}
		
		$.icekingutil.loadHtmlFromTmpl('roum_list_tmpl.htm',data.list,'tbody_roumlist');
	});
};
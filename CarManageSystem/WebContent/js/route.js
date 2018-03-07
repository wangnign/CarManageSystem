//打开新增线路的dialog
var rou_openDlgAdd = function(){
	$('#form_rou_am')[0].reset();
	$.icekingutil.formItem('id','form_rou_am').val(0);
	$('#dialog_rouam').modal('show');
};

var rou_openDlgModify = function(id){
	$.post('cs',{cls:'RouteController',mtd:'preModify',id:id},function(data){
		
		if(!data.succ){
			alert(data.stmt);
			return;
		}
		
		$.icekingutil.formItem('id','form_rou_am').val(data.cou.id);
		$.icekingutil.formItem('xianluhao','form_rou_am').val(data.cou.xianluhao);
		$.icekingutil.formItem('chufadi','form_rou_am').val(data.cou.chufadi);
		$.icekingutil.formItem('mudidi','form_rou_am').val(data.cou.mudidi);
		$.icekingutil.formItem('chufariqi','form_rou_am').val(data.cou.chufariqi);
		$.icekingutil.formItem('chufashijian','form_rou_am').val(data.cou.chufashijian);
		$.icekingutil.formItem('suoxushijian','form_rou_am').val(data.cou.suoxushijian);
		$.icekingutil.formItem('jiage','form_rou_am').val(data.cou.jiage);
		$.icekingutil.formItem('shengyupiaoshu','form_rou_am').val(data.cou.shengyupiaoshu);
		$.icekingutil.formItem('zongpiaoshu','form_rou_am').val(data.cou.zongpiaoshu);
		$('#dialog_rouam').modal('show');
	});
};

var rou_openDlgbuy = function(id){
	$('#dialog_buy').modal('show');	
};

var buy= function(){
	var obj = $.icekingutil.formDataObj('form_rou1_am');
	
	if(obj.idcard==''){
		alert('请填写身份证');
		return;
	}
	if(obj.name==''){
		alert('请填写姓名');
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
	if(obj.goumaishuliang==''){
		alert('请填写购买数量');
		return;
	}
	
	if(!confirm('确实要提交数据吗？'))
		return;
	
	$.post('cs',obj,function(data){
		if(data.flag==0){
			alert("数据库保存失败");
			return;
		}else if(data.flag==1){
			alert("购买数量必须大于等于0");
			return;
		}else if(data.flag==2){
			alert("购买失败！票剩余数量不足！");
			return;
		}else if(data.flag==3){
			alert("该用户不存在");
			return;
		}
		
		$('#dialog_buy').modal('hide');
	
		rou_init();
	});
	
	
};
//提交减少一张剩余票数
var rou_confirmDlgBuy=function(id,xianluhao){

	if(!confirm('确实要购买'+xianluhao+'的线路吗？'))
		return;
	$.post('cs',{cls:'RouteController',mtd:'buy',id:id},function(data){
		if(!data.succ){
		alert(data.stmt);
			return;
		}
		rou_searchRouByChufariqi(data.cou.chufariqi);
	});
};

//新增一个线路信息
var rou_add = function(){
	var obj = $.icekingutil.formDataObj('form_rou_am');
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
	if(obj.chufariqi==''){
		alert('请填写出发日期');
		return;
	}
	if(obj.chufashijian==''){
		alert('请填写出发时间');
		return;
	}
	if(obj.suoxushijian==''){
		alert('请填写所需时间');
		return;
	}
	if(obj.jiage==''){
		alert('请填写价格');
		return;
	}
	if(obj.shengyupiaoshu==''){
		alert('请填写剩余票数');
		return;
	}
	if(obj.zongpiaoshu==''){
		alert('请填写总票数');
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
		$('#dialog_rouam').modal('hide');

		//添加修改完以后  刷新一下两边的显示
		rou_searchRouByChufariqi(obj.chufariqi);//线路列表
	});
	
};

//根据id删除线路
var rou_confirmDlgDelete=function(id,xianluhao){

	if(!confirm('确实要删除线路号为'+xianluhao+'的线路吗？'))
		return;
	$.post('cs',{cls:'RouteController',mtd:'delete',id:id},function(data){
		if(!data.succ){
		alert(data.stmt);
			return;
		}
		rou_searchRouByChufariqi(data.cou.chufariqi);
	});
	

};



//根据班级获得线路信息列表
var rou_searchRouByChufariqi = function(chufariqi){
	$.post('cs',{cls:'RouteController',mtd:'findRouteByChufariqi',chufariqi:chufariqi},function(data){
		if(!data.succ){
			alert('获取线路列表失败：'+data.stmt);
			return;
		}
		
		$.icekingutil.loadHtmlFromTmpl('rou_list_tmpl.htm',data.list,'tbody_roulist');
		rou_init(); //日期列表
	});
};



var rou_init = function(){
	$.post('cs',{cls:'RouteController',mtd:'allChufariqiPiaoshuList'},function(data){
		if(!data.succ){
			alert('获取线路列表失败：'+data.stmt);
			return;
		}
		var html = '';
		for(var i=0;i<data.list.length;i++){
			var tyrs = data.list[i];
			html += '<div class="list-group">';
			html += '<a href="####" onclick="rou_searchRouByChufariqi(\''+tyrs.chufariqi+'\');" class="list-group-item"><span class="badge">'+tyrs.piaoshu+'张</span>'+tyrs.chufariqi+'日</a>';
			html += '</div>';
		}
		$('#div_rouchufariqi').html(html);
	});
};
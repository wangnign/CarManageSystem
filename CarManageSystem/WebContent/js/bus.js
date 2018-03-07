//打开新增车辆资料的dialog
var bus_openDlgAdd = function(){
	$('#form_bus_am')[0].reset();
	$.icekingutil.formItem('id','form_bus_am').val(0);
	$('#dialog_busam').modal('show');
};
/**
 *  @param 接收 html传过来的id
 */
var bus_openDlgModify = function(id){
	$.post('cs',{cls:'BusController',mtd:'preModify',id:id},function(data){
		//这里进control验证 id
		if(!data.succ){
			alert(data.stmt);
			return;
		}
		//这里设置修改页面的值 返回对象data controller返回的是bus 这里就是bus.
		$.icekingutil.formItem('id','form_bus_am').val(data.bus.id);
		$.icekingutil.formItem('goumainian','form_bus_am').val(data.bus.goumainian);
		$.icekingutil.formItem('chehao','form_bus_am').val(data.bus.chehao);
		$.icekingutil.formItem('chexing','form_bus_am').val(data.bus.chexing);
		$.icekingutil.formItem('zuidazaikeliang','form_bus_am').val(data.bus.zuidazaikeliang);
		
		$('#dialog_busam').modal('show');
	});
};

//根据车型获得汽车信息列表并刷新班级列表
var bus_searchBusByChexing_Bus_init = function(chexing,goumainian){//添加 一个参数
	$.post('cs',{cls:'BusController',mtd:'findBusByChexing',chexing:chexing,goumainian:goumainian},function(data){
		if(!data.succ){
			alert('获取车辆列表失败：'+data.stmt);
			return;
		}
		$.icekingutil.loadHtmlFromTmpl('bus_list_tmpl.htm',data.list,'tbody_buslist');
		
		bus_init();//刷新
	});
};

//新增一个汽车信息
var bus_add = function(){
	var obj = $.icekingutil.formDataObj('form_bus_am');
	if(obj.chehao==''){
		alert('请填写车号');
		return;
	}
	
	if(obj.chexing==''){
		alert('请填写车型');
		return;
	}
	
	if(obj.zuidazaikeliang==''){
		alert('请填写最大载客量');
		return;
	}
	
	if(!confirm('确实要提交数据吗？'))
		return;
	
	$.post('cs',obj,function(data){
		if(!data.succ){
			alert(data.stmt);
			return;
		}
		$('#dialog_busam').modal('hide');
		bus_searchBusByChexing_Bus_init(obj.chexing,obj.goumainian);//这里也是一样的
	});
	
	
};

//根据id删除车号
var bus_confirmDlgDelete=function(id,chehao){

	if(!confirm('确实要删除车号为'+chehao+'的车辆吗？')) {
		return;
	} else {
		//删除跟modify一样的 传id
		$.post('cs',{cls:'BusController',mtd:'delete',id:id},function(data){
			if(!data.succ){
				alert(data.stmt);
				return;
			}
			//alert(data.bus.chexing);
			bus_searchBusByChexing_Bus_init(data.bus.chexing,data.bus.goumainian); //这里就是删除以后执行一次查询 然后更新页面
		});
	}
	

};

var bus_init = function(){
	$.post('cs',{cls:'BusController',mtd:'allCheXingList'},function(data){
		if(!data.succ){
			alert('获取车辆列表失败：'+data.stmt);
			return;
		}
		
		var html = '';
		for(var i=0;i<data.list.length;i++){
			var cx = data.list[i];
			html += '<div class="list-group">';
			html += '<a href="####" class="list-group-item active">'+cx.goumainian+'年</a>';
			if(cx.bjs!=null && cx.bjs.length>0){
				for(var j=0;j<cx.bjs.length;j++){
					var clsm = cx.bjs[j];
					html += '<a href="####" onclick="bus_searchBusByChexing_Bus_init(\''+clsm.chexing+'\',\''+cx.goumainian+'\');" class="list-group-item"><span class="badge">'+clsm.shumu+'辆</span>'+clsm.chexing+'</a>';
				}
			}
			html += '</div>';
		}
		$('#div_chexing').html(html);
	});
};
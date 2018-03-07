var test1 = function(){
	$.post('cs',{cls:'My1Controller',mtd:'json'},function(data){
		alert(data.succ);
		alert(data.stmt);
		$('#h1').html(data.stmt);
	});
};

$(function(){
//	alert('cccc');
	
	//为表格的行添加单击事件
	/*$('#table1 tr').bind('click',function(){
		alert($(this).attr('dbid'));
	});
	
	//为表格的行添加 mouseover 事件
	$('#table1 tr').bind('mouseover',function(){
		$(this).addClass('warning');
	});
	$('#table1 tr').bind('mouseout',function(){
		$(this).removeClass('warning');
	});*/
	
	/*$('#table1 tr').each(function(index,element){
		var tr = $(element);
		tr.click(function(){
			alert(tr.attr('dbid'));
		});
		tr.mouseover(function(){
			tr.addClass('warning');
		});
		tr.mouseout(function(){
			tr.removeClass('warning');
		});
	});*/
	
	$.icekingutil.initTable('table1',{
		trHoverClass:'warning',
		trDblclick: function(){
			alert($(this).attr('dbid'));
		}
	});
	
	
	
});
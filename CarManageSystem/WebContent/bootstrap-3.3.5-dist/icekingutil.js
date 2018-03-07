/**
 * 将JQuery和Bootstrap中的一些常用功能打包成JQuery的静态属性好和方法
 * icekingutil. js
 * V1.0. 2016-4-5
 * 
 */
(function(){
	$.icekingutil = function(){};
	$.extend($.icekingutil,{
		//静态属性的定义
		author:'iceking',
		version:'V1.0',
		//web工程的名称，每个工程中需要改变
		//$.icekingutil.webRoot='你的工程名称'
		webRoot:''
	},{
		//静态的方法定义
		
		//获取当前脚本的，bootstrap所在的文件夹
		bootstrapPath: function(){
			return '/'+$.icekingutil.webRoot+'/bootstrap-3.3.5-dist/';
		},
		
		//======================================================================
		//=====================表单提交相关的数据转换=============================
		//======================================================================
		
		//获得表单的jquery对象
		getFormObj: function(formId){
			if(formId == null || formId == '')
				return null;
			var jqobj = null;
			if(formId.substr(0,1) == '#'){
				jqobj = $(formId);
			}else{
				jqobj = $('#'+formId);
			}
			if(jqobj == null || jqobj.length==0)
				return null;
			return jqobj;
		},
		
		//通过jquery，从form中获得表单的字符形式，序列化之后的
		formDataStr: function(formId){
			var jqform = $.icekingutil.getFormObj(formId);
			if(jqform == null)
				return '';
			return jqform.serialize();
		},
		
		//获得表单form的Object对象，便于转json字符的
		formDataObj: function(formId){
			var jqform = $.icekingutil.getFormObj(formId);
			if(jqform == null)
				return null;
			var array = jqform.serializeArray();
			if(array == null || array.length == 0)
				return null;
			var obj = new Object();
			for(var i=0;i<array.length;i++){
				obj[array[i].name]=array[i].value;
			}
			return obj;
		},
		
		//获得表单对象的json字符形式
		formDataJson: function(formId){
			var jsobj = $.icekingutil.formDataObj(formId);
			if(jsobj == null)
				return '';
			return JSON.stringify(jsobj);
		},
		
		//获得表单中的一个name
		formItem: function(name,formId){
			var selstr = '[name="'+name+'"]';
			if(formId!=null && formId!=''){
				if(formId.substr(0,1) == '#'){
					selstr = formId+' '+selstr;
				}else{
					selstr = '#'+formId+' '+selstr;
				}
			}
			var jqobj = $(selstr);
			if(jqobj == null || jqobj.length==0){
				return null;
			}
			return jqobj;
		},
		
		//获得表单值，根据name属性
		formItemVal: function(name,formId){
			var jqobj = $.icekingutil.formItem(name,formId);
			if(jqobj == null)
				return '';
			return jqobj.val();
		},
		
		//获得表单值，int型，根据name属性
		formItemInt: function(name,formId){
			var tmpval = $.icekingutil.formItemVal(name,formId);
			if(tmpval == '')
				return 0;
			return parseInt(tmpval);
		},
		
		//直接一个js的object变成json的字符形式
		objToJson:function(jsobj){
			if(jsobj == null)
				return '';
			return JSON.stringify(jsobj);
		},
		
		//判断某个jquery对象，是否有属性
		hasAttr: function(jqobj,attr){
			if(typeof(jqobj.attr(attr))=='undefined')
				return false;
			return true;
		},
		
		//======================================================================
		//=====================从外部页面装载html=============================
		//======================================================================
		
		//将html插入到body中
		//url: 获取html的地址
		//uuid，插入的内容，需要通过这个uuid判断是否已经插入过了
		//forcenew, 每次都把原来的给删除掉
		//func: 插入之后的回调函数
		//objs：传给url的参数
		appendHtmlToBody: function(url,uuid,forcenew,func,objs){
			//判断是否已经导入过了
			var uuidexist = false;
			if(uuid!=null && uuid!=''){
				var uuidObj = $.icekingutil.getFormObj(uuid);
				if(uuidObj!=null && uuidObj.length>0){
					uuidexist = true;
					if(forcenew!=null && forcenew){
						//强制把原来的元素给删除掉
						uuidObj.remove();
						uuidexist=false;
					}
				}
			}
			
			//如果已经存在了，则不需要重新append了
			if(uuidexist){
				if(func!=null && typeof(func)=='function'){
					func();
				}
				return;
			}
			
			//不存在，进行post方式的导入
			if(objs==null)
				objs = new Object();
			objs.rnd=Math.random();
			$.post(url,objs,function(data,status){
				if(status!='success'){
					return;
				}
				//加入到body中
				$(data).appendTo('body');
				if(func!=null && typeof(func)=='function'){
					func();
				}
			},'html');
			
		},
		
		//通过post方法从html中得到内容，并装载到指定的位置
		loadHtmlPost: function(url,destId,func,objs){
			//目标必须存在
			if(destId == null || destId == '')
				return;
			//获得目标的jquery object，不存在的话，直接返回
			var destJqObj = $.icekingutil.getFormObj(destId);
			if(destJqObj == null || destJqObj.length==0)
				return;
			//目标清空原来的html内容
			destJqObj.empty();
			//通过post方法调用
			if(objs==null)
				objs = new Object();
			objs.rnd=Math.random();
			$.post(url,objs,function(data,status){
				if(status!='success'){
					//错误时候，直接在目标中添加错误信息
					destJqObj.html('Load html from template error. url='+htmlUrl);
					return;
				}
				destJqObj.html(data);
				//加载内容html之后，也可以执行方法
				if(func!=null && typeof(func)=='function'){
					func();
				}
			},'html');
		},
		
		//从一个html或者jsp中load内容到指定的位置
		 loadHtml: function(htmlUrl,destId,func){
			//目标必须存在
			if(destId == null || destId == '')
				return;
			//获得目标的jquery object，不存在的话，直接返回
			var destJqObj = $.icekingutil.getFormObj(destId);
			if(destJqObj == null || destJqObj.length==0)
				return;
			//目标清空原来的html内容
			destJqObj.empty();
			//通过get方法发送请求
			$.get(htmlUrl+'?rnd='+Math.random(),function(data,status){
				if(status!='success'){
					//错误时候，直接在目标中添加错误信息
					destJqObj.html('Load html from template error. url='+htmlUrl);
					return;
				}
				destJqObj.html(data);
				//加载内容html之后，也可以执行方法
				if(func!=null && typeof(func)=='function'){
					func();
				}
			},'html');
		},
		
		//从一个html的模板中装载到页面
		loadHtmlFromTmpl: function(htmlUrl,jsonData,destId){
			//目标必须存在
			if(destId == null || destId == '')
				return;
			
			//获得目标的jquery object，不存在的话，直接返回
			var destJqObj = $.icekingutil.getFormObj(destId);
			if(destJqObj == null || destJqObj.length==0)
				return;
			//目标清空原来的html内容
			//destJqObj.html('');
			destJqObj.empty();
			//通过get方法发送请求
			$.get(htmlUrl+'?rnd='+Math.random(),function(data,status){
				if(status!='success'){
					//错误时候，直接在目标中添加错误信息
					destJqObj.html('Load html from template error. url='+htmlUrl);
					return;
				}
				//成功的话，生成模板，并添加到指定目标位置，模板的名字就是url名字
				$.template(htmlUrl,data);
				$.tmpl(htmlUrl,jsonData).appendTo('#'+destJqObj.eq(0).attr('id'));
			},'html');
		},
		
		//======================================================================
		//=====================弹出信息窗口的初始化=============================
		//======================================================================
		//初始化弹出消息使用的几个modal窗口
		//判断消息是否已经初始化了，根据特定的alert modal的id是否存在来判断
		isMessageInited: function(){
			var msgObj = $('#jqbs_tmpl_modal_alert');
			if(msgObj == null || msgObj.length==0)
				return false;
			return true;
		},
		
		//初始化消息对象，一般页面开始时候调用
		initMessage: function(){
			if($.icekingutil.isMessageInited())
				return;
			//去bootstrap目录下的 tmpl_jqbs_message.html中load内容并append到body中
			var msgtmplurl = $.icekingutil.bootstrapPath()+'tmpl_jqbs_message.html';
			$.get(msgtmplurl+'?rnd='+Math.random(),function(data,status){
				if(status!='success'){
					//错误时候，直接在目标中添加错误信息
					alert('Load html from template error. url='+htmlUrl);
					return;
				}
				//成功，将返回的html插入到body中
				$(data).appendTo('body');
			},'html');
		},
		
		//弹出提示消息
		alert: function(msg,delay){
			if(!$.icekingutil.isMessageInited()){
				alert('消息函数未初始化');
				return;
			}
			//图标和文字切换一下
			$('#jqbs_tmpl_modal_alert_icon').attr('src',$.icekingutil.bootstrapPath()+'icon128/info_mail.png');
			$('#jqbs_tmpl_modal_alert_words').html(msg);
			
			//yes和no 两个按钮隐藏
			$('#jqbs_tmpl_modal_alert_btn_yes').hide();
			$('#jqbs_tmpl_modal_alert_btn_no').hide();
			
			if(delay!=null && delay>0){
				setTimeout(function(){
					$('#jqbs_tmpl_modal_alert').modal('show');
				},delay);
			}else{
				$('#jqbs_tmpl_modal_alert').modal('show');
			}
		},
		
		//弹出确认框，yes和no的操作可以自定义的
		confirm: function(msg,yesCallback,noCallback,autoclose){
			if(!$.icekingutil.isMessageInited()){
				alert('消息函数未初始化');
				return;
			}
			
			//图标和文字切换
			$('#jqbs_tmpl_modal_alert_icon').attr('src',$.icekingutil.bootstrapPath()+'icon128/faq.png');
			$('#jqbs_tmpl_modal_alert_words').html(msg);
			
			//yes和no 两个按钮可见
			$('#jqbs_tmpl_modal_alert_btn_yes').show();
			$('#jqbs_tmpl_modal_alert_btn_no').show();
			
			
			//设置yes和no的callback方法
			$('#jqbs_tmpl_modal_alert_btn_yes').unbind('click');
			$('#jqbs_tmpl_modal_alert_btn_yes').click(function(){
				if(yesCallback!=null && typeof(yesCallback)=='function'){
					yesCallback.call();
				}
				if(autoclose==null || autoclose)
					$('#jqbs_tmpl_modal_alert').modal('hide');
			});
			
			$('#jqbs_tmpl_modal_alert_btn_no').unbind('click');
			$('#jqbs_tmpl_modal_alert_btn_no').click(function(){
				if(noCallback!=null && typeof(noCallback)=='function'){
					noCallback.call();
				}
				if(autoclose==null || autoclose)
					$('#jqbs_tmpl_modal_alert').modal('hide');
			});
			
			$('#jqbs_tmpl_modal_alert').modal('show');
			
		},
		
		//======================================================================
		//=====================文件提交的初始化=============================
		//======================================================================
		
		//判断文件上传的modal窗口是否已经初始化了
		isFileUploadInited: function(){
			var msgObj = $('#jqbs_tmpl_modal_fileupload');
			if(msgObj == null || msgObj.length==0)
				return false;
			return true;
		},
		
		//初始化文件上传的modal内容
		initFileUpload: function(){
			if($.icekingutil.isFileUploadInited())
				return;
			//去bootstrap目录下的 tmpl_jqbs_fileupload.html中load内容并append到body中
			var msgtmplurl = $.icekingutil.bootstrapPath()+'tmpl_jqbs_fileupload.html';
			$.get(msgtmplurl+'?rnd='+Math.random(),function(data,status){
				if(status!='success'){
					//错误时候，直接在目标中添加错误信息
					alert('Load html from template error. url='+htmlUrl);
					return;
				}
				//成功，将返回的html插入到body中
				$(data).appendTo('body');
			},'html');
		},
		
		//打开文件上传的modal窗口
		openFileUpload: function(callback,autoclose){
			
			if(!$.icekingutil.isFileUploadInited()){
				alert('文件上传功能未初始化，请执行：$.icekingutil.initFileUpload()');
				return;
			}
			
			//文件内容的两个框清空
			/*$('#jqbs_tmpl_modal_fileupload').unbind('shown.bs.modal');
			$('#jqbs_tmpl_modal_fileupload').on('shown.bs.modal',function(){
				$.icekingutil.formItem('errorinfo','jqbs_tmpl_modal_fileupload_form1').html('&nbsp;');
				$.icekingutil.formItem('fileupload','jqbs_tmpl_modal_fileupload_form1').val('');
				$.icekingutil.formItem('filemark','jqbs_tmpl_modal_fileupload_form1').val('');
			});*/
			$.icekingutil.formItem('errorinfo','jqbs_tmpl_modal_fileupload_form1').html('&nbsp;');
			$.icekingutil.formItem('fileupload','jqbs_tmpl_modal_fileupload_form1').val('');
			$.icekingutil.formItem('filemark','jqbs_tmpl_modal_fileupload_form1').val('');
			
			//给提交按钮绑定事件
			$('#jqbs_tmpl_modal_fileupload_btn_submit').unbind('click');
			
			$('#jqbs_tmpl_modal_fileupload_btn_submit').click(function(){
				//使用ajaxFilUpload提交数据，返回之后，在调用callback
				var mark = $.icekingutil.formItem('filemark','jqbs_tmpl_modal_fileupload_form1').val();
				/*$.ajaxFileUpload({
					url:'cs',
					secureuri:false,
					//file的id
					fileElementId:'jqbs_tmpl_modal_fileupload',
					dataType:'json',
					//其它一并上传的普通参数，这里需要指定controller的类名字
					data:{cls:'FilUploadCtrl',mark:mark},
					//成功，请求成功，并非业务逻辑正确
					success: function (data, status) {
						alert(data);
						
						if(!data.succ){
							$.icekingutil.formItem('errorinfo','jqbs_tmpl_modal_fileupload_form1').html('ajaxFileUpload fail. status='+status+', stmt='+data.stmt);
							return;
						}
						if(callback!=null && typeof(callback)=='function'){
							//data.stmt 就是文件的名称
							callback(data.stmt);
						}
						if(autoclose==null || autoclose)
							$('#jqbs_tmpl_modal_fileupload').modal('hide');
					},
					//失败，请求失败
					error: function (data, status, e) {
						$.icekingutil.formItem('errorinfo','jqbs_tmpl_modal_fileupload_form1').html('ajaxFileUpload fail. status='+status);
						alert(e);
					}
				});*/
				
				//使用jquery.form.js 来提交表单
				$('#jqbs_tmpl_modal_fileupload_form1').ajaxSubmit({
					url:'fileuploadservlet',
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.succ){
							if(callback!=null && typeof(callback)=='function'){
								//data.stmt 就是文件的名称
								callback(data.stmt);
							}
							if(autoclose==null || autoclose)
								$('#jqbs_tmpl_modal_fileupload').modal('hide');
						}else{
							$.icekingutil.formItem('errorinfo','jqbs_tmpl_modal_fileupload_form1').html('ajaxFileUpload fail. stmt='+data.stmt);
						}
					}
				});
			});
			
			
			$('#jqbs_tmpl_modal_fileupload').modal('show');
		},
		
		
		//======================================================================
		//=====================针对表格的一些初始化定义操作=============================
		//======================================================================
		//tableId: 表格的id
		//options: 表格的参数定义：
		//	trHoverClass: 悬停的css，如果没有，则不设置悬停
		//	trClick: 行单击事件
		//	trDblclick: 行双击事件
		initTable: function(tableId,options){
			if(tableId==null)
				return;
			if(options==null)
				return;
			$('#'+tableId+' tr').each(function(index,element){
				var tr = $(element);
				//为行添加单击事件
				if(typeof(options.trClick)=='function'){
					tr.click(options.trClick);
				}
				//为行添加双击事件
				if(typeof(options.trDblclick)=='function'){
					tr.dblclick(options.trDblclick);
				}
				//为行添加悬停class
				if(typeof(options.trHoverClass)=='string' && options.trHoverClass!=''){
					tr.mouseover(function(){
						tr.addClass(options.trHoverClass);
					});
					tr.mouseout(function(){
						tr.removeClass(options.trHoverClass);
					});
				}
			});
		}
		
	});
})(jQuery);

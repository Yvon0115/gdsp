
/**
 * @class
 * @name JqueryAjaxEx
 * @description <jquery methods class> 基于jquery的.fn方法扩展
 * @example 例1：$("userTable").loadUrl({url:"/abc/abc"}) 加载截面
 */
define(["jquery","cas/core","cas/utils","cas/options","cas/mlib","plugins/jquery/jquery.history"],function($,$F,$utils,$options,$lib) {

	function _callBeforeFunction(ms,op){
		var r= true, $toggle = op.toggle?$(op.toggle):$doc;
		if(!$.isNumeric(op.callindex))
			op.callindex=-1
		$.each(ms,function(i,func){
			if(op.callindex >= i)
				return;
			op.callindex=i;
			if($.isFunction(func)){
				r=func(op,json)
			}else if(typeof func=="string"){
				var f= $lib.getCallBefore(func);
				if(f==null || !$.isFunction(f)){
					f=window[func];
					if(f == null || !$.isFunction(f))
						f=$toggle.getJsFunction(func,"op");
				}
				if($.isFunction(f))
					r=f(op)
			}
			return r;
		});
		return r !== false;
	}
	function _callBackFunction(ms,op,json){
		var r= true, $toggle = op.toggle?$(op.toggle):$doc;

		$.each(ms,function(i,func){
			if($.isFunction(func)){
				r=func(op,json)
			}else if(typeof func=="string"){
				var f= $lib.getCallBack(func);
				if(f==null || !$.isFunction(f)){
					f=window[func];
					if(f == null || !$.isFunction(f))
						f=$toggle.getJsFunction(func,"options,json");
				}
				if($.isFunction(f))
					r=f(op,json);
			}
			return r;
		});
		return r !== false;
	}
	/**
	 * 回调用方法列表
	 */
	function _callback(op,json){
		var cb1=op.callback,toggle=op.toggle,cb2=toggle?$(toggle).attr("ajax-after"):null,cb3=json?json.callback:null;
		var cb = [];
		if(cb1){
			if($.isArray(cb1)){
				$.merge(cb,cb1);
			}else{
				cb.push(cb1);
			}
		}
		if(cb2){
			cb2 = cb2.split(";");
			if($.isArray(cb2)){
				$.merge(cb,cb2);
			}else{
				cb.push(cb2);
			}
		}
		if(cb3){
			if($.isArray(cb3)){
				$.merge(cb,cb3);
			}else{
				cb.push(cb3);
			}
		}
		if(cb.length == 0)
			return;
		_callBackFunction(cb,op,json);
	};
	/**
	 * ajax之前调用的方法
	 * @param $toggle 触发元素
	 * @param options ajax选项
	 * @returns 是否可以继续调用
	 */
	function _breforeCall(op){
		var cb1=op.callbefore,toggle=op.toggle,cb2=toggle?$(toggle).attr("ajax-before"):null;
		var cb = [];
		if(cb1){
			if($.isArray(cb1)){
				$.merge(cb,cb1);
			}else{
				cb.push(cb1);
			}
		}
		if(cb2){
			cb2 = cb2.split(";");
			if($.isArray(cb2)){
				$.merge(cb,cb2);
			}else{
				cb.push(cb2);
			}
		}
		if(cb.length == 0)
			return true;
		return _callBeforeFunction(cb,op);
	}
	/**
	 * 表单提交后的方法处理
	 */
	var that = {

		/**
		 * ajax 远程调用
		 */
		ajaxCall:function(options){
			var op = $.extend({
				type:'POST',
				dataType:"json",
				cache: false,
				success:function(json){ that.ajaxSuccess(json,op);},
				error: that.ajaxError
			},options);
			if(!_breforeCall(op))
				return;
			if(op.data)
				op.data = $.param(op.data, true);
			$.ajax(op);
		},
		/**
		 * ajax成功后调用的方法
		 */
		ajaxSuccess:function(json,options){
			if(!that.ajaxHandle(json))
				return;
			_callback(options,json);
		},
		/**
		 * ajax访问错误处理
		 * @param xhr{XMLHttpRequest} xml请求对象
		 * @param ajaxOption {object} 调用选项
		 */
		ajaxError:function (xhr, ajaxOptions, thrownError){
			$F.messager.error(xhr.responseText);
		},
		/**
		 * ajax访问成功后的结果处理
		 */
		ajaxHandle:function (json){
			json=json||{};
			var status = json.statusCode,msg=json.message,STATUS=$F.statusCode;
			switch(status){
				case STATUS.error:
				case STATUS.internalError:
				case STATUS.notFound:
					if(!msg){
						msg="系统调用出错";
					}
					$F.messager.error(msg);
					return false;
				case STATUS.timeout:
					if(msg)
						$F.messager.error(msg, {okCall:$F.loadLogin});
					else
						$F.loadLogin();
					return false;
				default :
					if(msg)
						$F.messager.success(msg);
			}
			return true;
		}
	};
	$F.ajax = that;
	$.fn.extend({
		/**
		 * 通过ajax请求向元素内部增加请求结果html
		 * @url{string}请求的地址
		 * @data{object}请求数据
		 * @callback{function}回调方法
		 */
		loadUrl: function(url,data,callback){
			if(typeof url == "string")
				$(this).htmlAJAX({url:url, data:data, callback:callback});
			else
				$(this).htmlAJAX(url);
		},
		/**
		 * 当前容器加载URL内容
		 * @param options 加载选项
		 * {
		 * 		type:请求方式POST/GET默认POST
		 * 		cache:是否缓存true/false默认false 
		 * 		async:是否异步true/false默认true
		 * 		global:是否为全局请求(是否会触发ajaxStart和ajaxEnd的事件)默认为true
		 * 		success:请求成功时的方法处理
		 * 		error:请求失败的方法处理
		 * 		operator:加载内容后内容处理append/prepend/before/after/inner，默认设置为当前元素的内容
		 * 		init:是否进行界面初始化，默认为true
		 * 		history:true
		 * }
		 * 
		 */
		loadURLHtml: function(options){
			var $this = $(this);
			if ($.fn.xheditor) {
				$("textarea.editor", $this).xheditor(false);
			}
			var op = {
				type:'POST',
				cache:false,
				async:true,
				dataType:"json",
				init:true,
				history:false,
				title:window.title,
				toggle:$this,
				success:function(response,flag,xhr){
					if(op.history){
						//$.history.load(this.url.replace(/^.*#/, ''));
					}
					var json = $.jsonEval(response);
					if(!that.ajaxHandle(json))
						return;

					switch(this.operator){
					case "append":
						$this.append(response);
						break;
					case "prepend":
						$this.prepend(response);
						break;
					case "before":
						$this.before(response);
						break;
					case "after":
						$this.after(response);
						break;
					case "replace":
						$this=$this.replaceWith(response);
						break;
					default:
						$this.html(response);
					}
					if(options.init !==false){
						$this.initPageUI();
					}
					_callback(options);
					$this.fadeIn();
				},
				error:function (xhr, ajaxOptions, thrownError){
					this.success(xhr.responseText);
				}
			};
			op = $.extend(op,$options.ajax,options);
			if(!_breforeCall(op))
				return;
			$.ajax(op);
		},
		appendAJAX:function(options){
			options.operator='append';
			$(this).loadURLHtml(options);
		},
		prependAJAX:function(options){
			options.operator='prepend';
			$(this).loadURLHtml(options);
		},
		breforeAJAX:function(options){
			options.operator='before';
			$(this).loadURLHtml(options);
		},
		afterAJAX:function(options){
			options.operator='after';
			$(this).loadURLHtml(options);
		},
		replaceAJAX:function(options){
			options.operator='replace';
			$(this).loadURLHtml(options);
		},
		htmlAJAX:function(options){
			options.operator=null;
			$(this).loadURLHtml(options);
		}

	});
	$("body").on("click.data-api","[ajax-toggle='ajax']", function(event){
		event.preventDefault();
		var $this = $(this)
			,url = $utils.evalElementAttr($this)
			,m = $this.attr("ajax-method")||"GET";
		if (!url) {
			return false;
		}
		that.ajaxCall({url:url,type:m,toggle:$this});
	});
	return that;
});
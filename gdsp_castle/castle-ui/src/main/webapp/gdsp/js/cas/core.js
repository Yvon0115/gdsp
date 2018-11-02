/**
 * @class
 * @name $Frame($F)
 * @description <jquery methods class> 全局Frame对象
 * @example 例1：$F.regInitMethod("initUser",function(){alert(1)}) 应用启动
 * @example 例2：$F.nextId() 取id
 */
define(["jquery","base/json2"],function($){

	var $F = undefined;
	$F = function(selector, context){
		if(typeof $F._exfn == "function"){
			return $F._exfn(selector, context);
		}
		return $(selector, context);
	};

	$.extend($F,{
		/**
		 * 设置为开发状态
		 */
		develop:true,
		/**
		 * 常用键盘码常量
		 */
		keyCode: {
			ENTER: 13, ESC: 27, END: 35, HOME: 36,
			SHIFT: 16, TAB: 9,
			LEFT: 37, RIGHT: 39, UP: 38, DOWN: 40,
			DELETE: 46, BACKSPACE:8
		},
		rspace: /[\t\r\n\f]/g,
		/**
		 * 远程访问的状态码
		 */
		statusCode: {ok:200, error:300, timeout:301,internalError:500,notFound:404},

		/**
		 * require 脚本文件时缓存的参数及对象使用的属性
		 */
		jsattrs: {PARAM:"__jsparam", OBJECT:"__jsobj"},
		/**
		 * 系统初始化方法集合
		 */
		initMethods:{
		},
		/**
		 * 系统销毁方法集合
		 */
		destroyMethods:{
		},
		/**
		 * 状态对象
		 */
		status:{
			spliter:true
		},
		/**
		 * 自定义事件类型
		 */
		eventType:{
			pageLoad:"onload",
			pageDestroy:"pageDestroy"
		},
		messager: {
			error: function (msg, options){
				alert(msg);
				if (options.okCall && $.isFunction(options.okCall)) {
					options.okCall();
				}
			},
			warning:this.error,
			correct:this.error
		},
		/**
		 * 注册初始化方法
		 * @param key{String/Object} 包含多个初始化方法的集合
		 * @param func{Function} 构造方法
		 */
		regInitMethod:function(key,func){
			var othis = this;
			var initMethods = othis.initMethods;
			if($.isPlainObject(key)){
				initMethods = $.extend(initMethods,key);
			}else if($.isFunction(key)){
				if(key.appid){
					initMethods[key.appid] = null;
					delete initMethods[key.appid];
				}else{
					key.appid = othis.nextId();
					initMethods[key.appid]=key;
				}
			}else if(typeof key==="string"){
				if( func && $.isFunction(func)){
					func.appid=key;
					initMethods[key] = func;
				}else{
					initMethods[key] = null;
					delete initMethods[key];
				}
			}else{
				throw "valid component's constructor";
			}
		},
		/**
		 * 初试化方法调用
		 */
		initCalls:function($box){
			var othis = this;
			var initMethods = othis.initMethods;
			for (name in initMethods ) {
				initMethods[name]($box);
			}
		},
		/**
		 * 初始化方法，包括组件和布局等
		 * @param box{jquery Object|DOM|string} 初始化范围
		 */
		init:function(box){
			$box=$(box||"body");
			/*清理业务脚本对象缓存*/
			$box.clearAppJsObject();
			var $js = $("[name=jsRequire]",$box);
			if($js.length == 0){
				$F.initCalls($box);
				return;
			}
			var rs =new Array,ids=new Array,loads=new Array;
			$js.each(function(idx,item){
				var $item=$(this),jspath = $item.val(),load=$item.attr("onload"),id = $item.attr("id");
				if(jspath){
					if(!id) {
						id = "param"+$F.nextId();
					}
					ids.push(id);
					rs.push(jspath);
				}
				if(load){
					loads.push(load);
				}
			});
			if(loads.length>0){
				$box.on($F.eventType.pageLoad,function(){
					func = $box.getJsFunction(loads.join(";"));
					func();
				})
			}
			if(rs.length == 0){
				$F.initCalls($box);
				return;
			}
			require(rs,function(){
				var jsobj = new Array,nids=new Array,args=arguments;
				$.each(args,function(i){
					if(args[i] != null && ids[i]!=null){
						jsobj.push(args[i]);
						nids.push(ids[i]);
					}
				});
				if(nids.length > 0){
					$box.setAppJsObject(nids,jsobj);
				}
				$F.initCalls($box);
			});
			$js.remove();
		},
		/**
		 * 页面销毁时处理方法
		 */
		regDestroyMethod:function(key,func){
			var othis = this;
			var destroyMethods = othis.destroyMethods;
			if($.isPlainObject(key)){
				$.extend(destroyMethods,key);
			}else if($.isFunction(key)){
				if(key.appid){
					destroyMethods[key.appid] = null;
					delete destroyMethods[key.appid];
				}else{
					key.appid = othis.nextId();
					destroyMethods[key.appid]=key;
				}
			}else if(typeof key==="string"){
				if( func && $.isFunction(func)){
					func.appid=key;
					destroyMethods[key] = func;
				}else{
					destroyMethods[key] = null;
					delete destroyMethods[key];
				}
			}else{
				throw "valid component's destroy";
			}
		},
		/**
		 * 销毁方法调用
		 */
		destroyCalls:function($box,e){
			var othis = this;
			var destroyMethods = othis.destroyMethods;
			for (name in destroyMethods ) {
				destroyMethods[name]($box,e);
			}
		},
		/**
		 * 页面加载时调动，非ajax加载
		 * @param options
		 */
		boot:function(options){
			this.options = $.extend(this.options, options);
			this.init();
			var _doc = $(document);
			var $this = this;
			if (!_doc.isBind(this.eventType.pageDestroy)) {
				_doc.bind(this.eventType.pageDestroy, function(e){
					var box = e.target;
					$this.destroyCalls(box,e);
				});
			}
		},

		/**
		 * 生成下一个id或一组id
		 * @param count{Number} 生成id的个数
		 */
		nextId : (function() {
			var idno = 0;
			return function(count) {
				if(count&&count>1){
					var ids = [];
					for(var i=0;i<count;i++){
						idno++;
						ids.push("app_" + idno);
					}
					return ids;
				}
				return ++idno;
			};
		})(),

		/**
		 * 调试信息输出
		 */
		log:function(msg){
			if (this.develop) {
				if (typeof(console) != "undefined") console.log(msg);
				else alert(msg);
			}
		},
		/**
		 * 生成唯一的id字符串：前缀+当前时间毫秒数
		 * @param prefix 前缀
		 * @returns {String}
		 */
		uuid: function(prefix) {
			prefix = prefix === undefined ? 'bs_' :prefix;
			return prefix + this.nextId();
		},
		/**
		 * 局部刷新界面锁定开关
		 * @param bool 布尔类型，true时显示加载界面锁定，false时屏蔽加载动画界面响应。
		 * @author ZhaoMY
		 */
		loadingSwitch: function(bool){
			if(bool == true){
				$(document).ajaxStart(function(){
					$('#__preloader').show();
				});
			} else {
				$(document).ajaxStart(function(){
					$('#__preloader').hide();
				});
			}
		},
		/**
		 * 开始结束日期控制
		 * @description 配合控件支持yyyy-mm-dd、yyyy-mm和yyyy格式
		 * @param 开始日期，结束日期
		 * @author ZhaoMY
		 */
		datepickerContro: function (startDate, endDate){
			//适应一个日期框的时候的处理情况
			if (endDate == undefined) {
				endDate = startDate;
			}
			if(startDate == "" || endDate == ""){
				$F.messager.error("查询日期不能为空！",{"label":"确定"});
				return false;
			}
			var eArr = endDate.split('-');
			//处理结束日期
			switch (eArr.length) {
				case 1://年
					eDate = new Date(eArr[0]);
					break;
				case 2://年月
					eDate = new Date(eArr[0],parseInt(eArr[1])-1);
					break;
				case 3://年月日
					eDate = new Date(eArr[0],parseInt(eArr[1])-1,eArr[2]);
					break;
			}
			//判断结束日期是否大于当前时间
			if(eDate > new Date()) {
				$F.messager.error("选择日期不能大于当前日期！",{"label":"确定"});
				return false;
			} else {
				//处理开始日期
				var sArr = startDate.split('-');
				switch (sArr.length) {
				case 1://年
					sDate = new Date(sArr[0]);
					break;
				case 2://年月
					sDate = new Date(sArr[0],parseInt(sArr[1])-1);
					break;
				case 3://年月日
					sDate = new Date(sArr[0],parseInt(sArr[1])-1,sArr[2]);
					break;
				}
				if(sDate > eDate) {
					$F.messager.error("开始日期大于结束日期！",{"label":"确定"});
					return false;
				} else {
					return true;
				}
			}
		},
		datepickerControAch: function (startDate, endDate){
			//适应一个日期框的时候的处理情况
			if (endDate == undefined) {
				endDate = startDate;
			}
			
			var eArr = endDate.split('-');
			//处理结束日期
			switch (eArr.length) {
				case 1://年
					eDate = new Date(eArr[0]);
					break;
				case 2://年月
					eDate = new Date(eArr[0],parseInt(eArr[1])-1);
					break;
				case 3://年月日
					eDate = new Date(eArr[0],parseInt(eArr[1])-1,eArr[2]);
					break;
			}
			//判断结束日期是否大于当前时间
			if(eDate > new Date()) {
				$F.messager.error("选择日期不能大于当前日期！",{"label":"确定"});
				return false;
			} else {
				//处理开始日期
				var sArr = startDate.split('-');
				switch (sArr.length) {
				case 1://年
					sDate = new Date(sArr[0]);
					break;
				case 2://年月
					sDate = new Date(sArr[0],parseInt(sArr[1])-1);
					break;
				case 3://年月日
					sDate = new Date(sArr[0],parseInt(sArr[1])-1,sArr[2]);
					break;
				}
				if(sDate > eDate) {
					$F.messager.error("开始日期大于结束日期！",{"label":"确定"});
					return false;
				} else {
					return true;
				}
			}
		}
	});
	/**
	 * 初始化方法
	 */
	$.fn.initPageUI =function(){
		$F.init(this);
	}
	return $F;
});
/**屏蔽 backspace */
$(document).keydown(function(e){
     var target = e.target ;
     var tag = e.target.tagName.toUpperCase();
     if(e.keyCode == 8){
	      if((tag == 'INPUT' && $(target).attr("readonly"))||(tag == 'TEXTAREA' && $(target).attr("readonly"))){
	      	return false ;
	      }else{
	      	 return true ;
	      }
     }
 });
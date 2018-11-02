/**
 * @class
 * @name jQuery.fn.dataloader
 * @description <jquery methods class> dataloader数据加载器DATALOADER AJAX PLUGIN DEFINITION
 * @example 例1：$("aaa").dataloader("addPlugin",plugin)添加插件
 */
define(["jquery","cas/core","cas/options","cas/utils"],function ($,$F,$options,$utils) {
	
	"use strict"; // jshint ;_;


	/**
	 * 数据加载器隐藏类
	 * @param element dom对象.
	 * @param options 选项
	 * @constructor DataLoader
	 */
	var DataLoader = function (element,options) {
		var o = this
		,$el = o.$element = $(element)
		,initialLoad = $el.attr("initialLoad")
		,paramStr = $el.attr("params");
		o.params = $utils.parseParameter(paramStr);
		o.plugins = {};
		/**
		 * 页面初始加载是否加载数据
		 */
		if(initialLoad == "true"){
			var id = $el.attr("id")
			,$query = $(".xquery[target='#"+id+"']");
			if($query.length>0){
				var queryId = $query.attr("id")
				,$queryAction = $("[cas-query='#"+queryId+"']");
				$("body").on($F.eventType.pageLoad,function(){
					$F.loadingSwitch(false);
					$queryAction.trigger("click");
					$F.loadingSwitch(true);
				})
			}else{
				o.load();
			}
		}
	}
	/**
	 * 原型定义
	 */
	DataLoader.prototype = {

		constructor: DataLoader,
		/**
		 * 设置固有参数
		 * @param params {Object} 参数对象
		 */
		setParams:function(params){
			if(!params){
				this.params = null;
			}
			if(typeof params == "string")
				params = $utils.parseParameter(params);
			this.params = $.extend(this.params,params);
		},
		/**
		 * 缓存参数
		 * @param params {string/Object} 参数对象
		 */
		cacheParams:function(params){
			if(!params){
				this.cachedParams = null;
				return;
			}
			if(typeof params == "string")
				params = $utils.parseParameter(params);
			this.cachedParams = $.extend({},this.cachedParams,params);
		},
		/**
		 * 添加插件
		 */
		addPlugin: function (plugin) {
			this.plugins[plugin.name] = plugin;
		},
		/**
		 * 删除插件
		 */
		removePlugin: function(name) {
			this.plugins[name] = null;
			delete this.plugins[name];
		},
		
		/**
		 * 获取查询参数
		 */
		getLoadParams: function(){
			var o = this
			,params = $.extend({},o.params, o.cachedParams)
			,plugins = o.plugins;
			$.each(plugins,function(n,p){
				if(p.getParameters){
					$.extend(params,$utils.parseParameter(p.getParameters()));
				}
			});
			return params;
		},
		/**
		 * 数据加载后的数据
		 */
		afterLoad:function(){
			var o = this
			,plugins = o.plugins;
			$.each(plugins,function(n,p){
				if(p.refresh){
					p.refresh(o.$element)
				}
			});
		},
		/**
		 * 数据加载
		 */
		load: function(parameters){
			var o = this;

			var params=o.getLoadParams();
			if(parameters){
				$.extend(params,$utils.parseParameter(parameters));
			}
			var options = $.extend({
					url:o.$element.attr("url"),
					history:false,
					cache:false,
					data: $.param(params,true),
					callback:function(){
						o.afterLoad();
					}
				},$options.dataLoader);
			if(!options.url)
				return;
			o.$element.loadUrl(options);
			
		},
		
		/**
		 * 数据加载
		 */
		loadReset: function(){
			var o = this
			,plugins = o.plugins;
			o.cachedParams = null;
			$.each(plugins,function(n,p){
				if(p.loadReset){
					p.loadReset();
				}
			});
		}
	}

	/**
	 * jQuery.fn扩展，是dataloader的入口DATALOADER AJAX PLUGIN DEFINITION
	 * @param option 选项
	 * @param value 参数
	 * @returns {*}
	 */
	$.fn.dataloader = function (option,value) {
		var methodReturn;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('dataloader');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('dataloader', (data = new DataLoader(this, options)));
			if (typeof option === 'string') methodReturn = data[option](value);
		});
		return (methodReturn === undefined) ? $set : methodReturn;
	};
	$.fn.dataloader.Constructor = DataLoader;

	$("body").on("click","[dataloader-toggle]", function(event){
		if(event)
			event.preventDefault();
		var $this = $(this)
			,target = $this.attr("dataloader-toggle")
			,param = $this.attr("dataloader-param")
			,reset = $this.attr("dataloader-reset")||""
			,hold = $this.attr("dataloader-hold")||"";
		var $target = $(target);
		if ($target.length == 0) {
			return false;
		}
		param = $utils.parseParameter(param);
		if(reset&&"true"===reset.toLowerCase())
			$target.dataloader("loadReset");
		if("cache"===hold.toLowerCase()){
			$target.dataloader("cacheParams",param);
			$target.dataloader("load");
		}else if("init"===hold.toLowerCase()){
			$target.dataloader("setParams",param);
			$target.dataloader("load");
		}else{
			$target.dataloader("load",param)
		}

	});
	/**
	 * 注册参数回调方法
	 * @param name {string/Function} 插件名字或插件回调方法
	 * @param func {Function} 插件名字或插件回调方法
	 */
	$.fn.addCallbackDataloaderPlugin = function(name,func){
		var $target = $(this);
		if(typeof name != "string"){
			if(!$.isFunction(name)) {
				return;
			}else{
				func = name;
				name = "dataloader_plugin"+$F.nextId();
			}
		}else if(!$.isFunction(func)){
			return;
		}
		$target.dataloader("addPlugin",{
			name:name,
			getParameters:function(){
				return func();
			}
		});
	}
	/**
	 * 判断当前jquery对象是否为dataloader
	 * @return {boolean} 是否为dataloder
	 */
	$.fn.isDataloader = function(){
		return $(this).hasClass("dataloader");

	}
});
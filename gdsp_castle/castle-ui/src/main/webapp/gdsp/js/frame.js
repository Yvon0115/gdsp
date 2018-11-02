!function ($) {
	require.config({
		baseUrl: __jsPath
		,shim: {
			"base/json2": {
				exports: "JSON"
			},
			"base/underscore": {
				exports: '_'
			},
			"bootstrap/bootstrap": {
				exports: '$.fn.popover'
			},
			daterangepicker:{
				deps:["moment"]
			},
			pqGrid:{
				deps:["jqueryui"]
			}
		}
		,paths: {
			script:"../script",
			css:"../css",
			jquery:"cas/jqex",
			perfectScrollbarJQuery: 'plugins/scrollbar/perfect-scrollbar.jquery',
			moment:"plugins/daterangepicker/moment",
			daterangepicker:"plugins/daterangepicker/daterangepicker",
			echarts: 'plugins/echarts',
			ckeditor: 'plugins/ckeditor',
			jqueryui:"plugins/jquery/jquery-ui",
			pqGrid:"plugins/pqGrid/pqgrid-dev"
		}
		,map: {
			'*': {
				'link': 'base/css'
			}
		},
		config:{
			"moment":{
				noGlobal:false
			}
		}
		/*packages: [
		           {
		               name: 'echarts',
		               location: 'plugins/echarts-2.2.7/echarts/src',
		               main: 'echarts'
		           },
		           {
		               name: 'zrender',
		               location: 'plugins/echarts-2.2.7/zrender/src', // zrender与echarts在同一级目录
		               main: 'zrender'
		           }
		       ]*/
	});

	/**
	 * establish history variables
	 */
	var History = window.History; // Note: We are using a capital H instead of a lower h
	window.localeFile=(window.localeFile?window.localeFile:"cas/lang_zh_CN");
	var __extendOptions=(window.__extendOptions?window.__extendOptions:null);
	var userAgent = navigator.userAgent.toLowerCase();
	$.extend($,{
		browser : {
			version: (userAgent.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [])[1],
			mozilla: /firefox/.test(userAgent),
			webkit : /webkit/.test(userAgent),
			opera : /opera/.test(userAgent),
			msie : /msie/.test(userAgent)
		},
		closeWindow:function(){
			if(!$.browser.msie){
				$F.messager.error("浏览器不支持代码关闭,请直接点击浏览器'X'关闭当前系统!");
			}
			window.opener=null;
			window.open('','_self');
			window.close();
		},
		/**
		 * 获取扩展选项的js地址
		 * @param requires {string/array}需要的引入的其他js文件
		 * @return {string/array} 需要引入的所有的js文件
		 */
		getExtendOptions:function(requires){
			if(requires){
				if($.isArray(requires)){
					if($.isArray(__extendOptions)){
						return requires.concat(__extendOptions)
					}else{
						return requires.concat([__extendOptions]);
					}
				}else{
					if($.isArray(__extendOptions)){
						return [requires].concat(__extendOptions)
					}else{
						return [requires,__extendOptions];
					}
				}

			}else{
				return __extendOptions;
			}
		},
		/**
		 * 取得css路径绝对
		 * @param path {String} css文件相对路径
         * @returns {String} css路径绝对
         */
		getCssPath:function(path){
			path=path?(path.charAt(0)=="/"?path:"/"+path):"";
			return __cssPath+path;
		},
		/**
		 * 取得通过require加载的css路径
		 * @param path {String} css文件相对路径
		 * @returns {string} require可用的加载的css路径
		 */
		getRequireCss:function(path){
			return "link!"+this.getCssPath(path);
		}
	});


	require(["cas/core","cas/utils","cas/messager","cas/options","cas/ajax","cas/register","cas/container","cas/tab","cas/modal","plugins/echarts/echarts.min"], function ($F,$utils,$messager,$options,$ajax) {

		/**
		 * 增加启动方法
		 */
		//window.jQuery = window.$=$;
		window.$doc = $(document);
		window.$F = window.$f = $F;
		window.$Msg = $messager;
		window.$Utils = $utils;
		window.$Options = $options;
		window.$Ajax = $ajax;

		/*导入扩展的脚本*/
		if ($options.includes && $options.includes.length > 0) {

			require($options.includes, function () {
				$F.boot();
			});
		}else {
			$F.boot();
		}

	})
}(window.jQuery);

define(["cas/core","cas/options",localeFile],function($F,$options){
	$.extend($F,{
		/**
		 * 扩展$F的方法
		 */
		_exfn:function(selector, context){
			var $con = this.getContainer();
			var $ctrl;
			if(!selector){
				if(context)
					return context;
				return $con; 
			}
				
			if(context&&$con.has(context)){
				$ctrl = $(context,$con).find(selector);
			}else{
				$ctrl = $con.find(selector);
			}
			return $ctrl;
		},

		/**
		 * 取得当前对话框对象
		 */
		getCurrentDialog:function(){
			if($.getCurrentDialog)
				return $.getCurrentDialog();
		},
		/**
		 * 取得当前对话框对象
		 */
		getPageContainer:function(){
			return $("#__castlePageContent:first");
		},
		/**
		 *  取得当钱所在容器(对话框/选项卡);
		 */
		getContainer:function(){
			var container = this.getCurrentDialog();
			if(!container||container.length==0){
				container=this.getPageContainer();
			}
			if(!container||container.length==0){
				container=$(document);
			}
			return container;
		},
		/**
		 * 查找当前对象所处容器
		 * @param 指定jquery selector对象
		 */
		findContainer:function(selector){
			var $o = $(selector);
			if($o.size == 0)
				return null;
			var $con = null;
			if($o.is($(document)) || $o.hasClass("modal")){
				$con = $o;
			}else{
				$con = $o.parents("(.modal):first");
			}
			if($con.size() == 0){
				$con = $(document);
			}
			return $con;
		},
		/**
		 * 添加初始化事件处理
		 * @param func{function} 事件处理方法
		 * @param target{jquery object} 事件触发对象
		 */
		pageLoad:function(func,target){
			if(!func || typeof func!="function")
				return;
			if(!target){
				target = this.getContainer();
			}
			if($(target).is(document)){
				$(document).ready(func);
			}else{
				$(target).on(this.eventType.pageLoad,func);
			}

		},
		loadLogin:function(){
			window.location=__contextPath+$options.pages.login;
		},
		showPreloader:function(){
			$($options.config.preloader).show();
		},
		closePreloader:function(){
			$($options.config.preloader).hide();
		}
	});

	$F.regInitMethod("globalCasInit",function(){
		//$("body").append($frags["globalBodyFrag"]);
		if ($.browser.msie && /6.0/.test(navigator.userAgent) ) {
			try {
				document.execCommand("BackgroundImageCache", false, true);
			}catch(e){}
		}
		//清理浏览器内存,只对IE起效
		if ($.browser.msie) {
			window.setInterval("CollectGarbage();", 10000);
		}
		
		var preloader = $($options.config.preloader);
		if(preloader.length > 0){
			$(document).ajaxStart(function(){
				preloader.show();
			}).ajaxStop(function(){
				preloader.hide();
			});
			setTimeout(function(){
				preloader.hide();
			}, 10);
		}
		$F.regInitMethod("globalCasInit");
	});
	return $F;
	
	
});



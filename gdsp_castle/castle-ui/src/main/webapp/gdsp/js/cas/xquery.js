/**
 * 查询对象
 */
define(["jquery","cas/core","cas/ajax","cas/modal"],function($,$F,$ajax) {
	"use strict";
	/**
	 * 构造方法
	 */
	var XQuery= function (element, options) {
		var o = this
		,$el = o.$element = $(element)
		,target = $el.attr("target")
		, parameter= $el.attr("parameter")||"auto";
		var $target = $F(target);
		var mode = "form";
		if($target.length>0){
			o.$target=$target;
			mode="ajax";
			if(parameter !="parameter")
				parameter="condition";
			if($target.isDataloader()){
				mode = "dataloader";
				o.$target.dataloader("addPlugin",{
					name:"xquery",
					getParameters:function(){
						return o.getParameters();
					}
				});
			}
			o.$target=$target;
		}else if(parameter!="condition"){
			parameter="parameter";
		}
		o.parameter = parameter;
		o.mode = mode;
		o.hiddenCon = {};
	};
	var rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i,
			rsubmittable = /^(?:input|select|textarea|keygen)/i,
			rCRLF = /\r?\n/g,
			rcheckableType = (/^(?:checkbox|radio)$/i);
	/**
	 * 条件参数收集
	 * @param $e{jquery object} 查询组件范围
	 * @param iadv{boolean} 是否包含高级查询
	 * @return {Json} 参数对象
	 */
	function collectionCondition(o,iadv){
		var params = [],$e=o.$element,con=o.hiddenCon;
		if(iadv === null || iadv === undefined)
			iadv= true;
		if(con != null){
			$.each(con,function(key,param){
				if(param){
					params.push(param);
				};
			});
		}
		/*
			reference form.serializeArray();
		 */
		var fields =$e.map(function() {
					// Can add propHook for "elements" to filter or add form elements
					var elements = $.prop( this, "elements" );
					return elements ? $.makeArray( elements ) : this;
				})
				.filter(function() {
					var type = this.type;

					// Use .is( ":disabled" ) so that fieldset[disabled] works
					return this.name && !$( this ).is( ":disabled" ) &&
							rsubmittable.test( this.nodeName ) && !rsubmitterTypes.test( type ) &&
							( this.checked || !rcheckableType.test( type ) )&&(iadv||$(this).is("[adv!=true]"));
				})
				.map(function( i, elem ) {
					var val = $( this ).val();
					var op = $( this ).attr("op");
					return (val == null || val=="" || !op)?
							null :
							$.isArray( val ) ?
									$.map( val, function( val ) {
										return { name: elem.name, value: val.replace( rCRLF, "\r\n" ) ,op:op};
									}) :
							{ name: elem.name, value: val.replace( rCRLF, "\r\n" ) ,op:op};
				}).get();
		return $.merge(params,fields);
	};
	/**
	 * 回调方法的方法
	 * @param ms 方法集合有方法名方法对象组成的数组
	 * @param $el form对象
	 * @returns {boolean}是否可以继续调用
	 */
	function _callBeforeFunction(ms,$el){
		var r= true;
		$.each(ms,function(i,func){
			if($.isFunction(func)){
				r=func($el)
			}else if(typeof func=="string"){
				func=window[func];
				if(func == null || !$.isFunction(func))
					func=$el.getJsFunction(func,"$el");
				$.isFunction(func);
					r=func($el)
			}
			return r;
		});
		return r !== false;
	}
	/**
	 * 提交表单之前调用的方法
	 * @param $toggle {jquery el}触发元素
	 * @param options {object} ajax选项
	 * @returns {boolean}是否可以继续调用
	 */
	function _breforeCall($el){
		var cb = $el.attr("ajax-before");
		if(!cb)
			return true;
		cb = cb.split(";");
		if(cb.length == 0)
			return true;
		return _callBeforeFunction(cb,$el);
	}
	/**
	 * 原型定义
	 */
	XQuery.prototype = {
		/**
		 * 快速查询
		 */
		submit:function(){
			var o = this,$el= o.$element;
			if (o.mode!="dataloader"&&!$el.attr("action")) {
				return false;
			}
			if((" " + $el.attr("class") + " ").replace($F.rspace, " ").indexOf("validate") >= 0){
				$el.submit();
				return;
			}
			$el.xquerySubmitHandler();
		},
		clear:function(){

			this.$element.map(function() {
						// Can add propHook for "elements" to filter or add form elements
						var elements = $.prop( this, "elements" );
						return elements ? $.makeArray( elements ) : this;
					})
					.filter(function() {
						var type = this.type;

						// Use .is( ":disabled" ) so that fieldset[disabled] works
						return this.name && !$( this ).is( ":disabled" ) &&
								rsubmittable.test( this.nodeName ) && !rsubmitterTypes.test( type ) &&
								( this.checked || !rcheckableType.test( type ) )&&(iadv||$(this).is("[adv!=true]"));
					}).each(function(){
						$(this).val("");
					});

		},
		/**
		 * 添加额外参数，只在ajax和dataloader模式好用
		 * @param param {json} {name:"",value:"",op:""}形式的参数
         */
		addParameter:function(param){
			var con = this.hiddenCon,name=param.name,val=param.value;
			if(!name)
				return;
			if(val===null||value==="" || value === undefined){
				con[name] = null;
				delete con[name];
			}else{
				con[name]=param;
			}
		},
		getParameters:function(){
			if(this.parameter == "parameter") {
				return this.$element.serializeArray();
			}else{
				var params = collectionCondition(this);
				return {"__xquery":JSON.stringify(params)}
			}
		}
	};
	/**
	 * jquery方法扩展xquery
	 */
	$.fn.xquery=function(option, value){
		var methodReturn
		,options = typeof option === 'object'? $.extend({},option):null;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('xquery');
			if (!data) $this.data('xquery', (data = new XQuery(this, options)));
			if (typeof option === 'string'){
				methodReturn = data[option](value);
			}
		});
		return (methodReturn === undefined) ? $set : methodReturn;
	};
	/**
	 * jquery方法扩展xquery,提交处理方法
	 */
	$.fn.xquerySubmitHandler=function(){
		var $el=$(this),o=$el.data('xquery'),$target = o.$target;

		/*提交dataloader方式加载数据*/
		if(o.mode == "dataloader"){
			$target.dataloader("loadReset");
			$target.dataloader("load");
			return false;
		}

		/*表单方式提交数据进行查询*/
		if(mode == "form"){
			if(_breforeCall($el))
				return true;
			return false;
		}

		if(!$target||$target.length == 0)
			return false;
		/*通过ajax方式loadUrl的方式加载*/
		var url=$el.attr("action");
		url =url.evalTemplate($el);
		var op = {
			type:$el.attr("method") || 'GET',
			url:url,
			data: o.getParameters(),
			dataType:"json",
			cache: false,
			toggle:$el
		};
		$target.loadUrl(op);
		return false;
	};
	$.fn.xquery.Constructor = XQuery;
	$("body").on("click.bs.xquery.data-api","[cas-query]",function ( e ) {
		if(e)
			e.preventDefault();
		var $query = $($(this).attr("cas-query"));
		if($query.length>0&&$query.isTag("form")){
			$query.xquery('submit');
		}

	});
 	return $F;
});
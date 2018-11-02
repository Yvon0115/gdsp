/**
 * 针对jquery增加核心的扩展
 * @depend 依赖应用核心和pinyin工具类
 */
define(["base/pinyin"],function($pinyin){
	/**
	 * 空方法
	 */
	$.emptyFunction = function(){};
	/**
	 * 字符串转换为json对象
	 * @param json{Object} json对象
	 */
	$.jsonEval = function (json) {
		try{
			return eval('(' + json + ')');
		} catch (e){
			return {};
		}
	};
	/**
	 * json转换为字符串
	 * @param json{Object} json对象
	 * @param {string} 字符串
	 */
	$.toJsonString = function (value, replacer, space){
		return JSON.stringify(value, replacer, space);
	};
	/**
	 * 扩展jquery方法
	 */
	$.extend($.fn,{
		/**
		 * 清除js对象缓存
		 */
		clearAppJsObject:function(){
			$(this).removeAttr("__jsappobj");
			$(this)[0].__jsappobj=null;
		},
		/**
		 * 设置js对象缓存
		 */
		setAppJsObject:function(jsParam,jsobjs){
			$(this).attr("__jsappobj","true");
			$(this)[0].__jsappobj={
				param:jsParam,
				objs:jsobjs
			};
		},
		/**
		 * 取得js对象缓存
		 * @return {Object}带有param和objs属性,param为参数名,objs为js对象数组
		 */
		getAppJsObject:function(){
			var $this=$(this)
			,$parents = $this.parents("[__jsappobj]")
			,jsobj = $this[0].__jsappobj
			,params=null
			,jsobjs=null;
			if(jsobj != null && typeof jsobj=="object" && $.isArray(jsobj.param) && $.isArray(jsobj.objs)){
				params=jsobj.param;
				jsobjs = jsobj.objs;
			}else{
				params=[];
				jsobjs =[];
			}
			$parents.each(function(){
				var $p=$(this)
				,j = this.__jsappobj;
				if(j == null)
					return;
				if(!$.isArray(j.param) || !$.isArray(j.objs))
					return;
				var jp=j.param,jo=j.objs;
				for(var i = 0; i < jp.length; i++){
					if($.inArray(jp[i],params)<0){
						params.push(jp[i]);
						jsobjs.push(jo[i]);
					}
				}
			});
			if(params.length == 0)
				return null;
			return {param:params.join(","),objs:jsobjs};
		},
		/**
		 * 获取带有引入js对象的方法
		 */
		getJsFunction:function(funcstr,param){
			var o = $(this).getAppJsObject();
			if(param == null)
				param = ""
			if(o){
				var f = new Function(o.param,"return function("+param+"){"+funcstr+"};");
				return f.apply(o,o.objs);
			}
			if(param == "")
				return new Function(funcstr);
			else
				return new Function(param,funcstr);
		},
		/**
		 * 获取带有引入js对象的方法
		 */
		getJsCacheFunc:function(funcstr){
			var $this = $(this)
			,func = $this.data(funcstr);
			if(func){
				return func;
			}
			func = $this.getJsFunction(funcstr);
			$this.data(funcstr,func);
			return func;
		},
		/**
		 * 获取带有引入js对象的方法
		 */
		getJsEvent:function(events){
			var o = $(this).getAppJsObject();
			if(o){
				var f = new Function(o.param,'var e = '+events+';return e;');
				return f.apply(o,o.objs);
			}
			return $.jsonEval(events);
		},
		/**
		 * 判断当前元素的是否为指定的标签
		 * @param tn 指定的标签
		 */
		isTag:function(tn) {
			if(!tn) return false;
			return $(this)[0].tagName.toLowerCase() == tn?true:false;
		},
		/**
		 * 鼠标悬浮样式处理
		 * @param className{String}样式名
		 * @param speed 加载速度
		 */
		hoverClass: function(className, speed){
			var _className = className || "hover";
			return this.each(function(){
				var $this = $(this), mouseOutTimer;
				$this.hover(function(){
					if (mouseOutTimer) clearTimeout(mouseOutTimer);
					$this.addClass(_className);
				},function(){
					mouseOutTimer = setTimeout(function(){$this.removeClass(_className);}, speed||10);
				});
			});
		},
		/**
		 * 聚焦样式处理
		 * @param className{String}样式名
		 */
		focusClass: function(className){
			var _className = className || "textInputFocus";
			return this.each(function(){
				$(this).focus(function(){
					$(this).addClass(_className);
				}).blur(function(){
					$(this).removeClass(_className);
				});
			});
		},
		inputPlaceHolder: function(){
			if(!$.browser.msie || parseInt($.browser.version,10) > 9){
				return;
			}
			return this.each(function(){
				var $this = $(this);
				function getPlaceHolderBox(){
					return $this.parent().find("label.placeholder");
				}
				function placeHolderBoxCss(opacity){
					var position = $this.position();
					return {
						width:$this.width(),
						top:position.top+'px',
						left:position.left +'px',
						opacity:opacity ||0.3
					};
				}
				if (getPlaceHolderBox().size() < 1) {
					if (!$this.attr("id")) $this.attr("id", $this.attr("name") + "_" +Math.round(Math.random()*10000));
					var $label = $('<label class="placeholder" for="'+$this.attr("id")+'">'+$this.attr("placeholder")+'</label>').appendTo($this.parent());

					$label.css(placeHolderBoxCss(0.3));
					if ($this.val()) $label.hide();
				}
				$this.focus(function(){
					getPlaceHolderBox().css(placeHolderBoxCss(0.3));
				}).blur(function(){
					if (!$(this).val()) getPlaceHolderBox().show().css("opacity","0.3");
				}).keydown(function(){
					getPlaceHolderBox().hide();
				});
			});
		},
		/**
		 * 判断当前元素是否已经绑定某个事件
		 * @param {Object} type
		 */
		isBind:function(type) {
			var _events = $(this).data("events");
			return _events && type && _events[type];
		},
		/**
		 * 取得dom元素的指定数值属性值
		 * @param pre{string} 数值属性一般为left,width等
		 * @param {number} 属性值
		 */
		cssNum:function(pre){
			var cssPre = $(this).css(pre);
			return cssPre.substring(0, cssPre.indexOf("px")) * 1;
		},
		
		/**
		 * 判断两个jquery对象是否相等
		 * @param obj2{jqueryObject} jquery对象
		 */
		equalObject:function(obj){
			var $this = (this);
			if($this.length == 0 && (obj == null||obj.length==0))
				return true;
			if(obj == null)
				return false;
			if($this.length != obj.length)
				return false;
			for(var i = 0; i < obj.length; i++){
				if($this[i] != obj[i])
					return false;
			}
			return true;
		}
	});
	
	
	
	/**
	 * 扩展String方法
	 */
	$.extend(String.prototype, {
		/**
		 * 字符串是否为正整数
		 */
		isPositiveInteger:function(){
			return (new RegExp(/^[1-9]\d*$/).test(this));
		},
		/**
		 * 字符串是否为整数
		 */
		isInteger:function(){
			return (new RegExp(/^\d+$/).test(this));
		},
		/**
		 * 字符串是否为数值型
		 */
		isNumber: function(value, element) {
			return (new RegExp(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(this));
		},
		/**
		 * 去字符串的前后空格
		 */
		trim:function(){
			return this.replace(/(^\s*)|(\s*$)|\r|\n/g, "");
		},
		/**
		 * 当前字符串进行转码
		 */
		trans:function() {
			return this.replace(/&lt;/g, '<').replace(/&gt;/g,'>').replace(/&quot;/g, '"');
		},
		/**
		 * 全部替换字符串
		 * @param os 需要替换字符串正则表达式
		 * @param ns 替换的字符串
		 * @return 替换结果
		 */
		replaceAll:function(os, ns) {
			return this.replace(new RegExp(os,"gm"),ns);
		},
		/**
		 * 模板替换
		 * @param $data 上下文书局
		 * @return 替换结果串
		 */
		evalTm:function($data) {
			if (!$data) return this;
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_{\\\\\\.}]*})","g"), function($1){
				var v = $data[$1.replace(/[{}]+/g, "")];
				return v?v:$1;
			});
		},
		/**
		 * 通过指定元素内容里包含的元素id为上下文的变量名，元素值为变量值进行模板替换
		 * @param _box 模板替换的范围
		 * @return 替换结果
		 */
		evalTmBySelector:function(_box) {
			var $parent = _box || $(document);
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_{\\\\\\.}]*})","g"), function($1){
				var $input = $parent.find($1.replace(/[{}]+/g, ""));
				return $input.size() > 0 ? $input.val() : $1;
			});
		},
		/**
		 * 通过指定元素内容里包含的元素id为上下文的变量名，元素值为变量值进行模板替换
		 * @param _box 模板替换的范围
		 * @return 替换结果
		 */
		evalTmById:function(_box) {
			var $parent = _box || $(document);
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_{\\\\\\.}]*})","g"), function($1){
				var $input = $parent.find("#"+$1.replace(/[{}]+/g, ""));
				return $input.size() > 0 ? $input.val() : $1;
			});
		},
		/**
		 * 通过指定元素内容里包含的元素id为上下文的变量名，元素值为变量值进行模板替换
		 * @param _box 模板替换的范围
		 * @return 替换结果
		 */
		evalTmByAttr:function(t) {
			if(!t)
				return this.toString();
			var $t =$(t);
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_{\\\\\\.}]*})","g"), function($1){
				var attr= $1.replace(/[{}]+/g, "");
				var v = attr=='text'?$t.text():(attr=='value'|| attr=='val')?$t.val()||$t.attr(attr):$t.attr(attr);
				return v || $1;
			});
		},
		/**
		 * 转换模板串
		 */
		evalTemplate:function(el){
			if(this.isFinishedTm())
				return this.toString();
			var $el = $(el)
			,box = $el.attr("evalTarget")
			,finder = $el.attr("evalFinder")
			,parent = $el.attr("evalParent");
			var $box=box?$(box):finder?$(box,$el):parent?$el.parents(parent):$(document);
			var val = this.evalTmById($box);
			val.evalTmBySelector($box);
			val = val.evalTmByAttr($el.attr("evalTarget")||el);
			if (!val.isFinishedTm() && $el.attr("eval-warn")) {
				alert($el.attr("eval-warn"));
				return false;
			}
			return val;
		},
		/**
		 * 模板替换是否完成
		 * @return 布尔值
		 */
		isFinishedTm:function(){
			return !(new RegExp("{[A-Za-z_]+[A-Za-z0-9_]*}").test(this)); 
		},
		/**
		 * 去掉字符串前面指定的字母
		 */
		skipChar:function(ch) {
			if (!this || this.length===0) {return '';}
			if (this.charAt(0)===ch) {return this.substring(1).skipChar(ch);}
			return this;
		},
		/**
		 * 判断是否为合格的密码串
		 */
		isValidPwd:function() {
			return (new RegExp(/^([_]|[a-zA-Z0-9]){6,32}$/).test(this)); 
		},
		/**
		 * 判断是否为邮件地址串
		 */
		isValidMail:function(){
			return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(this.trim()));
		},
		/**
		 * 判断当前串是否为空串
		 */
		isSpaces:function() {
			for(var i=0; i<this.length; i+=1) {
				var ch = this.charAt(i);
				if (ch!=' '&& ch!="\n" && ch!="\t" && ch!="\r") {return false;}
			}
			return true;
		},
		/**
		 * 是否为电话号码
		 */
		isPhone:function() {
			return (new RegExp(/(^([0-9]{3,4}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0-9]{3,4}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/).test(this));
		},
		/**
		 * 是否为url地址
		 */
		isUrl:function(){
			return (new RegExp(/^[a-zA-z]+:\/\/([a-zA-Z0-9\-\.]+)([-\w .\/?%&=:]*)$/).test(this));
		},
		/**
		 * 判断是否为外部地址
		 */
		isExternalUrl:function(){
			return this.isUrl() && this.indexOf("://"+document.domain) == -1;
		},
		/**
		 * 获取汉字拼音首字母串
		 * @return {String}拼音首字母串
		 */
		toInitials:function() {
			return $pinyin.toInitials(this);
		},
		/**
		 * 获取汉字拼音大写首字母串
		 * @return {String}大写的拼音首字母串
		 */
		toUpperInitials:function() {
			return $pinyin.toUpperInitials(this);
		},
		
		/**
		 * 获取汉字拼音串
		 * @param ch{String}拼音字符串
		 * @return {String}汉字拼音串
		 */
		toPinyin:function() {
			return $pinyin.toPinyin(this);
		},
		
		/**
		 * 获取汉字首字母大写的拼音串
		 * @return {String}首字母大写的拼音串
		 */
		toCapPinyin:function() {
			return $pinyin.toCapPinyin(this);
		}
	});
	return $;
});
/**
 * 工具类扩展
 * @depend 依赖框架jquery扩展类
 */
define(["jquery","cas/core"],function ($,$F){
	/**
	 * 定义工具方法
	 */
	$F.utils={
		/**
		 * 收集指定html元素指定属性，生成选项对象
		 */
		collectOptions:function(el,opNames){
			if(!opNames||!jQuery.isArray(opNames))
				return null;
			$el = $(el);
			var options = {};
			for(var i=0; i< opNames.length;i++){
				var val,name = opNames[i];
				if(name.indexOf(":")>-1){
					var t = name.split(":");
					val=$el.attr(t[0]);
					name=t[1];
				}else{
					val = $el.attr(name);
				}
				if(val){
					if(val.isInteger()){
						options[name] = parseInt(val,10);
					}else{
						options[name] = val;
					}
				}
			}
			return options;
		},
		/**
		 * 转换元素属性中的模板变量
		 */
		evalElementAttr:function(el,attrName){
			var $el = $(el),attr=attrName||"href";
			var val = this.attr($el,attr);
			if(!val || val.isFinishedTm())
				return val;
			var val = val.evalTemplate(el);
			return val;
		},
		
		/**
		 * 取得当前元素的指定属性值包括val text html属性
		 */
		attr:function(el,attr){
			if(!attr)
				return null;
			return attr=='text'?$(el).text():(attr=='html'?$(el).html():(attr=='val'||attr=='value'?$(el).val():$(el).attr(attr)))
		},
		/**
		 * 格式化消息串
		 * @param msg{string} 消息模版
		 * @param args{array} 替换变量数组
		 */
		format:function(source,args){
			if ( arguments.length > 2 && args.constructor !== Array  ) {
				args = $.makeArray( arguments ).slice( 1 );
			}
			if ( args.constructor !== Array ) {
				args = [args];
			}
			$.each( args, function( i, n ) {
				source = source.replace( new RegExp( "\\{" + i + "\\}", "g" ), function() {
					return n;
				});
			});
			return source;
		},
		/**
		 * 获取传入日期的月份范围，月份的第一天到最后一天
		 * @param dated{date｝指定日期
		 * @return json｝{begin,end}开始结束日期
		 */
		getMonthRange: function(date){
			var year = date.getFullYear()
				,month = date.getMonth()
				,days = new Date(year, month + 1, 0).getDate()
				,begin = new Date(year,month,1)
				,end = new Date(year,month,days); 
			return {begin:begin,end:end};
		},
		/**
		 * 获取传入年度的范围，年度的第一天到最后一天
		 * @param year
		 * @result {begin,end}
		 */
		getYearRange: function(year){
			var begin = new Date(year,0,1)
				,end = new Date(year,11,31); 
			return {begin:begin,end:end};
		},
		/**
		 * 获取传入日期所在季度的范围，季度第一天和最后一天
		 * @param date
		 * @returns {begin,end}
		 */
		getSeasonRange: function(date){
			var year = date.getFullYear()
				,month = date.getMonth()
				,season = getMonth_Season(month + 1)
				,firstMonth = 0
				,lastMonth = 0;
			switch (season) {
			case 1:
				firstMonth = 1;
				lastMonth = 3;
				break;
			case 2:
				firstMonth = 4;
				lastMonth = 6;
				break;
			case 3:
				firstMonth = 7;
				lastMonth = 9;
				break;
			case 4:
				firstMonth = 10;
				lastMonth = 12;
				break;
			default:
				break;
			}
			var lastDate = new Date(year, lastMonth, 0).getDate()
				,begin = new Date(year, firstMonth - 1, 1)
				end = new Date(year, lastMonth - 1, lastDate);
			return {begin:begin,end:end};
			/**
			 * 返回当前的日期为第几季度
			 * @param date
			 * @returns {Number}
			 */
			function getMonth_Season(month){
				var result = 0;
				if(month <= 3){
					result = 1;
				}else if(month <= 6){
					result = 2;
				}else if(month <= 9){
					result = 3;
				}else if(month <= 12){
					result = 4;
				}
				return result;
			}
		},
		/**
		 * 三位一个逗号隔开的展现
		 * @param n 数值
		 * @param s 保留小数位
		 * @returns {String}
		 */
		formatNumber: function(s, n){
			n = n > 0 && n <= 20 ? n : 2;
			s = parseFloat((s + '').replace(/[^\d\.-]/g, '')).toFixed(n) + '';
			var l = s.split('.')[0].split('').reverse()
				, r = s.split('.')[1]
				, t = '';
			for (var i = 0; i < l.length; i++) {
				t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? ',' : '');
			}
			return t.split('').reverse().join('') + '.' + r;
		},
		/**
		 * 转换为大写金额
		 * @param num 数值
		 * @returns {String}
		 */
		formatChinese: function(num){
			if (isNaN(num))
				return '';
			if(num > Math.pow(10, 12)){
				return '还未处理这么大的值';
			}
			var cn = '零壹贰叁肆伍陆柒捌玖'
				,unit = new Array('拾佰仟', '分角')
				,unit1 = new Array('万亿', '')
				,numArray = num.toString().split('.')
				,start = new Array(numArray[0].length - 1, 2)
			for ( var i = 0; i < numArray.length; i++) {
				var tmp = ''
				for ( var j = 0; j * 4 < numArray[i].length; j++) {
					var strIndex = numArray[i].length - (j + 1) * 4
						,str = numArray[i].substring(strIndex, strIndex + 4)
						,start = i ? 2 : str.length - 1
						,tmp1 = toChinese(str, i);
					tmp1 = tmp1.replace(/(零.)+/g, '零').replace(/零+$/,'');
					tmp = (tmp1 + (tmp1 ? unit1[i].charAt(j - 1) : '')) + tmp;
				}
				numArray[i] = tmp;
			}
			numArray[1] = numArray[1] ? numArray[1] : '';
			numArray[0] = numArray[0] ? numArray[0] + '元' : numArray[0];
			numArray[1] = numArray[1].match(/分/) ? numArray[1] : numArray[1] + '整';
			return numArray[0] + numArray[1];
			/**
			 * 转换为大写
			 * @param num 数字
			 * @param 数字所在索引
			 * @returns {String} 大写值
			 */
			function toChinese(num, index) {
				num = num.replace(/\d/g, function($1) {
					return cn.charAt($1)+ unit[index].charAt(start-- % 4 ? start % 4: -1);
				})
				return num;
			}
		},
		/**
		 * 添加参数到object对象中
		 * @param obj {object对象} json对象
		 * @param parameter {string/object}参数对象
         */
		addParameter:function(obj,parameter){
			var name,value;
			if(typeof parameter == "string"){
				if(parameter.indexOf("=") < 1)
					return;
				var t = parameter.split("=");
				name = t[0];
				value = t[1];
			}else if($.isPlainObject(parameter)){
				name = parameter.name;
				value = parameter.value;
			}
			if(!name || value===null||value===undefined||value==="")
				return;
			var old = obj[name];
			if(old == null){
				obj[name] = value;
			}else if($.isArray(old)){
				old.push(value);
			}else{
				obj[name] = [old,value];
			}
		},

		/**
		 * 解析参数串为json对象，可为a=1&b=2形式也可以是json串
		 * @param param{string,array}字符串
		 * @param defaultValue 默认值
		 * @return {Object} 参数
		 */
		parseParameter:function(param,defaultValue){
			if(param == null)
				return defaultValue;
			var o = this;
			if(typeof param == "string"){
				var params =$.jsonEval(param);
				if(!$.isEmptyObject(params)){
					return params
				}
				var strs = param.split("&");

				$.each(strs,function(i,v){
					o.addParameter(params,v);
				});
				if($.isEmptyObject(params)){
					return defaultValue
				}
				return params;
			}else if($.isArray(param)){
				var params ={};
				$.each(param,function(i,v){
					o.addParameter(params,v);
				});
				if($.isEmptyObject(params)){
					return defaultValue;
				}
				return params;
			}else{
				return param;
			}
		},
		/**
		 * 通过控件值格式化串
		 * @param source 格式化模板
		 * @param args{string/Array} 控件表达式或值数组
		 * @returns {string} 结果串
		 */
		formatValue:function( source, args ) {
			if ( arguments.length === 1 ) {
				return function() {
					var ars = $.makeArray( arguments );
					ars.unshift( source );
					return $F.utils.formatValue.apply( this, ars );
				};
			}
			if ( arguments.length > 2 && args.constructor !== Array  ) {
				args = $.makeArray( arguments ).slice( 1 );
			}
			if ( args.constructor !== Array ) {
				args = [args];
			}
			$.each( args, function( i, n ) {
				source = source.replace( new RegExp( "\\{" + i + "\\}", "g" ), function() {
					var $n = $(n);
					if($n.length > 0)
						return $n.val();
					return n;
				});
			});
			return source;
		},
		/**
		 * 检测当前输入内容的合法性
		 * @param th
		 */
		validInputChar:function(th){
			 if(/[/]/g.test(th.value)){
					$(th).val(th.value.replace(/[/]/g,""));  
			 }
			 if(/[^\u4E00-\u9FA5/a-zA-Z/1-9/]/g.test(th.value)){           
				$(th).val(th.value.replace(/[^\u4E00-\u9FA5/a-zA-Z/0-9]/g,""));  
			} 

		},
		
		/**
		 * 检测当前输入内容的合法性,可以输入特殊符号
		 * @param th
		 */
		validInputSpeChar:function(th){
			 if(/[/]/g.test(th.value)){
					$(th).val(th.value.replace(/[/]/g,""));  
			 }
			 //  支持 - . [] 【】  _ ()（）
			 if(/[^\u4E00-\u9FA5/a-zA-Z/0-9._()\[\]【】（）\-/]/g.test(th.value)){           
				 $(th).val(th.value.replace(/[^\u4E00-\u9FA5/a-zA-Z/0-9._()\[\]【】（）\-/\:\：]/g,""));  
			} 

		},
		
		
		/**
		 * 检测当前输入内容的合法性
		 * @param th
		 */
		validInputNameChar:function(th){
			 if(/[/]/g.test(th.value)){
					$(th).val(th.value.replace(/[/]/g,""));  
			 }
			 if(/[^\u4E00-\u9FA5/a-zA-Z_/1-9+\-\(\)/]/g.test(th.value)){           
				$(th).val(th.value.replace(/[^\u4E00-\u9FA5/a-zA-Z_/0-9+\-\(\)]/g,""));  
			} 

		}
	}
	/**
	 * 扩展jQuery匹配表达式
	 */
	function innerTextExactMatch(elem, text) {
		return (elem.textContent || elem.innerText || $(elem).text() || '').toLowerCase() === (text || '').toLowerCase();
	}
	
	$.expr[':'].innerTextExactMatch = $.expr.createPseudo?
		$.expr.createPseudo(function (text) {
			return function (elem) {
				return innerTextExactMatch(elem, text);
			};
		}) :
		function (elem, i, match) {
			return innerTextExactMatch(elem, match[3]);
		};
	return $F.utils;
});
jQuery.fn.placeholder = function(dispalyName,id){
	var i = document.createElement('input'),
		placeholdersupport = 'placeholder' in i;
	if(true){
		var inputs = jQuery(this);
		inputs.siblings("label[class='placeholder']").css("display","none");
		inputs.each(function(){
			var input = jQuery(this),
				text = dispalyName,
				pdl = 0,
				height = input.outerHeight(),
				width = input.outerWidth();
				if(input.parent("div").hasClass("combobox")){
					placeholder = jQuery('<span class="phTips '+id+'" data-toggle="dropdown">'+text+'</span>');
				}else{
					placeholder = jQuery('<span class="phTips '+id+'" >'+text+'</span>');
				}
			try{
				pdl = input.css('padding-left').match(/\d*/i)[0] * 1;
			}catch(e){
				pdl = 5;
			}
			placeholder.css({'margin-left': -(width-pdl),'height':height,'line-height':height+"px",'position':'absolute', 'color': "#444a4e", 'font-size' : "14px","opacity":"0.3","z-index":"10"});
//			placeholder.click(function(){
//			input.focus();
//		});
//		if(input.val() != ""){
//			placeholder.css({display:'none'});
//		}else{
//			placeholder.css({display:'inline'});
//		}
		placeholder.insertAfter(input);
		$(this).focus(function() {
			$("."+$(this).attr("name")).css({display:'none'});
		});
		$(this).blur(function() {
			$(this).trigger("change");
		});
		$(this).change(function() {
			if ($(this).val() != ""){
				$("."+id).css({display:'none'});
			}else{
				$("."+id).css({display:'inline'});
			}
		});
	});
}
return this;
};
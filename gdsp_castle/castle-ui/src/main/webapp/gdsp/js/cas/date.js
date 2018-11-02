/**
 * 日期本地化初始化方法
 */
define(["cas/core",localeFile,"bootstrap/bootstrap","plugins/datapicker/bootstrap-datetimepicker","link!plugins/datapicker/bootstrap-datetimepicker"],function($F,$lang){
	$.fn.datetimepicker.dates[$lang.locale] = $lang.datetimepicker;
	$.fn.datePicker=function(){
		/**
		 * type--日期框类型，date:日期，datetime日期时间， time：时间
		 * scale--精度，y:年，m:月，d:日，h:小时，i:分
		 * 
	     * 0 or 'hour' for the hour view
	     * 1 or 'day' for the day view
	     * 2 or 'month' for month view (the default)
	     * 3 or 'year' for the 12-month overview
	     * 4 or 'decade' for the 10-year overview. Useful for date-of-birth datetimepickers.
		 */
		$(this).each(function(){
			var $o =$(this)
				,type = $o.attr("type")||"date"
				,scale= $o.attr("scale")
				,sv=2
				,mxv=4
				,miv=2
				,todayBtn=1
				,weekStart=1
				,autoclose=1
				,todayHighlight=1
				,forceParse=1
				,pickerPosition="bottom-left"
				,weekNoSupport="no"
				,seasonSupport="no"
				,daysOfWeekEnabled=$o.attr("daysOfWeekEnabled")||""
				,format=$o.attr("data-date-format")||""
				,startDate=$o.attr("startDate")||""
			    ,endDate=$o.attr("endDate")||""
			    ,formatViewType=$o.attr("formatViewType")||"";
			var config = {};
			var formatRel = "";
			var dateType = "";
			if(type=="datetime"){
				if(scale=="h")
					miv=1;
				else
					miv=0;
			}else if(type=="time"){
				sv=1;
				mxv=1;
				if(scale=="h")
					miv=1;
				else
					miv=0;
			}
			//增加年、季、月、周、日(此处为了扩展日期类型插件选择)
			else if(type=="_year"){
				sv = 4;
				miv = 4;
				formatRel = format==""?'yyyy':format; 
				config['format'] = formatRel;
			}else if(type=="_month"){
				sv=3
				miv=3;
				formatRel = format==""?'yyyy-mm':format; 
				config['format'] = formatRel;
			}else if(type=="_day"){
				formatRel = format==""?'yyyy-mm-dd':format; 
				config['format'] = formatRel;
			}else if(type=="_hour"){
				if(scale=="h"){
					miv=1;
					formatRel = format==""?'yyyy-mm-dd hh:00:00':format; 
				}else{
					miv=0;
					formatRel = format==""?'yyyy-mm-dd hh:ii:ss':format; 
				}
				
				config['format'] = formatRel;
			}else if(type=='_week'){
				formatRel = format==""?'yyyy-mm-dd':format;
				config['format'] = formatRel;
				if(daysOfWeekEnabled!=""){
					var daysOfWeekDisabledList = {};
					var daysOfWeekEnabledMap = {};
					var daysOfWeekEnableds = daysOfWeekEnabled.split(",");
					var i=0;
					for(i=0;i<daysOfWeekEnableds.length;i++){
						daysOfWeekEnabledMap[daysOfWeekEnableds[i]+""]=daysOfWeekEnableds[i]+"";
					}
					
					var daysOfWeekDisabledListStr = "";
					for(i=0;i<7;i++){
						if(daysOfWeekEnabledMap[i+""]==null){
							daysOfWeekDisabledListStr+=i+",";
						}
					}
					if(daysOfWeekDisabledListStr!=""){
						daysOfWeekDisabledListStr="["+daysOfWeekDisabledListStr.substring(0,daysOfWeekDisabledListStr.length-1)+"]";
						config['daysOfWeekDisabled']=eval(daysOfWeekDisabledListStr);
					}
					
				}
				todayBtn = 0;
				weekNoSupport = 'yes';
			}else if(type=='_season'){
				sv = 3;
				miv = 3;
				formatRel = format==""?'yyyy-mm':format; 
				config['format'] = formatRel;
				todayBtn = 0;
				seasonSupport = 'yes';
			}
			else if(scale=="y"){
				sv=4;
				miv=4;
			}else if(scale=="m"){
				sv=3;
				miv=3;
			}
			//日期公式类型
			if(type=='_year'||type=='_season'||type=='_month'||type=='_week'||type=='_day'){
				config["dateType"]= type;
			}else if(scale=="y"){
				config["dateType"]= "_year";
			}else if(scale=="m"){
				config["dateType"]= "_month";
			}else {
				config["dateType"]= "_day";
			}
			config["language"]= $lang.locale;
			config["weekStart"]= weekStart;
			config["todayBtn"]= todayBtn;
			config["autoclose"]= autoclose;
			config["todayHighlight"]= todayHighlight;
			config["startView"]= sv;
			config["maxView"]= mxv;
			config["minView"]= miv;
			config["forceParse"]= forceParse;
			config["pickerPosition"]= pickerPosition;
			config["weekNoSupport"]= weekNoSupport;
			config["seasonSupport"]=seasonSupport;
			config["startDate"]=$('#'+startDate).val();
			config["endDate"]=$('#'+endDate).val();
			config["formatViewType"]=formatViewType;
			
			$o.datetimepicker(config).on("click",function(ev){
				$o.datetimepicker('setStartDate',$('#'+startDate).val());
				$o.datetimepicker('setEndDate',$('#'+endDate).val());
			});
			if($o.hasClass("disabled"))
				$o.datetimepicker("_detachEvents");
		});
		$
	};
	$("body").on("dblclick.datetimepicker.data-api", ".date.datepicker>input:text", function (e) {
//		$(this).parent().datetimepicker("reset");
	});
	return $F;
});

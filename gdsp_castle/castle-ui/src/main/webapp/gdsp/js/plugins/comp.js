//-----DateTypeComboBox 此处js供控件调用 
function changeDateView(obj){
	var dateTypeObj = $(obj).find("input[extpro]");
	var scale = dateTypeObj.attr("scale") ||"";
	var viewTypeMap = eval('('+dateTypeObj.attr("keymap")+')');
	var viewType = viewTypeMap[$(dateTypeObj).val()];
	var inputIds = $(dateTypeObj).attr("extpro"),i=0;
	var weekEnabled = $(dateTypeObj).attr("daysOfWeekEnabled");
	var formatMap = $(dateTypeObj).attr("formatmap")||"";
	var format = null;
	if(formatMap!=""){
		format = eval("("+formatMap+")");
	}
	var dateFormat = format!=null?format[$(dateTypeObj).val()]:"";
	if(inputIds!=null && inputIds.trim()!="" && inputIds.indexOf(",")!=-1){
		var inputIdsArr = inputIds.split(",");
		for(i=0;i<inputIdsArr.length;i++){
			if(inputIdsArr[i].trim()!=""){
				changeSingleDateView(inputIdsArr[i].trim(),viewType,weekEnabled,dateFormat,scale);
			}
		}
	}else{
		alert("ext参数出错：日期ids输入错误!");
		return;
	}
}

//dateInputId->
//viewType->年:_year,月:_month,日:_day 如果需要时继续扩展 
function changeSingleDateView(dateInputId,viewType,weekEnabledStr, dateFormat,scale){
	var value = $("#"+dateInputId).val();
	//处理日期控件外层包裹id
	var dateConId=dateInputId+'_date_editor_div';
	if(viewType==null || viewType==""){
		//alert("日历控件：请设置显示模式！");
		//默认清空
		$("#"+dateInputId).val("");
		$("#"+dateInputId).trigger("keyup");
		return;
	}else{
		var _dateConfig = {};
		_dateConfig['language'] = 'zh_CN';
		_dateConfig['weekStart'] = 1;
		_dateConfig['todayBtn'] = 1;
		_dateConfig['autoclose'] = 1;
		_dateConfig['todayHighlight'] = 1;
		_dateConfig['forceParse'] = 1;
		_dateConfig['pickerPosition'] = "bottom-left";
		_dateConfig['startView'] = 2;
		_dateConfig['maxView'] = 4;
		_dateConfig['minView'] = 2;
		_dateConfig['firstInit'] = 'no';
		_dateConfig["dateType"]= viewType;
		if(viewType=='_year'){
			_dateConfig['startView'] = 4;
			_dateConfig['minView'] = 4;
			_dateConfig['format'] = dateFormat!=""?dateFormat:"yyyy";
		}else if(viewType=='_month'){
			_dateConfig['startView'] = 3;
			_dateConfig['minView'] = 3;
			_dateConfig['format'] = dateFormat!=""?dateFormat:"yyyy-mm";
		}else if(viewType=='_day'){
			//取默认值
			_dateConfig['format'] = dateFormat!=""?dateFormat:"yyyy-mm-dd";
		}else if(viewType=='_week'){
			_dateConfig['format'] = dateFormat!=""?dateFormat:"yyyy-mm-dd";
			if(!weekEnabledStr || weekEnabledStr.trim()==''){
				weekEnabledStr = $("#"+dateConId).attr("daysOfWeekEnabled")||"";
			}
			var weekEnabled = getWeekEnabled(weekEnabledStr);
			if(weekEnabled!=null){
				_dateConfig['daysOfWeekDisabled']=weekEnabled;
			}
			_dateConfig['todayBtn'] = 0;
			_dateConfig['weekNoSupport'] = 'yes';
		}else if(viewType=='_season'){
			_dateConfig['startView'] = 3;
			_dateConfig['minView'] = 3;
			_dateConfig['format'] = dateFormat!=""?dateFormat:"yyyy-mm";
			_dateConfig['todayBtn'] = 0;
			_dateConfig['seasonSupport'] = 'yes';
		}
		else if(viewType=='_hour'){
			if(scale=="h"){
				_dateConfig['minView'] = 1;
				_dateConfig['format'] = dateFormat!=""?dateFormat:"yyyy-mm-dd hh:00:00";
			}else{
				_dateConfig['minView'] = 0;
				_dateConfig['format'] = dateFormat!=""?dateFormat:"yyyy-mm-dd hh:ii:ss";
			}
			
		}else{
			alert("日历控件：没有找到此显示模式（"+viewType+"），若需要请扩展");
			return;
		}
		$("#"+dateConId).datetimepicker("remove");
		$("#"+dateConId).datetimepicker(_dateConfig);
		//处理切换默认值问题 如果存在公式则不许需要处理默认日期
		var formulaMap = {};
		var formulaMapString = $("#"+dateConId).attr('data-formula');
		if(formulaMapString&&formulaMapString!=null && formulaMapString !=''){
			if(formulaMapString.indexOf(":")>-1){
				var formulaMapSplit = formulaMapString.split(/\)\s*,/g);
				var formula=null,formulaPre="",formulaPost="";
				for(var i=0;i<formulaMapSplit.length;i++){
					if(formulaMapSplit[i]!=null && formulaMapSplit[i].trim()!=''){
						formula = formulaMapSplit[i].replace(/\r\n\t/g,'').split(":");
						if(formula!=null&&formula.length==2){
							formulaPre = (formula[0]).trim();
							formulaPost = (formula[1]).trim();
							if(formulaPost.indexOf(")")<0){
								formulaPost +=")"; 
							}
							if(formulaPre!=null && formulaPost!=null){
								formulaMap[formulaPre.trim()] = formulaPost.trim();
							}
						}
					}
				}
			}else{
				//不执行没有频度类别的公式
			}
		}
		var formula = formulaMap[viewType];
		if(!formula || formula=='' || formula==null){
			if(viewType=='_week'){
				//获取本周第一天js
				var weekEnabledInt = 1;
				if(weekEnabledStr.length==1){
					weekEnabledInt = parseInt(weekEnabledStr);
				}
				var weekFirstDate = new Date(value);
				weekFirstDate.setDate(weekFirstDate.getDate()-weekFirstDate.getDay()+weekEnabledInt);
				$("#"+dateConId).datetimepicker("setDate",weekFirstDate);
			}else if(viewType=='_season'){
				$("#"+dateConId).datetimepicker("setDate",getCurSeasonDate());
			}else{
				$("#"+dateConId).datetimepicker("setDate",new Date(value));
			}
		}
		$("#"+dateInputId).trigger("focusin");
	}
}

//设置日期公式
//pattern:取当前日期维度     cur_year cur_month cur_season cur_week cur_day 
//span:跨度   整数 
//daysOfWeek
function setDateFormula(pattern,span,daysOfWeek){
	if(pattern==null || pattern == ""){
		alert("日期公式设置错误setDateFormula("+pattern+","+span+")");
		return null;
	}
	var lowCasePattern = pattern.toLowerCase();
	var curDate = new Date();
	var spanInt = parseInt(span);
	if(lowCasePattern == 'cur_year'){
		curDate.setFullYear(curDate.getFullYear()+spanInt);
	}else if(lowCasePattern == 'cur_season'){
		var curSeasonDate = getCurSeasonDate();
		var seasonMonth = curSeasonDate.getMonth()+3*spanInt;
		curSeasonDate.setMonth(seasonMonth);
		curDate = curSeasonDate;
	}else if(lowCasePattern == 'cur_month'){
		curDate.setMonth(curDate.getMonth()+spanInt);
	}else if(lowCasePattern == 'cur_week'){
		var daysOfWeekInt = 1;
		if(daysOfWeek){
			daysOfWeekInt = paseInt(daysOfWeek);
		}
		var curWeekDate = getCurWeekDate(daysOfWeekInt);
		curWeekDate.setDate(curWeekDate.getDate()+7*spanInt);
		curDate = curWeekDate;
	}else if(lowCasePattern == 'cur_day'){
		curDate.setDate(curDate.getDate()+spanInt);
	}else if(lowCasePattern == 'cur_hour'){
		curDate.setHours(curDate.getHours()+spanInt);
	}else{
		alert("日期公式设置错误setDateFormula("+pattern+","+span+")");
		return null;
	}
	return curDate;
}
//获取当前季末月份
function getCurSeasonDate(){
	var curDate = new Date();
	var curMonth = curDate.getMonth();
	var mod  =  (curMonth+1)%3;
	mod = mod==0?3:mod;
	curDate.setDate(1);
	curDate.setMonth(curMonth+3-mod);
	return curDate;
}
//返回当前周第一天
function getCurWeekDate(day){
	if(day==0){
		day = 7;
	}
	var weekFirstDate = new Date();
	weekFirstDate.setDate(weekFirstDate.getDate()-weekFirstDate.getDay()+day);
	return weekFirstDate;
}
function getWeekEnabled(daysOfWeekEnabled){
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
			return eval(daysOfWeekDisabledListStr);
		}
	}
	return null;
}
function changeDateOptions(comboboxId,options){
	var comboboxObj = $("#"+comboboxId);
	comboboxObj.combobox("setItemsOptions",options);
	changeDateView(comboboxObj);
}

function changeOptions(comboboxId,options){
	var comboboxObj = $("#"+comboboxId);
	comboboxObj.combobox("setItemsOptions",options);
}
//-----end DateTypeComboBox


//开始和结束日期与查询范围联动	
function Linkage(timescope,start_date,end_date){
	$("input[id='"+timescope+"']").change(function() {
	var start = $("input[id='"+start_date+"']");
	var end = $("input[id='"+end_date+"']");
	var scope = $(this).val();
	//为结束日期赋值
	end.val(GetDateStr(0));
	end.trigger("keyup");
	end.trigger("change");
	//为开始日期赋值
	if (scope != "" && scope != "自由查询") {
		if (scope == "今天") {
			start.val(GetDateStr(0));
		} else if (scope == "昨天") {
			start.val(GetDateStr(-1));
			end.val(GetDateStr(-1));
			end.trigger("keyup");
			end.trigger("change");
		} else if (scope == "近一周") {
			start.val(GetDateStr(-6));
		} else if (scope == "近三个月") {
			start.val(get3MonthBefor());
		} else {
			start.val(lastMonthDate());
		}
		start.trigger("keyup");
		start.trigger("change");
	} else {
		start.val("");
		end.val("");
		start.trigger("change");
		end.trigger("change");
	}
	});
}

//获取日期字符串    AddDayCount为获取当前日期之后的日期的天数
function GetDateStr(AddDayCount) { 
	var dd = new Date(); 
	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
	var y = dd.getFullYear(); 
	var m = dd.getMonth()+1;//获取当前月份的日期 
	var d = dd.getDate(); 
	if(d < 10){
		d = "0" + d;
	}
	if(m < 10){
		m = "0" + m;
	}
	return y+"-"+m+"-"+d; 
} 

//修改人：wangxiaolong
//修改时间：2017-4-10
//修改原因：修改近一个月，近3个月显示
//获取近1个月的日期
function lastMonthDate(){
    var Nowdate = new Date();
    var vYear = Nowdate.getFullYear();
    var vMon = Nowdate.getMonth() + 1;
    var vDay = Nowdate.getDate();
　　//每个月的最后一天日期（为了使用月份便于查找，数组第一位设为0）
    var daysInMonth = new Array(0,31,28,31,30,31,30,31,31,30,31,30,31);
    if(vMon==1){
        vYear = Nowdate.getFullYear()-1;
        vMon = 12;
    }else{
        vMon = vMon -1;
    }
　　//若是闰年，二月最后一天是29号
    if(vYear%4 == 0 && vYear%100 != 0){
        daysInMonth[2]= 29;
    }
    if(daysInMonth[vMon] < vDay){
        vDay = daysInMonth[vMon];
    }
    if(vDay < 10){
    	vDay = "0" + vDay;
	}
	if(vMon < 10){
		vMon = "0" + vMon;
	}
	return vYear+"-"+vMon+"-"+vDay; 
}
//获取近3个月的日期
function get3MonthBefor(){
    var resultDate,year,month,date;
    var currDate = new Date();
    year = currDate.getFullYear();
    month = currDate.getMonth()+1;
    date = currDate.getDate();
    switch(month)
    {
      case 1:
      case 2:
      case 3:
        month += 9;
        year--;
        break;
      default:
        month -= 3;
        break;
    }
    month = (month < 10) ? ('0' + month) : month;
    date = (date < 10) ? ('0' + date) : date;
    resultDate = year + '-'+month+'-'+date;
  return resultDate;
}



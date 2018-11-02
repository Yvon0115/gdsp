
function submitBusilogQuery(formid, tablerloaderid) {
	// 取得 id 为form1 的 form 的所有输入变量  
//	var values = $("#"+formid).serializeArray();
//	var  index, key, params;
//	for (index = 0; index < values.length; ++index) {
//		key = values[index].name;
//		if (key.endWith("_queryitem"))
//		{
//			params =  key.replace("_queryitem","") + ":" + values[index].value + ",";
//		}
//	}

	val1 = $("#p_start_date").val();
	val2 = $("#p_end_date").val();
//	$("#ResAccessPages").dataloader("load");
   $("#"+tablerloaderid).dataloader("load",{"p_start_date":val1, "p_end_date":val2});
}

/**
 * 判断字符串以特定字符串结尾的方法
 */
String.prototype.endWith=function(endStr){
	  var d=this.length-endStr.length;
	  return (d>=0&&this.lastIndexOf(endStr)==d)
}
/**
 * 导出访问日志
 */
function exportBusiLogModel(){
	//获取时间范围的值
	var scopeValue=$("#timeScope").combobox("getValue");
	//获取选择的开始时间
	var startTime = $("#p_start_date").val();
	//获取选择的结束时间
	var endTime = $("#p_end_date").val(); 
	//将参数传到后台
	window.location.href = __contextPath +"/systools/log/doExportBusiLogModel.d?scopeValue="+scopeValue+"&&startTime="+startTime+"&&endTime="+endTime;
}


function initDatePlaceholder() {
	jQuery("input[name='type_label']").placeholder("查询范围","type_label");
	jQuery("input[name='p_start_date']").placeholder("开始日期","p_start_date");
	jQuery("input[name='p_end_date']").placeholder("结束日期","p_end_date");
	Linkage("timeScope_value","p_start_date","p_end_date");
	$("input[id='timeScope_value']").trigger("change");
	
	$("input[id='p_start_date']").click(function(){
		$('#timeScope_value').val("查询范围");
	});
	$("input[id='p_end_date']").click(function(){
		$('#timeScope_value').val("查询范围");
	});
	$(".fa-calendar").click(function(){
		$('#timeScope_value').val("查询范围");
	});
}




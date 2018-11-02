function exportOperationLogModel(){
	//获取用户名
	var userName = $("#uname").val(); 
	//获取时间范围的值
	var scopeValue=$("#timeScope").combobox("getValue");
	//获取选择的开始时间
	var startTime = $("#p_start_date").val();
	//获取选择的结束时间
	var endTime = $("#p_end_date").val(); 
	//获取操作类型
	var opType = $("#opType").combobox("getValue");
	//获取表名
	var tname = $("#opTable").combobox("getValue");
	window.location.href = __contextPath +"/systools/operationlog/doExportOpLogModel.d?userName="+userName+"&&scopeValue="
				+scopeValue+"&&startTime="+startTime+"&&endTime="+endTime+"&&opType="+opType+"&&tname="+tname;
}
function queryshow(){
	$("#searchcontent").toggle();
	$(".querybutton").toggle();
}

function initDatePlaceholder() {
	jQuery("input[name='queryscope_label']").placeholder("查询范围","queryscope_label");
	jQuery("input[name='uname']").placeholder("操作人","uname");
	jQuery("input[name='optype_label']").placeholder("操作类型","optype_label");
	jQuery("input[name='opTable_label']").placeholder("操作表","opTable_label");
	jQuery("input[name='p_start_date']").placeholder("开始日期","p_start_date");
	jQuery("input[name='p_end_date']").placeholder("结束日期","p_end_date");
	
	Linkage("timeScope_value","p_start_date","p_end_date");
	$("input[id='timeScope_value']").trigger("change");
	
	
	//修改人：wangxiaolong
	//修改时间：2017-4-7
	//修改原因：添加联动功能，当点击开始时间，结束时间，查询范围变为自由查询；
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




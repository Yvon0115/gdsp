/**
 * 任务日志
 */

function joblogQuery() {
	val1 = $("#p_start_date").val();
	val2 = $("#p_end_date").val();
	$("#logsContent").dataloader("load", {
		"p_start_date" : val1,
		"p_end_date" : val2
	});
}

function initDatePlaceholder() {
	jQuery("input[name='type_label']").placeholder("查询范围","type_label");
	jQuery("input[id='p_start_date']").placeholder("开始日期","p_start_date");
	jQuery("input[id='p_end_date']").placeholder("结束日期","p_end_date");
	
	//修改人：lijun
	//修改时间：2017-4-10
	//修改原因：添加联动功能，当点击开始时间，结束时间，查询范围变为自由查询；
	Linkage("type_value","p_start_date","p_end_date");
	$("input[id='type_value']").trigger("change");
	$("input[id='p_start_date']").click(function(){
		$('#type_value').val("查询范围");
	});
	$("input[id='p_end_date']").click(function(){
		$('#type_value').val("查询范围");
	});
	$(".fa-calendar").click(function(){
		$('#type_value').val("查询范围");
	});
}


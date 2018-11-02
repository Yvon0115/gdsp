/** 输入框事件 - 联动时间控件 */
function linkageDateinput(){
	// 获取输入时长
	var days = $("#duration").val();
	// 输入校验 - 不允许输入非数字
	if (isNaN(days) || "" == days || " " == days ) {
		$("#duration").val("");
		return;
	}
	// 计算结束日期
	var date = setDateFormula("cur_day",days,false);
	
	var year = date.getFullYear();
	var _month = date.getMonth()+1;
	if (_month < 10) {
		_month = "0"+_month;
	}
	var _day = date.getDate();
	if (_day < 10) {
		_day = "0"+_day;
	}
	var dateStr = year+"-"+ _month+ "-" + _day;
	
	$('#agingEndDate').val(dateStr);
	$('#agingEndDate').trigger("keyup");    // 这一步才算是改变了日期控件的选中值
	
//	var curDate = new Date();
//	$('#agingEndDate_date_editor_div').attr("data-date",dateStr);
//	$('#agingEndDate_date_editor_div').datetimepicker('update');    // 整个控件出问题
/*	
	$('#agingEndDate').datetimepicker('remove');
	$('#agingEndDate').datetimepicker({format:"yyyy-mm-dd",
		language:"zh_CN",    //语言
		autoclose:true,     // 自动关闭
//		startDate:new Date(),    // 开始时间
		minView:2    // 精确度视图
//		viewSelect:2
	});
	*/
	
//	$('#agingEndDate').datetimepicker('reset');
//	$('#startDate').datetimepicker('_setDate',{"date":date9});
//	$('#startDate').datetimepicker('_setDate',{"date":date, "which":"view"});
//	$('#startDate').datetimepicker('update',dateStr);
}
/*
// 日期框事件绑定
$(document).ready(function(){
	$("#agingEndDate").on("input propertychange",function(){
//		linkageDateinput();
		changeRemainDays();
	});
	$("#agingEndDate").on("change",function(){
//		linkageDateinput();
		changeRemainDays();
	});
	$(":button[class='close']").on('click',function(){
		resetAgingLimitCheckbox();
	});
});
*/
//$(document).ready(function(){
//	var dt = new Date(2016);
//	var dt2 = new Date(2016,10);
//	var dt3 = new Date(2016,10,09);
//	
//	console.log(dt);
//	console.log(dt2);
//	console.log(dt3);
//});
/** 日期控件事件 - 联动时长输入框 */
// date linkage input
function changeRemainDays(){
	
	var date = new Date();    // 日期控件值
	var enddate = $("#agingEndDate").val();
	var eArr = enddate.split('-');
	//处理结束日期
	switch (eArr.length) {
		case 1://年
			eDate = new Date(eArr[0]);
			break;
		case 2://年月
			eDate = new Date(eArr[0],parseInt(eArr[1])-1);
			break;
		case 3://年月日
			date = new Date(eArr[0],parseInt(eArr[1])-1,eArr[2]);
			break;
	}
	var curDate = new Date();
	// 向上取整
	var dvalue = Math.ceil( ( date.getTime() - curDate.getTime() ) / 86400000);
	
	$("#duration").val(dvalue);
//	console.log(dvalue);
	
}




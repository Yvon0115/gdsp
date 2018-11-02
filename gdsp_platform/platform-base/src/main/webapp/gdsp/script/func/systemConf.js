/**
 * 根据数据初始化页面<br>
 */
function InitPage(){
	var sysHomeState = document.getElementById("sysHomeStateHidden").value;
	if(sysHomeState != "Y"){
		$("#sysHomeUrlEdit").val("");
		document.getElementById("sysHomeUrlEdit").disabled = true;
	}
}
/**
 * 系统首页设置函数<br>
 * 功能：1、单选按钮选择“不启用”时，设置URL输入框不可用。<br>
 *			 2、设置sysHomeState的值<br>
 */
function setSysHomeState(sysHomeState){
	if(sysHomeState != "Y"){
		$("#sysHomeUrlEdit").val("");
		document.getElementById("sysHomeUrlEdit").disabled = true;
	}else{
		if($("#sysHomeUrlEdit").val()=="")
			{
			$("#sysHomeUrlEdit").val("homepage.d")
			}
		document.getElementById("sysHomeUrlEdit").disabled = false;
	}
	if(null != sysHomeState){
		$("#sysHomeStateHidden").val(sysHomeState);
	}else{
		$("#sysHomeStateHidden").val("N");
	}
}
/**
 * 报表集成多选按钮处理函数<br>
 * 功能：1、初始化加载多选按钮勾选值<br>
 * 2、处理点击勾选后生成reportInts的值<br>
 */
var reportInts = document.getElementById("reportIntsHidden").value;
function setReportInts(doc_code,isChecked){
	if(null != doc_code && isChecked){
		reportInts += doc_code + ",";
	}else if(!isChecked){
		var deleteStr = doc_code  + ",";
		reportInts = reportInts.replace(deleteStr,"");
	}
	$("#reportIntsHidden").val(reportInts);
}
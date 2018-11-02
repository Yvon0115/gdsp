function getSTable(container) {
	// 获取当前table内容
	var stable = $("#stable").simpletable("getTable");
	var data=[];
	$.each(stable,function(i){
		data[i]=stable[i].innerText;
	})
	document.getElementById("tabledate").value=data;
}

function getChecked() {
	// 获取当前选中行数据
	var rowdata = $("#stable").simpletable("getChecked");
	var data=[];
	$.each(rowdata,function(i){
		data[i]=rowdata[i].innerText;
	})
	document.getElementById("rowdata").value=data;
	// 获取当前选中的总行数
	var crow = $("#stable").simpletable("getRows");
	document.getElementById("crow").value=crow;
}
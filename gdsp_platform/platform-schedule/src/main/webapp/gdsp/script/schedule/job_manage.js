/**
 * 任务管理
 */
	
/** 批量删除任务 */
function batchRemove(tableId,groupType){
	// 获取选中的行
	var jobs = $("#"+tableId+" input:checkbox:checked").parent().parent();
	var jobNames = new Array();
	var jobStatus = new Array();
	
	// 取得选中任务的名称和执行状态
	for (var i = 0; i < jobs.length; i++) {
		var tr = jobs[i];
		var jname = tr.children[1].textContent;
		var jstatus = tr.children[3].textContent;
		if (jstatus == "执行中") {
			$F.messager.warn("请先暂停执行中的任务再尝试删除。");
			return;
		}
		jobNames.push(jname);
		jobStatus.push(jstatus);
	}
	
	if (jobNames.length == 0) {
		$F.messager.warn("未选中任何数据！");
		return;
	}else{
		$F.messager.confirm("确定删除选中数据？",{"callback":function(flag){
			if (flag) {
				deleteOp(jobStatus, jobNames, groupType, tableId);    // 删除操作
			}else{
				return;
			}
		}});
	}
	
}

/** 请求后台删除 */
function deleteOp(jobStatus, jobNames, groupType ,tableId){
	var jobNameStr = JSON.stringify(jobNames);
//	alert(names);
//	alert(triggerStatus);
	// 后台验证
	var url = __contextPath;
	
	if ("alert" == groupType) {
		url = url + "/schedule/alert/delete.d";
	}else if("job" == groupType){
		url = url + "/schedule/job/delete.d";
	}
	$.post(url, {
		status : jobStatus,
		name : jobNameStr
	}, function(data) {
		
		$("#"+tableId).dataloader("load");
		
		var ajaxResultObj = $.parseJSON(data);
		var code = data.statuscode;
		if (ajaxResultObj.statusCode == 200) {
			$F.messager.success(ajaxResultObj.message, {
				label : "确定"
			});
		}
	});
}
//测试发起流程  --仅测试用
function startProcess(){
	//ajax提交保存表单
	var $form = $("#leaveForm"),
	submitUrl = $form.attr("action");
	$.post(submitUrl,$form.serializeArray(),
			function(data){//成功回调
				
				if(data instanceof jQuery){
					var params = data.params;
					data.statusCode == "200" && 
					start(params);
				}else{
					var jsonObj = $.parseJSON(data),
					params = jsonObj.params;
					jsonObj.statusCode == "200" && 
					start(params);
				}
		
			});
}

function start(params){
	if(params){
		var deploymentId = params["id"],
		formid = params["formid"],
		leaveDay = params["leaveDay"];
		$.ajax({
			url:__contextPath + "/workflow/leave/startProcess.d?deploymentId="+deploymentId+"&formid="+formid+"&leaveDay="+leaveDay,
			success:function(){
				$("#processContent").dataloader("load");
				$("#mainPanel").tab("show");
				$F.messager.success("操作成功！",{"label":"确定"});
				return;
			}
		});
	}
}

/**
 * 控制返回到后台的result参数的值
 * @param n: 1,同意 2,不同意 3,驳回发起人 4,驳回上一节点
 */
function ModiResult(n){
	$("#result").val(n);
	//$("#optionForm").submit();
	$.ajax({
		url:__contextPath + "/workflow/process/complete.d",
		data :　$("#optionForm").serialize(),
		method : "post",
		async:false,
		dataType : "json",
		success:function(data){
			if(data.statusCode == 200){
				$F.messager.success("保存成功!", {"label" : "确定"});
				if(7==n||8==n||9==n){
					$("#monitorListContent").attr("url",__contextPath + "/workflow/monitor/listData.d");
					$("#monitorListContent").dataloader("load");
					$("#mainPanel").tab("show");
				}else{
					$("#taskContent").attr("url",__contextPath + "/workflow/task/listData.d");
					$("#taskContent").dataloader("load");
					$("#mainPanel").tab("show");
				}
			}
		}
	});
}

function triggerButton(event){
	console.log(event.srcElement || event.target);
	$("button[result]").attr("result");
}

function downloadAttachments(url){
	window.location.href=__contextPath + "/workflow/task/downloadAttachments.d?fileUrl="+url;
}

function restartProcess(){
	var $form = $("form",$("#taskDetail")), 
	url = $form.attr("action"),
	taskId = $("#taskid").val(),
	formid = $("#formid").val();
	if (!url) {
		return;
	}
	$.post(url,$form.serializeArray(),
			function(data){
				data.statusCode == "200" && 
				$.ajax({
					url:__contextPath + "/workflow/process/complete.d?taskId="+taskId+"&result=10&formid="+formid,
					success:function(response){
						$("#taskContent").dataloader("load");
						$("#mainPanel").tab("show");
						$F.messager.success("操作成功！",{"label":"确定"});
						return;
					}
				})
			},"json")
}
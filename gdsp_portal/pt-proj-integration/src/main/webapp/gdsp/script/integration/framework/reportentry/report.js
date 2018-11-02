function selectCognosNode(e) {
	//var path = e.node.attr("report_path");
	var data_source_id=e.node.attr("nodeId");
	var report_path=e.node.attr("report_path");
	$("#reportId").val(data_source_id);
	$("#report_path").val(report_path);
	$("#widgetName").dataloader("cacheParams",{id:data_source_id});
	$("#widgetName").dataloader("load");

}
/**
 * 同步资源
 * @param event
 */
function toSysCognosFolder() {
	var data_source_id=$("#reportId").val();
	var report_path=$("#report_path").val();
	$Ajax.ajaxCall({
		"url" : __contextPath + "/framework/reportentry/toSysReportFolder.d",
		"data" : {
			"data_source_id" : data_source_id
		},
		callback : function(op, json) {
			// 回调函数
			json = json || {};
			var status = json.statusCode;
			if (status == "") {
				//pagereload();
				$Msg.success(json.message);
			}
			setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
				window.location.reload();//页面刷新
			},500);
		}
	});
}
/**
 * 删除资源文件夹验证事件
 */
function deleteResource(){
	var id = $("#reportId").val();
	var report_path=$("#report_path").val();
	if(id ==null || id==""){
		$F.messager.warn("请点击左侧文件夹,选择资源目录。",{"label":"确定","title" : "提示","icon" : "glyphicon glyphicon-exclamation-sign"});
		return;
	}
//	if(report_path=="/"){
//		
//		$F.messager.alert("根节不可以删除！",{"label":"确定","title" : "提示","icon" : "glyphicon glyphicon-exclamation-sign"});
//	}
	$F.messager.confirm("确认删除选中资源目录？",{"callback":function(flag){
		if(flag){
			$.post(__contextPath + "/framework/reportentry/delete.d",{"id":id},function(data){
				var ajaxResultObj = $.parseJSON(data);
				if(ajaxResultObj.statusCode==200){
					$F.messager.alert(ajaxResultObj.message,{"label":"确定"});
					setTimeout("window.location.reload()",1500);
					return;
				}else{
					$F.messager.warn(ajaxResultObj.message,{"label":"确定"});
				}
			});
		}
	}});
}

/**
 * 为path赋值
 * @param e
 */
function selectTemplateNode(e){
	var path=e.link.attr("value")
	var rootPath = $('#rootPath').val();
	var parmaterPath=path.substring(rootPath.length+1,path.length)
	$('#paramterPath').val(parmaterPath)
}
/**
 * 为queryTemplate赋值
 */
function saveTemplate(){
	var parmaterPath=$('#paramterPath').val()
	if(!parmaterPath.match("\.ftl$") && !parmaterPath.match("\.jsp")){
		$F.messager.warn("不能选择文件夹",{"label":"确定","title" : "提示","icon" : "glyphicon glyphicon-exclamation-sign"});
		return;
	}
	$("#widgetForm #queryTemplate_value").val(parmaterPath)
	$("#widgetForm #queryTemplate_label").val(parmaterPath)
}
/**
 * 批量添加指标
 * @param event
 * @param kpiId
 */
function toBatchAddKpi(event, widgetId) {
	$.openModalDialog({
		"href" : __contextPath + "/framework/widgetKpi/toBatchAddKpi.d?widgetId=" + widgetId,
		"data-target" : "#kpiDlg",
		"width": "800px",
		"height": "500px"
	});
	event.preventDefault();
	event.stopPropagation();
}

/**
 * 保存指标
 */
function selectIndexTreeNode(){
	var widget_id=$("#widget_id").val();
	var $node = $("#indexTree li[ckbox=true]>div>a");
	var indexs="";
	$.each($node,function(i){
		indexs += $(this).attr("value") +",";
	});
	var actionUrl=__contextPath +"/framework/widgetKpi/batchSaveKpi.d?indexs="+indexs+"&widget_id="+widget_id
	$.post(actionUrl,function(data){
		var ajaxResultObj = $.parseJSON(data);
		if(ajaxResultObj.statusCode==200){
			$("#kpiDlg").modal('hide');//关闭对话框
			$("#kpiName").dataloader("load");
		}else{
			$F.messager.warn(ajaxResultObj.message,{"label":"确定"});
		}
	});
}
function selectKpiLibNode(event){
	var node =event.node;
	var id = node.id;
	var name = node.text;
	$("#KpiContent").attr("url", __contextPath+"/framework/widgetKpi/queryKpi.d?id="+id);
	$("#KpiContent").dataloader("load");
}





/**
 * 批量添加参数
 * @param event
 * @param kpiId
 */
function toBatchAddParam(event, widget_id) {
	$.openModalDialog({
		"href" : __contextPath + "/framework/widgetParam/toBatchAddParam.d?widget_id=" + widget_id,
		"data-target" : "#ParamDlg",
		"width": "800px",
		"height": "500px"
	});
	event.preventDefault();
	event.stopPropagation();
}
function selectParamLibNode(event){
	var node =event.node;
	var id = node.id;
	var name = node.text;
	$("#ParamContent").attr("url", __contextPath+"/framework/widgetParam/queryParam.d?id="+id);
	$("#ParamContent").dataloader("load");
}





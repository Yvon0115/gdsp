/**
 * 提交form表单
 */
function saveKPIForm(){
	
	$("#kpiForm").submit();
}

/**
 * 重载页面
 */

function reloadPage(){
	
	window.location.reload();
}

/**
 * 关闭编辑对话框
 */
function closeEdit(){
	$("#editDlg").modal('hide');//关闭对话框
}

/**
 * 提交排序框表单
 */
function saveSortForm() {
	$("#sortForm").submit();
	$("#sortDlg").modal('hide');//关闭对话框
}

/**
 * 批量添加指标
 * @param event
 * @param kpiId
 */
function toBatchAddKpi(event, widgetId) {
	$.openModalDialog({
		"href" : __contextPath + "/runqian/kpi/toBatchAddKpi.d?widgetId=" + widgetId,
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
	var actionUrl=__contextPath +"/runqian/kpi/batchSaveKPI.d?indexs="+indexs+"&widget_id="+widget_id
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

/**
 * 编辑页面对话框
 * @param event
 * @param kpiId 字段ID
 */
function toEditKpi(event,id) {
	$.openModalDialog({
		"href" : __contextPath + "/runqian/kpi/edit.d?id=" + id,
		"data-target" : "#editDlg",
		"showCallback" : function() {
			
		}
	});
	event.preventDefault();
	event.stopPropagation();
}

/**
 * 到kpi排序页
 * @param event
 * @param widget_id
 */
function kpiSort(event, widget_id) {
	$.openModalDialog({
		"href" : __contextPath + "/runqian/kpi/toSort.d?widget_id="
				+ widget_id,
		"data-target" : "#sortDlg",
		"showCallback" : function() {
			$("#sort_table tr").click(function() {
				$(this).toggleClass("info");
			});
		}
	});
	event.preventDefault();
	event.stopPropagation();
}


function move_top() {
	var $tr = $("#sort_table").find("tr.info");
	$("#sort_table").prepend($tr);
	$tr.toggleClass("info");
}

function move_up() {
	var $tr = $("#sort_table").find("tr.info");
	$tr.each(function() {
		if ($(this).index() != 0) {
			$(this).prev().before($(this));
		}
	});
}

function move_down() {
	var $tr = $("#sort_table").find("tr.info");
	var len = $("#sort_table").find("tr").length;
	$tr.each(function() {
		if ($(this).index() != len - 1) {
			$(this).next().after($(this));
		}
	});
}

function move_bottom() {
	var $tr = $("#sort_table").find("tr.info");
	$("#sort_table").append($tr);
	$tr.toggleClass("info");
}


define([ "cas/modal" ], function() {
});

function toSort(event, widget_id) {
	$.openModalDialog({
		"href" : __contextPath + "/widgetmgr/param/toSort.d?widget_id="
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

function submitSortForm(){
	$("#sortForm").submit();
}
function move_up() {
	var $tr = $("#sort_table").find("tr.info");
	$tr.each(function() {
		if ($(this).index() != 0) {
			$(this).prev().before($(this));
		}
	});
}
function move_top() {
	var $tr = $("#sort_table").find("tr.info");
	$("#sort_table").prepend($tr);
	$tr.toggleClass("info");
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

function reload() {
	window.location.reload();
}

/**
 * 到增加字段说明界面
 * @param event
 * @param kpiId 资源ID
 */
function toAddKpi(event, kpiId) {
	$.openModalDialog({
		"href" : __contextPath + "/widgetmgr/kpi/add.d?kpi_id=" + kpiId,
		"data-target" : "#sortDlg",
		"showCallback" : function() {
			$("#sort_table tr").click(function() {
				$(this).toggleClass("info");
			});
		},
		"width":"700px"
	});
	event.preventDefault();
	event.stopPropagation();
}
/**
 * 到编辑字段说明界面
 * @param event
 * @param kpiId 字段ID
 */
function toEditKpi(event, kpiId,id) {
	$.openModalDialog({
		"href" : __contextPath + "/widgetmgr/kpi/edit.d?id=" + id + "&kpi_id=" + kpiId,
		"data-target" : "#sortDlg",
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
function toKpiSort(event, widget_id) {
	$.openModalDialog({
		"href" : __contextPath + "/widgetmgr/kpi/toSort.d?widget_id="
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

function exportKpi(){
	var $node = $("div[id='exportTreePanelId'] li[ckbox='true'] a");
	var resource_id = "";
	$.each($node,function(i){
		resource_id += $(this).attr("value") + ",";
	});
	var url = __contextPath +"/widgetmgr/kpi/doExportKpi.d?commonDirId=" +resource_id;
	window.location=url;
	$("#mainPanel").tab("show");
}

function submitImportFile(){
	$("importForm").submmit();
	return false;
//	 $("#uploadButton").click(function () {
//		    var filename = $("#excelFile").val();
//		    $.ajax({
//		      type: "POST",
//		      url: "addFile.do",
//		      enctype: 'multipart/form-data',
//		      data: {
//		        file: filename
//		      },
//		      success: function () {
//		        alert("Data Uploaded: ");
//		      }
//		    });
//	});
}

function batchImpParamFromParamLibrary(event, widget_id)
{
	$Msg.confirm("是否引入选中参数?", {
		"callback" : function(ok) {
			if (!ok) {
				return;
			}
			var $node = $("ul[id='dirTree'] li[ckbox='true']>div>a,ul[id='dirTree'] li[ckbox='true']>a");
			var paramsids = "";
			$.each($node,function(i){
				paramsids += $(this).attr("value") + ",";
			});
			
			$Ajax.ajaxCall({
				"url" : __contextPath + "/widgetmgr/param/batchImpParamFromParamLibrary.d",
				"data" : {
					"paramsids" : paramsids,
					"widget_id":widget_id
				},
				callback : function(op, json) {
					// 回调函数
					json = json || {};
					var status = json.statusCode;
					if (status == $F.statusCode.ok) {
						 $("#paramName").dataloader("cacheParams",{id:widget_id});
					      $("#paramName").dataloader("load");
						//$("#paramName").dataloader("load",{id:widget_id});
					}
				}
			});
		}
	});
	event.preventDefault();
	event.stopPropagation();

}
/**
 * 批量添加指标
 * @param event
 * @param kpiId
 */
function toBatchAddKpi(event, kpiId) {
	$.openModalDialog({
		"href" : __contextPath + "/widgetmgr/kpi/toBatchAddKpi.d?kpi_id=" + kpiId,
		"data-target" : "#kpiLibDlg",
		"showCallback" : function() {
			$("#sort_table tr").click(function() {
				$(this).toggleClass("info");
			});
		},
		"width": "800px",
		"height": "500px"
	});
	event.preventDefault();
	event.stopPropagation();
}

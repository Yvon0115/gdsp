function selecfavoritiestNode(e){
	var id =e.link.attr("value");
	$("#dirId").val(id);
	  $("#favoritesContent").dataloader("cacheParams",{id:id});
      $("#favoritesContent").dataloader("load");
}

function selectFileNode(e){
	var id =e.link.attr("value");
	$("#dirId").val(id);
}

function deleteDir(event) {
	
	var dirId = $("#dirId").val();
	if(dirId=="" || dirId==null){
		$F.messager.alert("请选中文件夹后点击删除按钮。",{"label":"确定"});
		return;
	}
	$Msg.confirm("是否删除当前文件夹?", {
		"callback" : function(ok) {
			if (!ok) {
				return;
			}
			$Ajax.ajaxCall({
				"url" : __contextPath + "/portal/favorites/deleteDir.d",
				"data" : {
					"id" : dirId
				},
				callback : function(op, json) {
					// 回调函数
					json = json || {};
					var status = json.statusCode;
					if (status == $F.statusCode.ok) {
						window.location.reload();
					}
				}
			});
		}
	});
	event.preventDefault();
	event.stopPropagation();
}

function editDir(){
	var dirId = $("#dirId").val();
	if(dirId=="" || dirId==null){
		$F.messager.alert("请选中文件夹后点击编辑按钮。",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/portal/favorites/editDir.d?id="+dirId);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
//	divCognosTreeSwith("treeDiv");
}
/**
 * 选择父节点时，赋值给隐藏域
 * @param e
 */
function selectParentNode(e){
	var id =e.link.attr("value");
	var name = e.link.text();
	$("#refParentId").val(id);
	$("#refParentName").val(name);
}
/**
 * 回显父菜单ID和名称
 */
function saveParentNode(){
	$("#favoritesForm #pid_label").val($("#refParentName").val());
	$("#favoritesForm #pid_value").val($("#refParentId").val());
}
function saveFileParentNode(){
	$("#FileForm #pid_label").val($("#refParentName").val());
	$("#FileForm #pid_value").val($("#refParentId").val());
}

function tofavoriteSort(event) {
    var pid=$("#dirId").val();
	$.openModalDialog({
		"href" : __contextPath + "/portal/favorites/toSort.d?pid="
				+ pid,
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

/**
 * 关闭排序对话框
 */
function closeSortDlg(){
	$("#sortDlg").modal('hide');
}


/**
 * 关闭收藏资源的对话框
 */
function afterSaveFavorites(){
	$("#favoritesDlg").modal('hide');
}


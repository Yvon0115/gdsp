function selectParamLibNode(e){
	var id =e.link.attr("value");
	var name = e.link.text();
	$("#name").val(name);
	$("#dirId").val(id);
	 $("#param_libContent").dataloader("cacheParams",{id:id});
     $("#param_libContent").dataloader("load");
}

function selectParamLibSubNode(e){
	var id =e.link.attr("value");
	$("#dirId").val(id);
}

function addParamLibDir(){
	var dirId = $("#dirId").val();
	var name = $("#name").val();
	$("#detailPanel").attr("href",__contextPath +"/portal/params_library/addDir.d?id="+dirId+"&name="+encodeURIComponent(name));	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}

function deleteParamLibDir(event) {
	var dirId = $("#dirId").val();
	if(dirId=="" || dirId==null){
		$F.messager.warn("请选中文件夹后点击删除按钮。",{"label":"确定"});
		return;
	}
	$Msg.confirm("是否删除当前文件夹?", {
		"callback" : function(ok) {
			if (!ok) {
				return;
			}
			$Ajax.ajaxCall({
				"url" : __contextPath + "/portal/params_library/deleteDir.d",
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

/**
 * 添加参数
 */
function addParamLibrarySub(){
	var dirId = $("#dirId").val();
	if(dirId=="" || dirId==null){
		$F.messager.warn("请选择添加参数所在分类。",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/portal/params_libsub/add.d?pid="+dirId);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}
function editParamLibDir(){
	var dirId = $("#dirId").val();
	if(dirId=="" || dirId==null){
		$F.messager.warn("请选中文件夹后点击编辑按钮。",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/portal/params_library/editDir.d?id="+dirId);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
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
	$("#ParamLibraryForm #pid_label").val($("#refParentName").val());
	$("#ParamLibraryForm #pid_value").val($("#refParentId").val());
}

function toParamLibrarySort(event) {
	var dirId = $("#dirId").val();
	$.openModalDialog({
		"href" : __contextPath + "/portal/params_library/toSort.d?pid="
				+ dirId,
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

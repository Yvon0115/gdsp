define([ "cas/modal" ], function() {
	var funcs = {
			selectNode: function(e){
				var id =e.node.id;
				var type=e.node.text;
				$("#type").val(type);
				$("#dirId").val(id);
				$("#widgetmetaContent").dataloader("cacheParams",{id:id});
				$("#widgetmetaContent").dataloader("load");
			},
			//click="resManagerPageJS.deleteResource('${row.id}')"
			deleteResource: function(id){
				if(id=="" || id==null){
					$F.messager.alert("删除失败，请刷新页面后重新删除",{"label":"确定"});
					return;
				}
				var dirId = $("#dirId").val();
				$F.messager.confirm("确定删除吗？",{"callback":function(flag){
					if(flag){
						$.ajax({
							type: "POST",
						    url: __contextPath + "/appcfg/resourceManage/deleteWidgetmeta.d",
						    data: "id="+id,
						    success: function(ajaxResult){
						    	var ajaxResultObj = $.parseJSON(ajaxResult);
						    	if(ajaxResultObj.statusCode==300){
						    		$F.messager.alert(ajaxResultObj.message,{"label":"确定"});
						    		return;
						    	}
						    	if(ajaxResultObj.statusCode==200){
						    		$F.messager.alert(ajaxResultObj.message,{"label":"确定"});
						    	}
						    	$("#widgetmetaContent").dataloader("load",{id:dirId});
						    },
						    error: function(){
						    	$F.messager.alert("删除失败，请联系管理员。",{"label":"确定"});
						    }
						});
					}
				},
				title : "确认",
				icon : "glyphicon glyphicon-exclamation-sign"
				});
			},
			validateSortnum : function(th){
				if(/[/]/g.test(th.value)){
					$(th).val(th.value.replace(/[/]/g,""));  
				 }
				 if(/[^0-9]/g.test(th.value)){
					$(th).val(th.value.replace(/[^0-9]/g,""));  
				 } 
			}
	};
	return funcs;
});

function toAddWidget(){
	var dirId = $("#dirId").val();
	var type = encodeURI($("#type").val());
	if(dirId ==null || dirId==""){
		$F.messager.warn("请选择资源目录。",{"label":"确定","title" : "提示","icon" : "glyphicon glyphicon-exclamation-sign"});
		return;
	}
	$("#widgetmetaPanel").attr("href",__contextPath +"/appcfg/resourceManage/addWidgetmeta.d?dirId="+dirId+"&type="+type);	
	$("#widgetmetaPanel").attr("reload","true");
	$("#widgetmetaPanel").tab("show");
}

function deleteCommonDir(){
	var dirId = $("#dirId").val();
	if(dirId=="" || dirId==null){
		$F.messager.warn("请选中资源目录后点击删除按钮。",{"label":"确定"});
		return;
	}
	$F.messager.confirm("确认删除选中资源目录？",{"callback":function(flag){
		if(flag){
			$.post(__contextPath + "/appcfg/resourceManage/deleteCommonDir.d",{"id":dirId},function(data){
				if(data=="\"Y\""){
					$F.messager.alert("操作成功。",{"label":"确定"});
					window.location.reload();
				}else{
					$F.messager.alert("操作失败，请确认该资源目录为空。",{"label":"确定"});
				}
			});
		}
	}});
}

function editCommonDir(){
	var dirId = $("#dirId").val();
	if(dirId=="" || dirId==null){
		$F.messager.warn("请选中资源目录后点击编辑按钮。",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/appcfg/resourceManage/editCommonDir.d?id="+dirId);	
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
	$("#resDirForm #parent_id_label").val($("#refParentName").val());
	$("#resDirForm #parent_id_value").val($("#refParentId").val());
}


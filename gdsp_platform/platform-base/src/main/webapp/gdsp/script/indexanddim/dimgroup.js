/**
 * 选择父节点时，赋值给隐藏域
 * @param e
 */
function selectParentNode(e){
	var code =e.link.attr("value");
	var name =e.link.text();
	$("#innercode").val(code);
	$("#parentname").val(name);

}

/**
 * 回显父维度组ID和名称
 */
function saveParentNode(e){
	var code = $("#innercode").val();
	var name = $("#parentname").val();
	var v = {value:[code],text:[name]};
	$.closeReference(v);
}
function addGroup(){
	var groupId = $("#groupId").val();
	var groupName = $("#groupName").val();
	var innercode=$("#innercode").val();
	$("#detailPanel").attr("href",__contextPath +"/indexanddim/dimgroup/addDimGroup.d?id="+groupId+"&groupname="+encodeURIComponent(groupName)+"&innercode="+innercode);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}

function changeGroupNode(innercode,id,groupname){	
	$("#groupId").val(id);
	$("#groupName").val(groupname);
	$("#innercode").val(innercode);
	$("#dimListContent").attr("url", __contextPath+"/indexanddim/dimgroup/reloadDim.d?dimGroupId="+id+"&innercode="+innercode);
	$("#dimListContent").dataloader("load");
}
function editGroup(){
	var groupId = $("#groupId").val();
	if(groupId){
		$("#detailPanel").attr("href",__contextPath +"/indexanddim/dimgroup/editDimGroup.d?id="+groupId);	
		$("#detailPanel").attr("reload","true");
		$("#detailPanel").tab("show");
	}else{
    	$F.messager.warn("未选中任何数据。",{"label":"确定"});
    }
}

function delGroup(){
	var groupId = $("#groupId").val();
	var groupName = $("#groupName").val();
	if(groupId=="" || groupId==null){
		$F.messager.warn("未选中任何数据。",{"label":"确定"});
		return;
	}
	$("#groupId").val("");
	$("#groupName").val("");
	$F.messager.confirm("确定删除分组\""+groupName+"\"吗？",{"callback":function(flag){
		if(flag){
			$.ajax({
				type: "POST",
				url: __contextPath + "/indexanddim/dimgroup/deleteDimGroup.d",
				data: "id="+groupId,
				success:function(ajaxResult){
					var ajaxResultObj = $.parseJSON(ajaxResult);
					if(ajaxResultObj.statusCode==300){
			    		$F.messager.alert(ajaxResultObj.message,{"label":"确定"});
			    		return;
			    	}
			    	if(ajaxResultObj.statusCode==200){
			    		$F.messager.alert(ajaxResultObj.message,{"label":"确定"});
			    		window.location.reload();
			    		return;
			    	}
				},
				 error: function(){
				    	$F.messager.alert("删除失败，请联系管理员。",{"label":"确定"});
				    }
			});
		}
	}});
}
function addDim(){
	var dimGroupId = $("#groupId").val();
	if (dimGroupId =="") {
		$F.messager.warn("请选择维度分组！",{"label":"确定"});
		return;
	}
	$("#addDimPanel").attr("href",__contextPath +"/indexanddim/dimgroup/addDim.d?dimGroupId="+dimGroupId);	
	$("#addDimPanel").attr("reload","true");
	$("#addDimPanel").tab("show");
}



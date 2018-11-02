
function toEdit(){
//	var a=$(".selected a").attr("value");
	var id = $("#indGroupId").val();
	  if(id==''){
		  $F.messager.warn("未选中任何数据。",{"label":"确定"}); 
		  return;
	  }
	  $("#detailPanel").attr("href",__contextPath +"/indexanddim/indexgroup/editGroup.d?id="+id);	
	  $("#detailPanel").attr("reload","true");
	  $("#detailPanel").tab("show");
	}

function saveParentNode(){
	$("#indGroupAddForm #innercode_label").val($("#refParentName").val());
	$("#indGroupAddForm #innercode_value").val($("#refParentId").val());
}

function selectParentNode(e){
	var id =e.node.id;
	var name = e.node.text;
	$("#refParentId").val(id);
	$("#refParentName").val(name);
}
function selectNode(e){
	var id=e.node.id;
	var name=e.node.text;
	$("#indGroupId").val(id);
//	 $.ajax({
//         url:__contextPath + "/indexanddim/indexgroup/listData.d?groupId="+id+"",
//         type: "get",
//         success: function (returnValue) {
//             
//         },
//         error: function (returnValue) {
//             alert("对不起！数据加载失败！");
//         }
//     });
	$("#indsContent").attr("url", __contextPath + "/indexanddim/indexgroup/listData.d?groupId="+id);
	$("#indsContent").dataloader("load");	
}
function toDelete(){
//	var id=$(".selected a").attr("value");
	var id = $("#indGroupId").val();
	if(id=="" || id==null){
		$F.messager.warn("未选中任何数据。",{"label":"确定"});
		return;
	}

	$F.messager.confirm("确认删除该指标组吗？",{"callback":function(flag){
		if(flag){
			$.ajax({
				type: "POST",
			    url: __contextPath + "/indexanddim/indexgroup/deleteGroup.d",
			    data: "id="+id,
			    success: function(ajaxResult){
			    	var ajaxResultObj = $.parseJSON(ajaxResult);
			    	if(ajaxResultObj.statusCode==300){
			    		$F.messager.warn(ajaxResultObj.message,{"label":"确定"});
			    		return;
			    	}
			    	if(ajaxResultObj.statusCode==200){
			    		window.location.reload();
			    		return;
			    	}
			    },
			    error: function(){
			    	$F.messager.warn("删除失败，请联系管理员",{"label":"确定"});
			    }
			});
		}
	}});

}
function addIndex(){
	var groupId=$("#groupId").val();
	if(groupId=="" || groupId==null){
		$F.messager.warn("请选择指标组。",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/indexanddim/indexgroup/addIndex.d?groupId="+groupId);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}



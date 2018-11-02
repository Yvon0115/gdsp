
function multiDistribute(id){
	var ids = [];
	if(!id){
		ids = getCheckedIds();
		if(ids.length <= 0){
			$F.messager.info("未选中用户数据，无法分配！",{"label":"确定"});
			return;
		}
	}else{
		ids.push(id);
	}
	
	$.openModalDialog({
		href:__contextPath+"/user/distributeOrg/distributeOrg.d?ids="+ids
		,title:"机构分配框"
		,height:"100px"
		,showCallback:function(){
			
		}
	});
}

function selectNode(e){
	var treeId = e.node.id;
	$("#allocateTreeId").val(treeId);
}

function saveAllocate(){
	var ids = $("#userIds").val();
	var allocateTreeId = $("#allocateTreeId").val();
	$.closeDialog();
	$.post(__contextPath+"/user/distributeOrg/saveAllocated.d",{"ids":ids,"pk_org":allocateTreeId},function(data){
		if($.jsonEval(data).statusCode == $F.statusCode.ok){
			$F.messager.success("操作成功！");
			$("#userContent").attr("url",__contextPath+"/user/distributeOrg/listData.d");
			$("#userContent").dataloader("load");
		}else{
			$F.messager.error("操作失败！");
		}
	});
}

function getCheckedIds(){
	var ids = [];
	var $checkbox = $("#userContent input:checkbox:checked");
	$checkbox.each(function(i){
		var thisId = $(this).val();
		ids.push(thisId);
	});
	return ids;
}
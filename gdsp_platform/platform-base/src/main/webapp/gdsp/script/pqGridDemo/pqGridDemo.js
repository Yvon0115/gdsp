function edit(){
	var id = $("#grid_array").simplegrid("getCheckedIds");
	if(id==""){
		$F.messager.warn("未选中用户！",{"label":"确定"});
		return;
	}else if(id.length>1){
		$F.messager.warn("暂不支持批量修改！",{"label":"确定"});
		return;
	}else{
		$("#detailPanel").attr("href",__contextPath +"/pqGrid/demo/toedit.d?id="+id);	
		$("#detailPanel").attr("reload","true");
		$("#detailPanel").tab("show");
	}
}
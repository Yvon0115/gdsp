/**
 * 
 */	 

function selectList(id){
	$(".datas").css("background-color","#fff");
	$("#"+id).css("background-color","#e3e3e3");
	$("#dataSourceId").val(id);
	$("#powerDicContent").attr("url", __contextPath +"/datalicense/powerdic/listData.d?dataSourceId="+id);
	$("#powerDicContent").dataloader("load");
}

function addData(){
	var dataSourceId = $("#dataSourceId").val();
	if(dataSourceId == ""){
		$F.messager.warn("未选中数据源",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/datalicense/powerdic/addData.d?dataSourceId="+dataSourceId);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}

function saveData(){
	var dataSourceId = $("#dataSourceId").val();
	$("#powerDicContent").attr("url", __contextPath +"/datalicense/powerdic/savaData.d?dataSourceId="+dataSourceId+"ids="+ids);
	$("#powerDicContent").dataloader("load");
}
function selectNode(event) {
	var node = event.node;
	var pk_sup = node.id;
	$("#productPageContent").attr("url",__contextPath + "/grant/product/listData.d?pk_sup=" + pk_sup);
	$("#productPageContent").dataloader("load");
}
function addProduct(){
	var pk_sup = $("#selSupId").val();
	if(pk_sup==null || pk_sup==""){
		$F.messager.warn("请选择商品所在供应商!",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/grant/product/add.d?pk_sup="+pk_sup);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}
function selectNode(event) {
	var node = event.node;
	var pk_sup = node.id;
	$("#formPanel").loadUrl(__contextPath + "/grant/supplier/listData.d?pk_sup=" + pk_sup);
}
function selectParentNode(e) {
	var node = e.node;
	var pk_sup = node.id;
	var name = e.node.text;
	$("#refParentId").val(pk_sup);
	$("#refParentName").val(name);
}
function saveParentNode() {
	$("#supAddForm #pk_fathersup_label").val($("#refParentName").val());
	$("#supAddForm #pk_fathersup_value").val($("#refParentId").val());
}
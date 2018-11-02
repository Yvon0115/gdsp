/**
 * 选择父节点时，赋值给隐藏域
 * @param e
 */
function selectParentNode(e){
	var id =e.node.id;
	var name = e.node.text;
	$("#refParentId").val(id);
	$("#refParentName").val(name);
}
function saveParentNode() {
	var pk_fatherId = $("#refParentId").val();
	var name = $("#refParentName").val();
	var v = {value:[pk_fatherId],text:[name]};
	$.closeReference(v);
}
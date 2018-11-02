
function selectNode(e){
	var id =e.link.attr("value");
	var name = e.link.text();
	$("#refParentId").val(id);
	$("#refParentName").val(name);
}

function saveNodes(){
	$("#actionForm #widgetid_label").val($("#refParentName").val());
	$("#actionForm #widgetid_value").val($("#refParentId").val());
	$("#canel_action").click();
}

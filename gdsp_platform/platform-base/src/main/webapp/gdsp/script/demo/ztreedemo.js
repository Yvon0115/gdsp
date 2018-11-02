function nodeEvent(event){
	var node = event.node;
	var pk_org = node.id;
	$("#zTree").setFontCss(node,{"color":"red"});
//	$("#pk_org").val(pk_org);
//	$("#formPanel").loadUrl(__contextPath +"/grant/ztree/listDemo.d?orgId="+pk_org);	
//	$("#zTree").zTree.getZTreeObj("zTree").getCheckedNodes(true);
//	var s = $.fn.zTree.getZTreeObj("zTree").getCheckedNodes(true);
}
function nodeEvent2(event){
	var node = event.node;
	var name = node.name;
	console.log("你点了"+name+"了");
//	alert("你点了"+name+"了");
}
function nodeExpand(event){
	var node = event.node;
	var name = node.name;
	console.log("你展开了"+name+"了");
//	alert("你展开了"+name+"了");
}
function nodeCollapsed(event){
	var node = event.node;
	var name = node.name;
	console.log("你折叠了"+name+"了");
//	alert("你折叠了"+name+"了");
}
function nodeSeclcted(event){
	var node = event.node;
	var name = node.name;
	console.log("你选择了"+name+"了");
//	alert("你选择了"+name+"了");
}
function nodeUnselected(event){
	var node = event.node;
	var name = node.name;
	console.log("你取消选择了"+name+"了");
//	alert("你取消选择了"+name+"了");
}
function nodeChecked(event){
	var node = event.node;
	var name = node.name;
	console.log("你选中了"+name+"了");
//	alert("你选中了"+name+"了");
}
function nodeAsyncSuccess(event){
	var node = event.node;
	var name = node.name;
	console.log("你异步加载成功了"+name+"了");
//	alert("你异步加载成功了"+name+"了");
}
function nodeAsyncError(event){
	var node = event.node;
	var name = node.name;
	console.log("你异步加载失败了"+name+"了");
//	alert("你异步加载失败了"+name+"了");
}

function reflesh(){
	$("button[class='btn btn-default btn-buuuu ']").on("click",{"type":"refresh","slient":false,"treeId":"zTree","isAsync":true},$("#zTree").refreshNode);
//	$("#zTree").refreshNode({"type":"refresh","slient":false,"isAsync":true});
}
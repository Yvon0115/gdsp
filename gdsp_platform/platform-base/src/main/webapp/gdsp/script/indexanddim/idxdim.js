
function changeIdxNode(id,leaf) {
	var indexId = id;
	$("#indexId").val(indexId);
	var leaves=leaf
	$("#leaf").val(leaves);
	if(leaves != "N") {
		$("#dimContent").attr("url", __contextPath+"/indexanddim/idxdimrel/listData.d?indexid="+indexId);
		$("#dimContent").dataloader("load");
	}
}

function addDim() {
	var indexId = $("#indexId").val();
	var leaves = $("#leaf").val();
	if(leaves == "N" || leaves == "") {
		$F.messager.warn("请选择待分配指标！",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath+"/indexanddim/idxdimrel/addDim.d?indexId="+indexId);
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}

function selectNode(e){	
	var tableString = e.link.attr("value");	
	var nameString = e.link.context.textContent;
	$.closeReference({value:[tableString],text:[nameString]});
	$("#comedepart").val(nameString);
	$("#comedepart_label").val(nameString);
}

function emportMould(){
	window.location.href = __contextPath +"/indexanddim/idxdimrel/emportMould.d";
}

function redirectMain(){
	window.location.href = __contextPath +"/indexanddim/idxdimrel/list.d";
}

function saveForm(){
	$fileName=$("#excelFile").val();
	$fx = $fileName.substring($fileName.lastIndexOf("."));
	$fx = $fx.toLocaleLowerCase();
	if($fx == '.xls'||$fx == '.xlsx'){
		$("#inportForm").submit();
	}else{
		$F.messager.warn("请选择正确的文件",{"label":"确定"});
		return;
	}
}
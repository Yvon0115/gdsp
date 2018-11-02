// get categoryId
function selectNode(e){
//	var pk_category = e.link.attr("value");	
	var pk_category = e.node.id;
	$("#categoryCode").val(pk_category);
	// 显示单一类别
	$("#processContent").attr("url",__contextPath+"/workflow/process/listData.d?categoryCode="+pk_category);
	$("#processContent").dataloader("load");
}

function openCreateWindon() {	
	var categoryCode = $("#categoryCode").val();
	if(!categoryCode){		
		$F.messager.warn("请先选择类型！",{"label":"确定"});
		return;
	}
	window.open(__contextPath +"/workflow/model/create.d?categoryCode="+categoryCode);
}

function openModifyWindon(id) {	
		window.open(__contextPath +"/workflow/model/getXml.d?id="+id);
	}

function deployProcess(id,deployState) {	
	//deployState 为空：未部署
	if(!deployState){
		$.ajax({
			type : "POST",
			url : __contextPath+"/workflow/model/deploy.d",
			data : {
				"id" : id
			},
			success : function(ajaxResult) {
				var ajaxResultObj = $.parseJSON(ajaxResult);
				$F.messager.success(ajaxResultObj.message, {"label" : "确定"});
				$("#processContent").dataloader("load");
			},
			error : function() {
				$F.messager.warn("流程部署失败！", {"label" : "确定"});
			}
		});
			
	}else{
		$F.messager.warn("已部署的流程不能重复部署！",{"label":"确定"});
		return;
	}
}



function startTest() {	
	$F.messager.warn("未发布或者停用状态的流程不可启用！",{"label":"确定"});
}


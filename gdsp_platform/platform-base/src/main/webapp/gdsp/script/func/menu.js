//define([ "cas/modal","cas/combobox"], function() {
//	var funcs = {
//			/*selectNode : function (e){
//				var id = e.link.attr("value");
//				var menuName = e.link.text();
//				$("#menuId").val(id);
//				$("#menuName").val(menuName);
//				$("#menuDetail").loadUrl(__contextPath +"/func/menu/queryMenuDetail.d?id="+id);
//			}*/
//	}
//	return funcs;
//});

// 节点选择事件
function selectNode(e){
	var id = e.node.id;
	var menuName = e.node.text;
	$F.loadingSwitch(false);
	$("#menuId").val(id);
	$("#menuName").val(menuName);
	$("#menuDetail").loadUrl(__contextPath +"/func/menu/queryMenuDetail.d?id="+id);
}
//详细页面加载时调用该，管理节点和业务节点时显示URL;管理节点、业务节点、菜单节点，安全级别显示 
function validateType(container){
	var value=$("#funtype").combobox("getValue");
	if(value==2 || value==3){//管理节点和业务节点显示URL
		$('.form-group').has('#url').show();
	}else{
		$('.form-group').has('#url').hide();
	}
	if(value==2 || value==3 || value==4){//管理节点、业务节点、菜单节点 显示安全级别字段
		$('.form-group').has('#safeLevel').show();
	}else{
		$('.form-group').has('#safeLevel').hide();
	}
//	if(value==0 || value==4 || value ==""){//一级节点和页面节点不显示父节点
//		$('.form-group').has('#parentid').hide();
//	}else{
//		$('.form-group').has('#parentid').show();
//	}
}
//添加和编辑界面加载时调用
function validateTypeEditForm(container){
	$(container + "-tabpane #innercode").parents(".form-group:first").hide();
	var value=$("#funtypeEditForm").combobox("getValue");
	
	if(value==2 || value==3){//管理节点和业务节点 显示URL字段
		$(container +"-tabpane #url").parents(".form-group:first").show();
	}else{
		$(container +"-tabpane #url").parents(".form-group:first").hide();
		$(container +"-tabpane #url").val("/");
	}
	if(value==2 || value==3 || value==4){//管理节点、业务节点、菜单节点 显示安全级别字段
		$(container +"-tabpane #safeLevel").parents(".form-group:first").show();
	}else{
		$(container +"-tabpane #safeLevel").parents(".form-group:first").hide();
	}
	var value2=$("#funtype").combobox("getValue");
	if(value2==2 || value2==3 || value2==4){//管理节点、业务节点、菜单节点 显示安全级别字段
		$(container +"-tabpane #safeLevel").parents(".form-group:first").show();
	}else{
		$(container +"-tabpane #safeLevel").parents(".form-group:first").hide();
	}
//	if(value==0 || value==4 || value ==""){//一级节点和页面节点不显示父节点
//		$(container +"-tabpane #parentid").parents(".form-group:first").hide();
//	}else{
//		$(container +"-tabpane #parentid").parents(".form-group:first").show();
//	}
}

//添加和修改界面中菜单类型change相应函数，管理节点和业务节点时显示URL;管理节点、业务节点、菜单节点，安全级别显示 
function typeChange(container,obj){
	var value=$(obj).combobox("getValue");
	if(value==2 || value==3){//管理节点和业务节点
		$(container +"-tabpane #url").parents(".form-group:first").show();
//		var a = $(container +"-tabpane #url").attr("validation");
//		alert(a);
		// 切换到管理菜单或业务菜单时，url加上校验
		$(container +"-tabpane #url").val("");
		$(container +"-tabpane #url").attr("validation",'{"required":true}');
	}else{
		$(container +"-tabpane #url").parents(".form-group:first").hide();
		// 菜单目录和其他类型，去除url的校验
		$(container +"-tabpane #url").removeAttr("validation");
		$(container +"-tabpane #url").removeAttr("aria-required");
		$(container +"-tabpane #url").val("");
	}
	// 控制安全级别显示
	if(value==2 || value==3 || value==4){//管理节点和业务节点
		$(container +"-tabpane #safeLevel").parents(".form-group:first").show();
	}else{
		$(container +"-tabpane #safeLevel").parents(".form-group:first").hide();
	}
	
	if(value==4)
	{
		$(container +"-tabpane #parentid #parentid_label").val("");
		$(container +"-tabpane #parentid #parentid_value").val("");
//		$(container +"-tabpane #parentid").attr("disabled","");
		$(container +"-tabpane #parentid").parents(".form-group:first").hide();
	}
	else{
		$(container +"-tabpane #parentid").parents(".form-group:first").show();
		//$(container +"-tabpane #parentid").attr("disabled","disabled");
	}
//	if(value==0 || value==4 || value ==""){//一级节点和菜单节点不显示父节点
//		$(container +"-tabpane #parentid").parents(".form-group:first").hide();
//	}else{
//		$(container +"-tabpane #parentid").parents(".form-group:first").show();
//	}
}
/**
 * 删除菜单
 * @param id
 */
function deleteMenu(id){
	if(id=="" || id==null){
		$F.messager.warn("删除失败，请刷新页面后重新删除",{"label":"确定"});
		return;
	}
	$F.messager.confirm("确定删除菜单吗？",{"callback":function(flag){
		if(flag){
			$.ajax({
				type: "POST",
			    url: __contextPath + "/func/menu/delete.d",
			    data: "id="+id,
			    success: function(ajaxResult){
			    	var ajaxResultObj = $.parseJSON(ajaxResult);
			    	if(ajaxResultObj.statusCode==300){
			    		$F.messager.alert(ajaxResultObj.message,{"label":"确定"});
			    		return;
			    	}
			    	if(ajaxResultObj.statusCode==200){
			    		$F.messager.success(ajaxResultObj.message,{"label":"确定"});
			    		$("#muneRegisterVo").dataloader("load");
//			    		window.location.reload();
//			    		return;
			    	}
			    },
			    error: function(){
			    	$F.messager.warn("删除失败，请联系管理员",{"label":"确定"});
			    }
			});
		}
	}});
	//action=[c.rpc("${ContextPath}/func/menu/delete.d?id=${muneRegisterVo?if_exists.id?if_exists}",{"tab":"#mainPanel"},{"confirm":"确认删除？"})]
	//click="deleteMenu('${muneRegisterVo?if_exists.id?if_exists}')"
}

/**
 * 选择父节点时，赋值给隐藏域
 * @param e
 */
function selectParentNode(e){
	
	/*
	// 老树的取值方式
	var id =e.link.attr("value");
	var name = e.link.text();
	$("#refParentId").val(id);
	$("#refParentName").val(name);
	*/
	
	// 获取所选父节点的id和名称
	var id =e.node.id;
	var name = e.node.text;
	$("#refParentId").val(id);
	$("#refParentName").val(name);
	
}
/**
 * 回显父菜单ID和名称
 */
function saveParentNode(){
	// 赋值给修改/添加菜单  表单
	$("#menuEditForm #parentid_label").val($("#refParentName").val());
	$("#menuEditForm #parentid_value").val($("#refParentId").val());
//	$("#menuForm #parentid_label").val($("#refParentName").val());
//	$("#menuForm #parentid_value").val($("#refParentId").val());
}

function opentab(target){
	
}
/**
 * 添加菜单时，带着左侧被选中的菜单ID和name，赋值给父节点
 */
function addMenu(){
	var id = $("#menuId").val();
	var menuName = $("#menuName").val();
	menuName = encodeURIComponent(menuName);
	$("#detailPanel").attr("href",__contextPath +"/func/menu/add.d?id="+id+"&funname="+menuName);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}

/**
 * 选择父菜单，重新生成内码
 * 
 * 注释原因：
 * 不能为自己，并且不能选也部门首页在sql中进行条件查询处理
 */
function checkParentMenu(id){
	if(id=="" || id==null){
		return;
	}
	//修改父菜单的树
	var list = $('#parentTree').treeview('getSelected');
	
	if(list.length>0){
		var selectParentid = list[0].id;
		var url = __contextPath +"/func/menu/doEditParentMenu.d";
		$.ajax({
			type : "post",
			url : url,
			data : "id="+id+"&parentid="+selectParentid,
			success : function(result){
		    	if(result==0){
		    		$F.messager.success("修改成功！",{"label":"确定"});
		    		$("#muneRegisterVo").dataloader("load");
//		    		window.location.reload();
		    	}else if(result==1){
		    		$F.messager.warn("修改失败，父节点不能为自身，请再次选择！",{"label":"确定"});
		    	}else if(result==2){
		    		$F.messager.warn("修改失败，父节点没变化，请再次选择！",{"label":"确定"});
		    	}
			},
			error : function(){
				$F.messager.error("错误！修改失败，请联系管理员",{"label":"确定"});
			}
		});
	}else{
		$F.messager.warn("请选择要修改的父节点",{"label":"确定"});
	}
	
	
//	var oneself = "";
//	$("div.selected>a").each(function(i){
//		if(i==0){ 
//			oneself = $(this).attr("value");
//		}
//		if(i==1){
//			selectParentid = $(this).attr("value");
//		}
//	});
//	if(selectParentid == oneself){
//		$F.messager.warn("父节点不能为自身，请再次选择！",{"label":"确定"});
//		return;
//	}
	
	
}

/*
 * 修改父菜单时，对是否有子进行判断	
 * 
 */
 
function editParentMenu(id){
	if(id=="" || id==null){
		return;
	}
	var list = $('#menuTree').treeview('getSelected');
	var node = list[0].nodes;
	if(node==undefined){
		$.openModalDialog({
			"href" :__contextPath + "/func/menu/toEditParentMenu.d?id="+id,
			"data-target" : "#editparent",
			"width":"414px",
			"height":"361px",
		});
	}else{
		$F.messager.warn("此菜单下有附属菜单，不能变更",{"label":"确定"});
	}
	
}

function syndispcode(container,obj)
{
	var value=$(obj).val();
	if($(container +"-tabpane #dispcode").val()==""){
	$(container +"-tabpane #dispcode").val(value);}
}
function saveSafeLevelNode()
{
	
	var l=$("input[name='safeLevelID']:checked").length
	if(l>1){
		$F.messager.warn("只能选择一个安全级别");
		return;
	}
	var s='';
	$('input[name="safeLevelID"]:checked').each(function(){
	s=$(this).val();
	});
	var str=s.split(",");
	$("#menuEditForm #safeLevel_label").val(str[1]);
	$("#menuEditForm #safeLevel_value").val(str[0]);
}
function publishExternalMenu(){
	var $node = $F("#selectPublishMenuTree").simpletree("getSelectedNode");
	if($node.length == 0 || !$node.attr("value")) {
		$Msg.warn("请选择上级菜单！");
		return false;
	}else{
		$F("#parentid").val($node.attr("value"));
		$F("#publishForm").submitForm();
	}
}

/**
 * 导出菜单按钮
 */
function exportAllLeafMune(){
	
	var url = __contextPath +"/func/menu/exportAllLeafMune.d";
	window.location=url;
	//关闭弹出窗口
	var ref = $.getCurrentDialog();	
	ref.modal("hide");		
}


// 添加/编辑菜单 - 选择上级节点弹出层
function openMenuModal(){
	$.openModalDialog({
		"href" :__contextPath + "/func/menu/queryParentMenu.d",
		"data-target" : "#aaa",
		"width":"414px",
		"height":"361px",
	});
	
	
}
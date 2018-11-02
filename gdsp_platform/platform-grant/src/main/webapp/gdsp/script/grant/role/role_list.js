/** 添加角色 */
function addRole(){
	var pk_org = $("#selOrgID").val();

	if(pk_org==null || pk_org==""){
		$F.messager.warn("请选择角色所在机构!",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/grant/role/add.d?pk_org="+pk_org);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}

function deleteRole(ids){
	$F.messager.confirm("删除角色会一并删除角色与用户、机构、菜单和页面的关联关系，确定要删除该角色？",{"callback":function(flag){
		if(flag){
			$.ajax({
				type: "POST",
			    url: __contextPath + "/grant/role/delete.d",
			    data: "id="+ids,
			    success: function(ajaxResult){
			    	var ajaxResultObj = $.parseJSON(ajaxResult);
			    	if(ajaxResultObj.statusCode==300){
			    		$F.messager.warn(ajaxResultObj.message,{"label":"确定"});
			    		return;
			    	}
			    	if(ajaxResultObj.statusCode==200){
			    		$F.messager.warn(ajaxResultObj.message,{"label":"确定"});
			    			$("#rolesContent").dataloader("load");
//			    			setTimeout("window.location.reload()",1500);  // 反面教材
//			    			window.location.reload();
			    			return;
			    	}
			    },
			    error: function(){
			    	$F.messager.warn("删除失败，请联系管理员",{"label":"确定"});
			    }
			});
		}
	}});
}

function selectNode(event){
	var node = event.node;
	var pk_org = node.id;
	$("#pk_org").val(pk_org);
	$("#rolesContent").attr("url", __contextPath+"/grant/role/listData.d?pk_org="+pk_org);
	$("#rolesContent").dataloader("load");
}

/** 角色关联机构 */
function saveOrgRole(){
	var checkedOrgIds = $("#checkedOrgIds").val();
	var roleId = $("#roleId").val();
	var orgID = $("#orgID").val();
	var $node = $("div[id='roleOrgListPanel-tabpane'] li[ckbox='true']>a,div[id='roleOrgListPanel-tabpane'] li[ckbox='true']>div>a");
	var resource_id = "";
	$.each($node,function(i){
		resource_id += $(this).attr("value") + ",";
	});
	$.ajax({
		type: "POST",
	    url: __contextPath + "/grant/role/addOrgToRole.d",
	    data:  {
			"roleId" : roleId,
			 "orgID":orgID,
			 "orgIds": resource_id,
			 "checkedOrgIds" : checkedOrgIds
		},
	    success: function(ajaxResult){
	    	var ajaxResultObj = $.parseJSON(ajaxResult);
	    	if(ajaxResultObj.statusCode==300){
	    		$F.messager.warn(ajaxResultObj.message,{"label":"确定"});
	    		return;
	    	}
	    	if(ajaxResultObj.statusCode==200){
	    		$F.messager.success(ajaxResultObj.message,{"label":"确定"});
	    		$.post(__contextPath+"/grant/role/editRoleUser.d",{id:roleId,orgID:orgID},null);
	    	}
	    },
	    error: function(){
	    	$F.messager.warn("保存失败，请刷新后重试",{"label":"确定"});
	    }
	});
}

/** 角色关联菜单 */
function saveMenuRole(){
	var checkedMenuIds = $("#checkedMenuIds").val();
	var roleId = $("#roleId").val();
//	var orgID = $("#orgID").val();
	var $node = $("div[id='roleMenuListPanel-tabpane'] li[ckbox='true']>a,div[id='roleMenuListPanel-tabpane'] li[ckbox='true']>div>a");
	var resource_id = "";
	$.each($node,function(i){
		resource_id += $(this).attr("value") + ",";
	});
	$.ajax({
		type: "POST",
	    url: __contextPath + "/grant/role/addMenuToRole.d",
	    data:  {
			 "roleID" : roleId,
//			 "orgID":orgID,
			 "selectedMenuIds": resource_id,  // 选中的
			 "exsitingMenuIds": checkedMenuIds  // 当前的
		},
	    success: function(ajaxResult){
	    	var ajaxResultObj = $.parseJSON(ajaxResult);
	    	if(ajaxResultObj.statusCode==300){
	    		$F.messager.warn(ajaxResultObj.message,{"label":"确定"});
	    		return;
	    	}
	    	if(ajaxResultObj.statusCode==200){
	    		$F.messager.success(ajaxResultObj.message,{"label":"确定"});
				$.post(__contextPath + "/grant/role/refreshMenuIDsOfRole.d"
				  , {roleID : roleId}
				  , function(data) {
				    	$("#checkedMenuIds").val(data);
				});
	    		return;
	    	}
	    },
	    error: function(){
	    	$F.messager.warn("保存失败，请刷新后重试",{"label":"确定"});
	    }
	});
}

/** 开启和关闭角色时效控制 */
function openAndCloseGrantAging(){
	
	// 启用角色的时效控制
	if ($("#agingLimit").prop("checked")) {
		$("#permissionAging1").prop("readonly",false);
		$("#permissionAging2").prop("readonly",false);
		var sysDefaultAging = $("#sysDefaultAging").val();
		$("#permissionAging1").val(sysDefaultAging);
		$("#permissionAging2").val(sysDefaultAging);
		
		$("#agingUnit").parent().parent().css("display","inline");
	//关闭角色的时效控制
	}else{
		$("#permissionAging1").prop("readonly",true);
		$("#permissionAging2").prop("readonly",true);
		$("#permissionAging1").removeAttr("required");
		$("#permissionAging2").removeAttr("required");
		$("#permissionAging1").val(null);
		$("#permissionAging2").val(null);
		$("#agingUnit").val(null);
		$("#agingUnit").parent().parent().css("display","none");
	}
}

/** 更新角色用户关联关系 */
function updateUsers(roleID,userIDs,date){
	$.post(__contextPath+"/grant/role/updateRelationAging.d",{roleId:roleID,date:date,ids:userIDs},function(data){
		var ajaxResultObj = $.parseJSON(data);
		var code = data.statuscode;
		if (ajaxResultObj.statusCode == 200) {
			$F.messager.success(ajaxResultObj.message,{label:"确定"});
		}
	});
}

/** 保存且只保存角色的时效开启状态，不同于角色保存 */
function saveRoleAgingStatus(roleID,agingLimit){
	$.post(__contextPath + "/grant/role/setRoleAgingStatus.d",{"roleID" : roleID,"agingLimit" : agingLimit});
}
/** 取消时效标选中状态 */
function resetAgingLimitCheckbox(){
	$("#agingLimit").prop({"checked":false});
}

function hiddenEnd_date(){
	$(".form-group:eq(3)").attr("style","display:none;");
}

/** 加载权限时效默认值输入框的样式 兼容IE */
function loadAgingInputStyle(){
	$("div.col-xs-offset-2").attr("style","margin-left:0px;");
}
function validInputChar(th){
	if(/[/]/g.test(th.value)){
		$(th).val(th.value.replace(/[/]/g,""));  
 }
 if(/[^\u4E00-\u9FA5/a-zA-Z/1-9/]/g.test(th.value)){           
	$(th).val(th.value.replace(/[^\u4E00-\u9FA5/a-zA-Z/0-9_]/g,""));  
}
}

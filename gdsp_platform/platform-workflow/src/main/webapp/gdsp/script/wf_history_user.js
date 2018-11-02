// 流程历史-条件查询-用户(引用base)

function addUser(){
	var pk_org = $("#orgID").val();

	if(pk_org==null || pk_org==""){
		$F.messager.warn("请选择用户所在机构!",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/grant/user/add.d?pk_org="+pk_org);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}

function selectNode(e){
	var pk_org = e.link.attr("value");
	$("#orgID").val(pk_org);
}

function transNode(e){
	var transOrg = e.link.attr("value");
	$("#transOrg").val(transOrg);
}

function pagereload() {
	window.location.reload();
}
//save org and user relation
function saveOrgUser()
{
	var userId=$("#userId").val();
	var $node = $("div[id='userOrgListPanel-tabpane'] li[ckbox='true']>a,div[id='userOrgListPanel-tabpane'] li[ckbox='true']>div>a");
	var resource_id = "";
	$.each($node,function(i){
		resource_id += $(this).attr("value") + ",";
	});
	var url = __contextPath +"/grant/user/addOrgToUser.d?userId="+userId+ "&orgIds=" +resource_id;
	$("button[href='saveOrgUserTree']").attr("href",url);
	}
function saveMenuUser()
{
	var userId=$("#userId").val();
	var $node = $("div[id='userMenuListPanel-tabpane'] li[ckbox='true']>a,div[id='userMenuListPanel-tabpane'] li[ckbox='true']>div>a");
	var resource_id = "";
	$.each($node,function(i){
		resource_id += $(this).attr("value") + ",";
	});
	var url = __contextPath +"/grant/user/addMenuToUser.d?userId="+userId+ "&menuIds=" +resource_id;
	$("button[href='saveMenuUserTree']").attr("href",url);
}

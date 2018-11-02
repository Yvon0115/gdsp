function exportUserRoleModel(){
	//获取用户名、账号、所属条件输入参数
	var accountQueryParam = $("#uaoParam").val();
	//获取角色名的输入参数
	var roleParam = $("#rname").val();
	window.location.href = __contextPath +"/systools/authoritylog/doExportUserRoleModel.d?accountQueryParam="+accountQueryParam+"&&roleParam="
				+roleParam;
}

function exportRoleFuncModel(){
	//获取角色名输入参数
	var roleParam = $("#rolename").val();
	//获取功能名输入参数
	var funcParam = $("#funcname").val();
	window.location.href = __contextPath +"/systools/authoritylog/doExportRoleFuncModel.d?roleParam="+roleParam+"&&funcParam="
				+funcParam;
}

function exportUserFuncModel(){
	//获取用户名、账号、所属条件输入参数
	var accountQueryParam = $("#uaoParameter").val();
	//获取角色名的输入参数
	var funcParam = $("#functionname").val();
	window.location.href = __contextPath +"/systools/authoritylog/doExportUserFuncModel.d?accountQueryParam="+accountQueryParam+"&&funcParam="
				+funcParam;
}

function exportRoleDataModel(){
	//获取角色名输入参数
	var roleParam = $("#rlname").val();
	//获取数据维度/数据维度值的输入参数
	var dataParam = $("#datalimited").val();
	window.location.href = __contextPath +"/systools/authoritylog/doExportRoleDataModel.d?roleParam="+roleParam+"&&dataParam="
				+dataParam;
}

function exportUserDataModel(){
	//获取用户名、账号、所属条件输入参数
	var accountQueryParam = $("#usaoParam").val();
	//获取角色名的输入参数
	var dataParam = $("#dataauthority").val();
	window.location.href = __contextPath +"/systools/authoritylog/doExportUserDataModel.d?accountQueryParam="+accountQueryParam+"&&dataParam="
				+dataParam;
}
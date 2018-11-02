<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/gdsp/common/global.jsp"%>

<script type="text/javascript">
<!--
jq(function(){
	var userIds = '';
	var groupIds = '';
	var roleIds = '';
	var orgIds = '';
	for(var i=0;i<task.candidateUsers.getSize();i++){
		var user = task.candidateUsers.get(i);
		if(i!=task.candidateUsers.getSize()-1){
			userIds=userIds+user.userId+',';
		}else{
			userIds=userIds+user.userId;
		}
	}
	//alert("userIds="+userIds);
	for(var i=0;i<task.candidateGroups.getSize();i++){
		var group = task.candidateGroups.get(i);
		if(i!=task.candidateGroups.getSize()-1){
			groupIds=groupIds+group.groupId+',';
		}else{
			groupIds=groupIds+group.groupId;
		}
	}
	//alert("groupIds="+groupIds);
	for(var i=0;i<task.candidateRoles.getSize();i++){
		var role = task.candidateRoles.get(i);
		if(i!=task.candidateRoles.getSize()-1){
			roleIds=roleIds+role.roleId+',';
		}else{
			roleIds=roleIds+role.roleId;
		}
	}
	//alert("roleIds="+roleIds);
	for(var i=0;i<task.candidateOrgs.getSize();i++){
		var org = task.candidateOrgs.get(i);
		if(i!=task.candidateOrgs.getSize()-1){
			orgIds=orgIds+org.orgId+',';
		}else{
			orgIds=orgIds+org.orgId;
		}
	}
	//alert("orgIds="+orgIds);
	_candidateUsersPreview_panel=jq('#candidateUsersPreview-panel').panel({
		border:false,
		noheader:true,
		top:0,
		left:0
	});
	_candidateUsersPreview_list=jq('#candidateUsersPreview-list').datagrid({
		title:"用户预览",
		url:'${ctx }/workflow/model/candidateUserPreview.d',
		queryParams: {
			userIds: userIds,
			groupIds: groupIds,
			roleIds: roleIds,
			orgIds: orgIds
		},
		height:400,
		autoRowHeight:false,
        rownumbers:true,//行号  
		sortName:'userCode',
	    sortOrder:'asc',
	    striped:true,
	});
});
function searchCandidateUser(){
			candidateUsersPreview-list.datagrid('load',{
			userName: jq("#userName").val()
		});
}
//-->
</script>
<div id="candidateUsersPreview-panel" style="padding:5px;">
	<!-- <table border="0">
		<tr>
			<td>姓名:</td>
			<td><input type="text" id="userName" name="userName" value="" size="15"/></td>
			<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchCandidateUser();">查询</a></td>
		</tr>
	</table> -->
	<table id="candidateUsersPreview-list">
		<thead>
			<tr>
				<th field="userCode" width="80" align="left">账号</th>
				<th field="userName" width="80" align="left" sortable="true">姓名</th>
				<th field="orgName" width="100" align="left">所属机构</th>
				<th field="roleName" width="150" align="left">所属角色</th>
				<th field="groupName" width="150" align="left">所属用户组</th>
				<th field="memo" width="164" align="left">描述</th>
			</tr>
		</thead>
	</table>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/gdsp/common/global.jsp"%>
<script type="text/javascript">
<!--
jq(function(){
	_task_candidate_unselected_role_panel_obj=jq('#_task_candidate_unselected_role_panel').panel({
		//height:600,
		border:false,
		noheader:true,
		top:0,
		left:0
	});
	_task_candidate_unselected_role_grid=jq('#_task_candidate_unselected_role_table').datagrid({
		title:"角色",
		url:'${ctx }/workflow/model/getAllRoles.d',
		//singleSelect:true,
		idField:'roleId',
		height:400,
		autoRowHeight:false,
		/* pagination:true,
		pageSize:15,
		pageNumber:1,
		pageList:[10,15], */
		rownumbers:true,
		sortName:'roleName',
	    sortOrder:'asc',
	    striped:true,
	    onLoadSuccess:function(data){
		   	var rows = data.rows;
		    for(var i=0;i<rows.length;i++){
			    if(task.getCandidateRole(rows[i].roleId)!=null){
					jq(this).datagrid('selectRow',i);
				}
			}
		},
	    toolbar:[{
	        text:"保存",
	        iconCls:'icon-save',
	        handler:function(){
	        	addCandidateRoles();
	        }
	    }]
	});
});
function searchTaskCandidateUnselectedRole(){
	_task_candidate_unselected_role_grid.datagrid('load',{
		 roleName: jq("#roleName").val()
		});
}
function addCandidateRoles(){
	var rows = _task_candidate_unselected_role_grid.datagrid("getSelections");
	for(var i=0;i<rows.length;i++){
		var role = rows[i];
		task.addCandidateRole({
			roleId:role.roleId,
			roleName:role.roleName,
			/* roleCode:role.roleCode */
		});
	}
	loadTaskCandidates("all");
	_task_candidate_win.window('close');
}
//-->
</script>
<div id="_task_candidate_unselected_role_panel" style="padding:5px;">
<div>
	<table border="0">
		<tr>
			<td>角色名:</td>
			<td><input type="text" name="roleName" id="roleName" value="" size="15"/></td>
			<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTaskCandidateUnselectedRole();">查询</a></td>
		</tr>
	</table>
</div>
<div>
	<table id="_task_candidate_unselected_role_table">
		<thead>
			<tr>
				<th field="roleId" align="middle" checkbox="true"></th>
				<!-- <th field="roleCode" width="300" align="middle">角色编码</th> -->
				<th field="roleName" width="200" align="left" sortable="true">名称</th>
				<th field="memo" width="200" align="left">描述</th>
			</tr>
		</thead>
	</table>
</div>
</div>
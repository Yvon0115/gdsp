<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/gdsp/common/global.jsp"%>
<script type="text/javascript">
<!--
jq(function(){
	_task_candidate_unselected_group_panel_obj=jq('#_task_candidate_unselected_group_panel').panel({
		//height:600,
		border:false,
		noheader:true,
		top:0,
		left:0
	});
	_task_candidate_unselected_group_grid=jq('#_task_candidate_unselected_group_table').datagrid({
		title:"用户组",
		url:'${ctx }/workflow/model/getAllGroups.d',
		//singleSelect:true,
		idField:'groupId',
		height:400,
		autoRowHeight:false,
		/* pagination:true,
		pageSize:15,
		pageNumber:1,
		pageList:[10,15], */
		rownumbers:true,
		sortName:'groupName',
	    sortOrder:'asc',
	    striped:true,
	    onLoadSuccess:function(data){
		   	var rows = data.rows;
		    for(var i=0;i<rows.length;i++){
			    if(task.getCandidateGroup(rows[i].groupId)!=null){
					jq(this).datagrid('selectRow',i);
				}
			}
		},
	    toolbar:[{
	        text:"保存",
	        iconCls:'icon-save',
	        handler:function(){
	        	addCandidateGroups();
	        }
	    }]
	});
});
function searchTaskCandidateUnselectedGroup(){
	_task_candidate_unselected_group_grid.datagrid('load',{
			groupName: jq("#groupName").val()
		});
}
function addCandidateGroups(){
	var rows = _task_candidate_unselected_group_grid.datagrid("getSelections");
	for(var i=0;i<rows.length;i++){
		var group = rows[i];
		task.addCandidateGroup({
			groupId:group.groupId,
			groupName:group.groupName,
			/* groupCode:group.groupCode */
		});
	}
	loadTaskCandidates("all");
	_task_candidate_win.window('close');
}
//-->
</script>
<div id="_task_candidate_unselected_group_panel" style="padding:5px;">
<div>
	<table border="0">
		<tr>
			<td>用户组名:</td>
			<td><input type="text" name="groupName" id="groupName" value="" size="15"/></td>
			<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTaskCandidateUnselectedGroup();">查询</a></td>
		</tr>
	</table>
</div>
<div>
<table id="_task_candidate_unselected_group_table">
	<thead>
		<tr>
			<th field="groupId" align="middle" checkbox="true"></th>
			<!-- <th field="groupCode" width="300" align="middle">用户组编码</th> -->
			<th field="groupName" width="200" align="left" sortable="true">名称</th>
			<th field="memo" width="200" align="left">描述</th>
		</tr>
	</thead>
</table>
</div>
</div>
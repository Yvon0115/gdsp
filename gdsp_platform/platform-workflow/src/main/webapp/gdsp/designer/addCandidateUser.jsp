<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/gdsp/common/global.jsp"%>

<script type="text/javascript">
<!--
jq(function(){
	_task_unselected_candidate_user_panel=jq('#task-unselect-candidate-user-panel').panel({
		border:false,
		noheader:true,
		top:0,
		left:0
		//fit:true
	});
	_task_unselect_candidate_user_list=jq('#task-unselect-candidate-user-list').datagrid({
		title:"用户列表",
		url:'${ctx }/workflow/model/searchCandidateUser.d',
		//singleSelect:true,
		width:900,
		height:400,
		idField:'userId',
		autoRowHeight:false,
	  /*pagination:true,
		pageSize:15,
		pageNumber:1,
		pageList:[10,15],*/
		rownumbers:true,
		sortName:'userId',
	    sortOrder:'asc',
	    striped:true,
	    onLoadSuccess:function(data){
		   	var rows = data.rows;
		    for(var i=0;i<rows.length;i++){
		    	if(task.getCandidateUser(rows[i].userId)!=null){
					jq(this).datagrid('selectRow',i);
				}
			}
		},
	    toolbar:[{
        	text:"保存",
 	        iconCls:'icon-save',
 	        handler:function(){
        		addCandidateUsers();
 	        }
	     }]
	});
});
function searchCandidateUser(){
	_task_unselect_candidate_user_list.datagrid('load',{
			userName: jq("#userName").val()
		});
} 
function addCandidateUsers(){
	var rows = _task_unselect_candidate_user_list.datagrid("getSelections");
	//alert(task);
	for(var i=0;i<rows.length;i++){
		var user = rows[i];
		task.addCandidateUser({
				userId:user.userId,
				userName:user.userName,
				userCode:user.userCode
			});
	}
	//alert(task.candidateUsers.getSize());
	loadTaskCandidates("all");
	_task_candidate_win.window('close');
}
//-->
</script>
<div id="task-unselect-candidate-user-panel" style="padding:5px;">
	<table border="0">
		<tr>
			<td>姓名:</td>
			<td><input type="text" id="userName" name="userName" value="" size="15"/></td>
			<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchCandidateUser();">查询</a></td>
		</tr>
	</table>
	<table id="task-unselect-candidate-user-list">
		<thead>
			<tr>
				<th field="userId" align="middle" checkbox="true"></th>
				<th field="userCode" width="150" align="left">账号</th>
				<th field="userName" width="150" align="left" sortable="true">姓名</th>
				<th field="memo" width="200" align="left">描述</th>
			</tr>
		</thead>
	</table>
</div>
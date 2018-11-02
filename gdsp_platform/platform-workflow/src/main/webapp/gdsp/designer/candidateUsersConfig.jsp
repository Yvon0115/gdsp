<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/gdsp/common/global.jsp"%>
<script type="text/javascript">
<!--
	jq(function() {
		_task_candidate_users_list = jq('#task-candidate-users-list').datagrid(
				{
					title : "候选人",
					singleSelect : true,
					height : 450,
					rownumbers : true,
					striped : true,
					toolbar : [ {
						text : '添加',
						iconCls : 'icon-add',
						handler : function() {
							_task_candidate_win.window('open');
							_task_candidate_win.window('setTitle',
									'候选人配置');
							_task_candidate_win.window('refresh',
									'${ctx }/gdsp/designer/addCandidateUser.jsp');
						}
					} ]
				});
		_task_candidate_win = jq('#task-candidate-win').window({
			href:'${ctx }/wf/procdef/procdef!forTaskListenerConfig.action',   
			closed : true,
			cache:false,
			draggable : false,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			modal : true,
			shadow : true
		});
		loadTaskCandidateUsers();
	});
	function candidateUsersBtFormatter(value, rowData, rowIndex) {
		var userId = rowData.userId;
		//var e = '<img onclick="editCandidateUser('+id+')" src="${ctx}/image/edit.gif" title="'+"<s:text name='button.common.modify'></s:text>"+'" style="cursor:hand;"/>';   
		var d = '<img onclick="deleteCandidateUser(\'' + userId
				+ '\')" src="${ctx}/image/delete.gif" title="' + "删除"
				+ '" style="cursor:hand;"/>';
		return d;
	}
	function deleteCandidateUser(userId) {
		task.deleteCandidateUser(userId);
		loadTaskCandidateUsers();
	}
	function loadTaskCandidateUsers() {
		var candidateUsers = task.candidateUsers;
		var candidate_users_grid_rows = [];
		//alert(candidateUsers.getSize());
		//alert(listeners.getSize());
		for ( var i = 0; i < candidateUsers.getSize(); i++) {
			var candidateUser = candidateUsers.get(i);
			//alert(candidateUser.userId);
			//alert(candidateUser.name);
			var user = {
				userId : candidateUser.userId,
				name : candidateUser.name,
				action : ''
			};
			candidate_users_grid_rows[i] = user;
		};
		var candidate_users_grid_data = {
			total : candidateUsers.getSize(),
			rows : candidate_users_grid_rows
		};
		_task_candidate_users_list.datagrid('loadData',
				candidate_users_grid_data);
	}
//-->
</script>
<div id="task-candidate-users-panel">
	<table id="task-candidate-users-list" name="task-candidate-users-list">
		<thead>
			<tr>
				<th field="name" width="100" align="middle" sortable="false">姓名</th>
				<th field="userId" width="100" align="middle" sortable="false" hidden="hidden">id</th>
				<th field="action" width="80" align="middle"
					formatter="candidateUsersBtFormatter">操作</th>
			</tr>
		</thead>
	</table>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/gdsp/common/global.jsp"%>
<script type="text/javascript">
<!--
jq(function(){
	_task_candidate_unselected_org_panel_obj=jq('#_task_candidate_unselected_org_panel').panel({
		//height:600,
		border:false,
		noheader:true,
		top:0,
		left:0
	});
	_task_candidate_unselected_org_grid=jq('#_task_candidate_unselected_org_table').datagrid({
		title:"机构",
		url:'${ctx }/workflow/model/getAllOrgs.d',
		//singleSelect:true,
		idField:'orgId',
		height:400,
		/* pagination:true,
		pageSize:15,
		pageNumber:1,
		pageList:[10,15], */
		rownumbers:true,
		sortName:'orgName',
	    sortOrder:'asc',
	    striped:true,
	    onLoadSuccess:function(data){
		   	var rows = data.rows;
		    for(var i=0;i<rows.length;i++){
			    if(task.getCandidateOrg(rows[i].orgId)!=null){
					jq(this).datagrid('selectRow',i);
				}
			}
		},
	    toolbar:[{
	        text:"保存",
	        iconCls:'icon-save',
	        handler:function(){
	        	addCandidateOrgs();
	        }
	    }]
	});
});
function searchTaskCandidateUnselectedOrg(){
	_task_candidate_unselected_org_grid.datagrid('load',{
			orgName: jq("#orgName").val()
		});
}
function addCandidateOrgs(){
	var rows = _task_candidate_unselected_org_grid.datagrid("getSelections");
	for(var i=0;i<rows.length;i++){
		var org = rows[i];
		task.addCandidateOrg({
			orgId:org.orgId,
			orgName:org.orgName,
			orgCode:org.orgCode
		});
	}
	loadTaskCandidates("all");
	_task_candidate_win.window('close');
}
//-->
</script>
<div id="_task_candidate_unselected_org_panel" style="padding:5px;">
<div>
	<table border="0">
		<tr>
			<td>机构名:</td>
			<td><input type="text" name="orgName" id="orgName" value="" size="15"/></td>
			<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTaskCandidateUnselectedOrg();">查询</a></td>
		</tr>
	</table>
</div>
<div>
<table id="_task_candidate_unselected_org_table">
	<thead>
		<tr>
			<th field="orgId" align="middle" checkbox="true"></th>
			<th field="orgCode" width="200" align="left">机构编码</th>
			<th field="orgName" width="200" align="left" sortable="true">名称</th>
			<th field="memo" width="200" align="left">描述</th>
		</tr>
	</thead>
</table>
</div>
</div>
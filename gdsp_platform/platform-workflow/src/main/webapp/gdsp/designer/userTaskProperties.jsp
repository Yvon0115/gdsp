<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ include file="/gdsp/common/global.jsp"%>

<script type="text/javascript">

//var tid = "";
//var task = workflow.getFigure(tid);
var listenerId="";
var formPropertyId="";
jq(function(){
	var count = 1;
	jq('#task-properties-accordion').accordion({
		onSelect:function(title,index){
			if(count==1){
				if(title=='节点属性'){
					var pp = jq('#task-properties-accordion').accordion('getSelected');
					if (pp){
    					pp.panel('refresh','${ctx }/gdsp/designer/taskGeneral.jsp');
					}
				}
				count++;
			}
			if(title=='会签属性'){
				var pp = jq('#task-properties-accordion').accordion('getSelected');
				if (pp){
					pp.panel('refresh','${ctx }/gdsp/designer/taskMultiInstance.jsp');
				}
			}
			}
		});
	jq('#performerType').combobox({
			editable:false,
			panelHeight:'auto',
			onChange:function(newValue, oldValue){
				//switchTaskCandidatesList(newValue);
			}
		});
	_form_win = jq('#form-win').window({
		//href:'${ctx }/wf/procdef/procdef!forTaskListenerConfig.action',   
	    closed:true,
	    //cache:false,
	    draggable:true,
	    collapsible:false,
	    minimizable:false,
	    maximizable:false,
	    modal:true,
	    shadow:true
	});
	task_candidate_panel=jq('#task-candidate-panel').panel({
		border:false,
		//minimized:true,
		height:450
		//width:450,
		//fit:true
	});
	populateTaskProperites();
	loadTaskCandidates("all");
	//switchTaskCandidatesList(jq('#performerType').combobox('getValue'));
	var performerType=jq('#performerType').combobox('getValue');
});
function switchIsExpression(obj){
	if(obj.value=='true'){
		jq('#expressionTr').show();
		task_candidate_panel.panel("close");
	}else if(obj.value=='false'){
		jq('#expressionTr').hide();
		var performerType=jq('#performerType').combobox('getValue');
		task_candidate_panel.panel("open");
		if(performerType == 'candidateUsers'){
			task_candidate_panel.panel("refresh","${ctx }/gdsp/designer/candidateUsersConfig.jsp");
		}else if(performerType == 'candidateGroups'){
			task_candidate_panel.panel("refresh","${ctx }/gdsp/designer/candidateGroupsConfig.jsp");
		}
	}
}

/* function switchTaskCandidatesList(performerType){
	if(jq('#nonExpression').attr('checked')){
		if(performerType == 'candidateUsers'){
			task_candidate_panel.panel("refresh","${ctx }/gdsp/designer/candidateUsersConfig.jsp");
		}else if(performerType == 'candidateGroups'){
			task_candidate_panel.panel("refresh","${ctx }/gdsp/designer/candidateGroupsConfig.jsp");
		}else if(performerType == 'candidateRoles'){
			task_candidate_panel.panel("refresh","${ctx }/gdsp/designer/assigneeConfig.jsp");
		}else if(performerType == 'candidateOrgs'){
			task_candidate_panel.panel("refresh","${ctx }/gdsp/designer/assigneeConfig.jsp");
		}
	}
} */
//保存任务属性配置
function saveTaskProperties(){
	saveTaskGeneral();
	if(typeof saveTaskMultiInstance == 'function')
		saveTaskMultiInstance();
	task.performerType=jq('#performerType').combobox('getValue');
	task.expression=jq('#expression').val();
	if(jq('#isExpression').attr('checked')){
		task.isUseExpression=jq('#isExpression').val();
	}else if(jq('#nonExpression').attr('checked')){
		task.isUseExpression=jq('#nonExpression').val();
	}
	task.formKey=jq('#formKey').val();
	task.dueDate=jq('#dueDate').val();
	task.priority=jq('#priority').val();
	task.documentation=jq('#documentation').val();
	task.extendEvent=jq('#extendEvent').val();
	//jq.messager.alert('提示','保存成功!','info');
}
function candidate(name,url){
	_task_candidate_win = jq('#task-candidate-win').window({
		href:'',   
		closed : true,
		cache:false,
		draggable : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		modal : true,
		shadow : true
	});
	_task_candidate_win.window('open');
	_task_candidate_win.window('setTitle',name);
	_task_candidate_win.window('refresh',url);
}
function setUsers(){
	var type = jq('#performerType').combobox('getValue');
	if(type == 'candidateUsers'){
		candidate('候选人配置','${ctx }/gdsp/designer/addCandidateUser.jsp');
	}else if(type == 'candidateGroups'){
		candidate('用户组配置','${ctx }/gdsp/designer/addCandidateGroup.jsp');
	}else if(type == 'candidateRoles'){
		candidate('角色配置','${ctx }/gdsp/designer/addCandidateRole.jsp');
	}else if(type == 'candidateOrgs'){
		candidate('机构配置','${ctx }/gdsp/designer/addCandidateOrg.jsp');
	}
}
function deleteUsers(){
	var dels = jq("input[name='can_chk']:checked").each(function(){
		if(task.getCandidateUser(jq(this).val())!=null){
			task.deleteCandidateUser(jq(this).val());
			return;			
		}
		if(task.getCandidateGroup(jq(this).val())!=null){
			task.deleteCandidateGroup(jq(this).val());
			return;			
		}
		if(task.getCandidateRole(jq(this).val())!=null){
			task.deleteCandidateRole(jq(this).val());
			return;			
		}
		if(task.getCandidateOrg(jq(this).val())!=null){
			task.deleteCandidateOrg(jq(this).val());
			return;			
		}
	});
	loadTaskCandidates("all");
}
function findUsers(){
	candidate('用户预览','${ctx }/gdsp/designer/candidateUsersPreview.jsp');
}
//执行人页面显示
function loadTaskCandidates(name) {
	var candidateUsers = task.candidateUsers;
	var candidateGroups = task.candidateGroups;
	var candidateRoles = task.candidateRoles;
	var candidateOrgs = task.candidateOrgs;
	var trHTML = "";
	jq("#can_all").empty();
	trHTML +="<tr>";
	trHTML +="<td width=\"50px;\"><input id=\"checkAll\" type=\"checkbox\" onclick=\"selectAll();\"/></td>";
	trHTML +="<td>来源类型</td>";
	trHTML +="<td>编码</td>";
	trHTML +="<td>名称</td>";
	trHTML +="</tr>";
	if(name=="user"||name=="all"){
		if(candidateUsers.getSize()>0){
			for ( var i = 0; i < candidateUsers.getSize(); i++) {
				trHTML +="<tr>";
				var user = candidateUsers.get(i);
				trHTML +="<td><input type='checkbox' name='can_chk' value="+user.userId+"></td>";
				trHTML +="<td>用户</td>";
				trHTML +="<td>"+user.userCode+"</td>";
				trHTML +="<td>"+user.userName+"</td>";
				trHTML +="</tr>";
			};
		}
	}
	if(name=="group"||name=="all"){
		if(candidateGroups!=null&&candidateGroups.getSize()>0){
			for ( var i = 0; i < candidateGroups.getSize(); i++) {
				trHTML +="<tr>";
				var group = candidateGroups.get(i);
				trHTML +="<td><input type='checkbox' name='can_chk' value="+group.groupId+"></td>";
				trHTML +="<td>用户组</td>";
				trHTML +="<td>"+group.groupCode+"</td>";
				trHTML +="<td>"+group.groupName+"</td>";
				trHTML +="</tr>";
			};
		}
	}
	if(name=="role"||name=="all"){
		if(candidateRoles.getSize()>0){
			for ( var i = 0; i < candidateRoles.getSize(); i++) {
				trHTML +="<tr>";
				var role = candidateRoles.get(i);
				trHTML +="<td><input type='checkbox' name='can_chk' value="+role.roleId+"></td>";
				trHTML +="<td>角色</td>";
				trHTML +="<td>"+role.roleCode+"</td>";
				trHTML +="<td>"+role.roleName+"</td>";
				trHTML +="</tr>";
			};
		}
	}
	if(name=="org"||name=="all"){
		if(candidateOrgs.getSize()>0){
			for ( var i = 0; i < candidateOrgs.getSize(); i++) {
				trHTML +="<tr>";
				var org = candidateOrgs.get(i);
				trHTML +="<td><input type='checkbox' name='can_chk' value="+org.orgId+"></td>";
				trHTML +="<td>机构</td>";
				trHTML +="<td>"+org.orgCode+"</td>";
				trHTML +="<td>"+org.orgName+"</td>";
				trHTML +="</tr>";
			};
		}
	}
	jq("#can_all").append(trHTML);
	trHTML="";
}
//回显任务属性配置
function populateTaskProperites(){
	//jq('#id').val(task.taskId);
	//jq('#name').val(task.taskName);
	jq('#performerType').combobox('setValue',task.performerType);
	if(task.isUseExpression){
		//jq('#expressionTr').show();
		jq('#expression').val(task.expression);
	}else{
		jq('#expressionTr').hide();
	}
	
	//jq('#formKey').val(task.formKey);
	//jq('#dueDate').val(task.dueDate);
	//jq('#priority').val(task.priority);
	jq('#documentation').val(task.documentation);
	//loadTaskListeners();
}
function selectAll(){
	if(jq('#checkAll').attr("checked")){
		jq("input[name='can_chk']").attr("checked",true);
	}else{
		jq("input[name='can_chk']").attr("checked",false);
	}
}
//-->
</script>
<style type="text/css">
.table{border-collapse:collapse; font-size:13px; height:24px; line-height:24px; color:black; text-align:center;}
.table tr th{color:black; font-size:13px; height:24px; line-height:24px;}
.table tr th.th_border{border-right:solid 1px #D3D3D3; border-left:solid 1px #D3D3D3;border-top:solid 1px #D3D3D3; }
.table tr td{border:solid 1px #D3D3D3;}

</style>
<div id="task-properties-layout" class="easyui-layout" fit="true">
	<!-- <div id="task-properties-toolbar-panel" region="north" border="false" style="height:30px;background:#E1F0F2;">
		<a href="##" id="sb2" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="saveTaskProperties()">保存</a>
	</div> -->
	<div id="task-properties-panel" region="center" border="true">
		<div id="task-properties-accordion" class="easyui-accordion" fit="true" border="false">
			<div id="general"  title="节点属性" selected="true" class="properties-menu">
				
			</div>
			<div id="main-config" title="用户配置" class="properties-menu" style="margin-top: 8px;margin-bottom: -8px;">
				<table id="user-config">
					<tr>
						<td style="text-align:right;">
							<p style="width:80px; text-align: right;">用户类型:</p>
						</td>
						<td>
							<select id="performerType" name="performerType">
								<option value="candidateUsers" selected="selected">用户</option>
								<!-- <option value="candidateGroups">用户组</option> -->
								<option value="candidateRoles">角色</option>
								<!-- <option value="candidateOrgs">机构</option> -->
							</select>
						</td>
					</tr>
				
					<tr>
						<td >
							<!-- <b>候选用户</b> -->
						<p style="width:80px; text-align: right;">操作选择:</p>
						</td>
						<td>
							<input type="button" value="新增" onclick="setUsers();setZindex();" />&nbsp;
							<input type="button" value="删除" onclick="deleteUsers();" />&nbsp;
							<input type="button" value="用户预览" onclick="findUsers();setZindex();" />
						</td>
					</tr>
					<tr >
						<td >
							<div style="margin-left: 20px;padding-top: 15px;">已选用户:</div>
						</td>
					</tr>
					<tr>
						<td colspan="2" rowspan="2">
							<div style="width: 280px;height: 300px;margin-bottom: 20px;">
								<table id="can_all" class="table" cellpadding="0" cellspacing="0"
									style="width: 258px; margin-left: 20px; margin-top: 15px;"  >
								</table>
							</div>
						</td>
					</tr>
					<tr style="display:none;">
						<td align="right">
							<p style="width: 70px;text-align: left;font-size: 14px;">表达式:</p>
						</td>
						<td>
							<input type="radio" id="isExpression" name="useExpression" value="true" onclick="switchIsExpression(this)"/>是
							<input type="radio" id="nonExpression" name="useExpression" value="false" onclick="switchIsExpression(this)" checked/>否
						</td>
					</tr>
					<tr id="expressionTr" style="display:none;">
						<td align="right">
							<p style="width: 70px;text-align: left;font-size: 14px;">表达式:</p>
						</td>
						<td>
							<input style="width: 200px;" type="text" id="expression" name="expression" size="50"/>
						</td>
					</tr>
					<!-- <tr>
						<td align="right">
							<p style="width: 70px;text-align: left;font-size: 14px;">描述:</p>
						</td>
						<td><textarea style="width: 200px;" id="documentation" name="documentation" cols="1" rows="1"></textarea></td>
					</tr> -->
					<!-- <tr>
						<td colspan="2" rowspan="2">
							<div id="task-candidate-panel" style="width: 280px;height: 300px;">
							</div>
						</td>
					</tr> -->
				</table>
			</div>
			<div id="multi-instance" title="会签属性" class="properties-menu" style="">
			</div>
		</div>
	</div>
</div>
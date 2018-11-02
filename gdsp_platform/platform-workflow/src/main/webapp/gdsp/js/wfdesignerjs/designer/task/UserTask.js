draw2d.UserTask=function(configPropCallback){
	draw2d.Task.call(this,configPropCallback);
	this.performerType=null;
	this.dueDate=null;
	this.priority=null;
	this.formKey = null;
	this.expression=null;
	this.isUseExpression=null;
	this.assignee=null;
	this.candidateUsers=new draw2d.ArrayList();
	this.candidateGroups=new draw2d.ArrayList();
	//增加候选类型
	this.candidateRoles=new draw2d.ArrayList();
	this.candidateOrgs=new draw2d.ArrayList();
	
	this.formProperties=new draw2d.ArrayList();
	this.taskListeners=new draw2d.ArrayList();
	this.setTitle("用户任务");
		
	
	//节点属性
	this.form=null;
	this.systeminform=null;
	this.emailinform=null;
	this.overtime=null;
	this.unit=null;
	this.workingday=null;
	this.dealMethod=null;
	this.extendEvent=null;	
	
};
draw2d.UserTask.prototype=new draw2d.Task();
draw2d.UserTask.prototype.type="draw2d.UserTask";
draw2d.UserTask.newInstance=function(userTaskXMLNode){
	var task = new draw2d.UserTask();
	task.id=userTaskXMLNode.attr('id');
	task.taskId=userTaskXMLNode.attr('id');
	task.taskName=userTaskXMLNode.attr('name');
	task.setContent(userTaskXMLNode.attr('name'));
	return task;
};
draw2d.UserTask.prototype.getIconClassName = function(){
	return "user-task-icon";
};
draw2d.UserTask.prototype.getStartElementXML=function(){
	var xml='<userTask ';
	xml=xml+this.getGeneralXML();
	xml=xml+this.getPerformersXML();
	xml=xml+'>\n';
	return xml;
};
draw2d.UserTask.prototype.getEndElementXML=function(){
	var xml = '</userTask>\n';
	return xml;
};
draw2d.UserTask.prototype.getDocumentationXML=function(){
	if(this.documentation==null||this.documentation=='')return '';
	var xml='<documentation>';
	xml=xml+this.documentation;
	xml=xml+'</documentation>';
	return xml;
};
draw2d.UserTask.prototype.getPerformersXML=function(){
	var xml='';
	//alert(jq('#performerType').combobox('getValue'));
	//alert(this.performerType);
	var performerType = this.performerType;
	//var performerType = jq('#performerType').combobox('getValue');
	if(performerType=='assignee'){
		xml=xml+'activiti:assignee="'+this.assignee+'" ';
	}else if(performerType=='candidateUsers'){
		var express = '';
		for(var i=0;i<this.candidateUsers.getSize();i++){
			var user = this.candidateUsers.get(i);
			if(i!=this.candidateUsers.getSize()-1){
				express=express+user.userId+',';
			}else{
				express=express+user.userId;
			}
		}
		xml=xml+'activiti:candidateUsers="'+express+'" ';
	}else if(performerType=='candidateGroups'){
		var express = '';
		for(var i=0;i<this.candidateGroups.getSize();i++){
			var group = this.candidateGroups.get(i);
			if(i!=this.candidateGroups.getSize()-1){
				express=express+group.groupId+',';
			}else{
				express=express+group.groupId;
			}
		}
		xml=xml+'activiti:candidateGroups="'+express+'" ';
	}
	/*if(this.isUseExpression){
		if(this.expression!=null&&this.expression!=''){
			if(this.performerType=='assignee'){
				xml=xml+'activiti:assignee="'+this.expression+'" ';
			}else if(this.performerType=='candidateUsers'){
				xml=xml+'activiti:candidateUsers="'+this.expression+'" ';
			}else if(this.performerType=='candidateGroups'){
				xml=xml+'activiti:candidateGroups="'+this.expression+'" ';
			}
		}
	}else{
		if(jq('#performerType').combobox('getValue')=='assignee'){
			if(this.assignee!=null&&this.assignee!='')
				xml=xml+this.assignee;
		}else if(jq('#performerType').combobox('getValue')=='candidateUsers'){
			for(var i=0;i<this.candidateUsers.getSize();i++){
				var user = this.candidateUsers.get(i);
				xml=xml+user.userId+',';
			}
		}else if(jq('#performerType').combobox('getValue')=='candidateGroups'){
			for(var i=0;i<this.candidateGroups.getSize();i++){
				var group = this.candidateGroups.get(i);
				xml=xml+group+',';
			}
		}
	}*/
	/*if(this.dueDate!=null&&this.dueDate!=''){
		xml=xml+'activiti:dueDate="'+this.dueDate+'" '
	}
	if(this.formKey != null && this.formKey != ""){
		xml=xml+'activiti:formKey="'+this.formKey+'" ';
	}
	if(this.priority!=null&&this.priority!=''){
		xml=xml+'activiti:priority="'+this.priority+'" '
	}*/
	
	return xml;
};
draw2d.UserTask.prototype.getExtensionElementsXML=function(){
	if(this.listeners.getSize()==0&&this.formProperties.getSize()==0)return '';
	var xml = '<extensionElements>\n';
	xml=xml+this.getFormPropertiesXML();
	xml=xml+this.getListenersXML();
	xml=xml+'</extensionElements>\n';
	return xml;
};
draw2d.UserTask.prototype.getListenersXML=function(){
	var xml = draw2d.Task.prototype.getListenersXML.call(this);
	for(var i=0;i<this.taskListeners.getSize();i++){
		var listener = this.taskListeners.get(i);
		xml=xml+listener.toXML();
	}
	return xml;
};
draw2d.UserTask.prototype.getFormPropertiesXML=function(){
	var xml = '';
	for(var i=0;i<this.formProperties.getSize();i++){
		var formProperty = this.formProperties.get(i);
		xml=xml+formProperty.toXML();
	}
	return xml;
};
draw2d.UserTask.prototype.toXML=function(){
	var xml=this.getStartElementXML();
	xml=xml+this.getDocumentationXML();
	xml=xml+this.getExtensionElementsXML();
	xml=xml+this.getMultiInstanceXML();
	xml=xml+this.getEndElementXML();
	return xml;
};
draw2d.UserTask.prototype.toJson=function(){
	var json=this.formPropertyToJson();
	json=json+this.getCandidateUsers();
	return json;
};
draw2d.UserTask.prototype.getCandidateUsers=function(){
	var json='candidateUsers:[';
	var performerType = this.performerType;
	var candidateUsers = this.candidateUsers;
	var candidateGroups = this.candidateGroups;
	var candidateRoles = this.candidateRoles;
	var candidateOrgs = this.candidateOrgs;
	if(candidateUsers.getSize()>0){
		for ( var i = 0; i < candidateUsers.getSize(); i++) {
			json += "{";
			var user = candidateUsers.get(i);
			json += "type:'user'";
			json += ",id:'"+user.userId+"'";
			json += ",code:'"+user.userCode+"'";
			json += ",name:'"+user.userName+"'";
			json += "},";
		};
	}
	if(candidateGroups!=null&&candidateGroups.getSize()>0){
		for ( var i = 0; i < candidateGroups.getSize(); i++) {
			json += "{";
			var group = candidateGroups.get(i);
			json += "type:'group'";
			json += ",id:'"+group.groupId+"'";
			json += ",code:'"+group.groupCode+"'";
			json += ",name:'"+group.groupName+"'";
			json += "},";
		};
	}
	if(candidateRoles.getSize()>0){
		for ( var i = 0; i < candidateRoles.getSize(); i++) {
			json += "{";
			var role = candidateRoles.get(i);
			json += "type:'role'";
			json += ",id:'"+role.roleId+"'";
			json += ",code:'"+role.roleCode+"'";
			json += ",name:'"+role.roleName+"'";
			json += "},";
		};
	}
	if(candidateOrgs.getSize()>0){
		for ( var i = 0; i < candidateOrgs.getSize(); i++) {
			json += "{";
			var org = candidateOrgs.get(i);
			json += "type:'org'";
			json += ",id:'"+org.orgId+"'";
			json += ",code:'"+org.orgCode+"'";
			json += ",name:'"+org.orgName+"'";
			json += "},";
		};
	}
	if(json!='candidateUsers:['){
		json = json.substring(0,json.length-1);
	}
	json += "]";
	return json;
};
draw2d.UserTask.prototype.getCandidateUser=function(userId){
	for(var i=0;i<this.candidateUsers.getSize();i++){
		var candidate = this.candidateUsers.get(i);
		if(candidate.userId===userId){
			return candidate;
		}
	}
	return null;
};
draw2d.UserTask.prototype.deleteCandidateUser=function(userId){
	var candidate = this.getCandidateUser(userId);
	this.candidateUsers.remove(candidate);
};
draw2d.UserTask.prototype.addCandidateUser=function(user){
	var userId = user.userId;
	var user2 = this.getCandidateUser(userId);
	if(user2==null)
		this.candidateUsers.add(user);
};
draw2d.UserTask.prototype.getCandidateGroup=function(id){
	for(var i=0;i<this.candidateGroups.getSize();i++){
		var candidate = this.candidateGroups.get(i);
		if(candidate!=null&&candidate.groupId==id){
			return candidate;
		}
	}
	return null;
};
draw2d.UserTask.prototype.deleteCandidateGroup=function(id){
	var candidate = this.getCandidateGroup(id);
	this.candidateGroups.remove(candidate);
};
draw2d.UserTask.prototype.addCandidateGroup=function(group){
	var groupId = group.groupId;
	var can = this.getCandidateGroup(groupId);
	if(can==null){
		this.candidateGroups.add(group);
	}
};
//增加角色的增删查方法
draw2d.UserTask.prototype.getCandidateRole=function(id){
	for(var i=0;i<this.candidateRoles.getSize();i++){
		var candidate = this.candidateRoles.get(i);
		if(candidate!=null&&candidate.roleId===id){
			return candidate;
		}
	}
	return null;
};
draw2d.UserTask.prototype.deleteCandidateRole=function(id){
	var candidate = this.getCandidateRole(id);
	this.candidateRoles.remove(candidate);
};
draw2d.UserTask.prototype.addCandidateRole=function(candidate){
	var roleId = candidate.roleId;
	var can = this.getCandidateRole(roleId);
	if(can==null){
		this.candidateRoles.add(candidate);
	}
};
//增加机构的增删查方法
draw2d.UserTask.prototype.getCandidateOrg=function(id){
	for(var i=0;i<this.candidateOrgs.getSize();i++){
		var candidate = this.candidateOrgs.get(i);
		if(candidate!=null&&candidate.orgId===id){
			return candidate;
		}
	}
	return null;
};
draw2d.UserTask.prototype.deleteCandidateOrg=function(id){
	var candidate = this.getCandidateOrg(id);
	this.candidateOrgs.remove(candidate);
};
draw2d.UserTask.prototype.addCandidateOrg=function(candidate){
	var orgId = candidate.orgId;
	var can = this.getCandidateOrg(orgId);
	if(can==null){
		this.candidateOrgs.add(candidate);
	}
};
//<<<<<

draw2d.UserTask.prototype.getTaskListener=function(id){
	for(var i=0;i<this.taskListeners.getSize();i++){
		var listener = this.taskListeners.get(i);
		if(listener.getId()=== id){
			return listener;
		}
	}
};
draw2d.UserTask.prototype.deleteTaskListener=function(id){
	var listener = this.getTaskListener(id);
	this.taskListeners.remove(listener);
};
draw2d.UserTask.prototype.addTaskListener=function(listener){
	this.taskListeners.add(listener);
};
draw2d.UserTask.prototype.setTaskListeners=function(listeners){
	this.taskListeners = listeners;
};
draw2d.UserTask.prototype.getFormProperties=function(id){
	for(var i=0;i<this.formProperties.getSize();i++){
		var prop = this.formProperties.get(i);
		if(prop.id== id){
			return prop;
		}
	}
};
draw2d.UserTask.prototype.deleteFormProperties=function(id){
	var prop = this.getFormProperties(id);
	this.formProperties.remove(prop);
};
draw2d.UserTask.prototype.addFormProperties=function(prop){
	this.formProperties.add(prop);
};
draw2d.UserTask.prototype.setFormProperties=function(props){
	this.formProperties = props;
};

draw2d.UserTask.prototype.formPropertyToJson=function(){	
	var json='';		
	json=json+ "taskName:'" + this.taskName+"'";
	json=json +",taskId:'" + this.taskId+"'";
	json=json + ",form:'" + this.form+"'";
	json=json + ",systeminform:'" + this.systeminform+"'";
	json=json + ",emailinform:'" + this.emailinform+"'";
	json=json + ",overtime:'" + this.overtime+"'";
	json=json + ",unit:'" + this.unit+"'";
	json=json + ",workingday:'" + this.workingday+"'";
	json=json + ",dealMethod:'" + this.dealMethod+"'";
	json=json + ",extendEvent:'" + this.extendEvent+"'";
	json=json + this.getMultiInsJson();
    return json;	
};
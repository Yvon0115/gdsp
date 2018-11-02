draw2d.Task = function(configPropCallback) {
	this.cornerWidth = 15;
	this.cornerHeight = 15;
	this.rightOutputPort = null;
	this.bottomOutputPort = null;
	this.topOutputPort = null;
	this.leftOutputPort = null;
	draw2d.Node.call(this);
	this.setDimension(130,60);
	this.originalHeight = -1;
	this.taskId=null;
	this.taskName="";
	this.documentation=null;
	this.listeners=new draw2d.ArrayList();
	this.asynchronous=null;
	this.exclusive=true;
	this.isSequential=null;
	this._loopCardinality=null;
	this._collection=null;
	this._elementVariable=null;
	this._completionCondition=null;
	//会签规则
	this._multiInstanceRule=null;
	this._multiInstanceVal=0;
	this._multiCheckbox=null;
	
	this.openPropertiesCallBack=configPropCallback;
	this.setIcon();
	//设置任务节点不能通过delete键删除
	this.deleteable=false;
};
draw2d.Task.prototype = new draw2d.Node();
draw2d.Task.prototype.type = "Task";
draw2d.Task.prototype.generateId=function(){
	this.id="task"+Sequence.create();
	this.taskId=this.id;
};
draw2d.Task.prototype.createHTMLElement = function() {
	var item = document.createElement("div");
	item.id = this.id;
	item.style.position = "absolute";
	item.style.left = this.x + "px";
	item.style.top = this.y + "px";
	item.style.height = this.width + "px";
	item.style.width = this.height + "px";
	item.className="task";
	item.style.zIndex = "" + draw2d.Figure.ZOrderBaseIndex;
	
	this.top_left = document.createElement("div");
	this.top_left.className="task-top-left";
	this.top_left.style.width = this.cornerWidth + "px";
	this.top_left.style.height = this.cornerHeight + "px";
	
	this.top_right = document.createElement("div");
	this.top_right.className="task-top-right";
	this.top_right.style.width = this.cornerWidth + "px";
	this.top_right.style.height = this.cornerHeight + "px";
	
	this.bottom_left = document.createElement("div");
	this.bottom_left.className="bottom-top-left";
	this.bottom_left.style.width = this.cornerWidth + "px";
	this.bottom_left.style.height = this.cornerHeight + "px";
	
	this.bottom_right = document.createElement("div");
	this.bottom_right.className="bottom-top-right";
	this.bottom_right.style.width = this.cornerWidth + "px";
	this.bottom_right.style.height = this.cornerHeight + "px";
	
	this.header = document.createElement("div");
	this.header.className="task-header";
	this.header.style.position = "absolute";
	this.header.style.left = this.cornerWidth + "px";
	this.header.style.top = "0px";
	this.header.style.height = (this.cornerHeight) + "px";
	this.disableTextSelection(this.header);
	
	this.footer = document.createElement("div");
	this.footer.className="task-footer";
	this.footer.style.position = "absolute";
	this.footer.style.left = this.cornerWidth + "px";
	this.footer.style.top = "0px";
	this.footer.style.height = (this.cornerHeight - 1) + "px";
	
	this.textarea = document.createElement("div");
	this.textarea.className="task-textarea";
	this.textarea.style.position = "absolute";
	this.textarea.style.left = "0px";
	this.textarea.innerText = this.taskName;
	this.textarea.style.top = this.cornerHeight + "px";
	this.disableTextSelection(this.textarea);
	
	item.appendChild(this.top_left);
	item.appendChild(this.header);
	item.appendChild(this.top_right);
	item.appendChild(this.textarea);
	item.appendChild(this.bottom_left);
	item.appendChild(this.footer);
	item.appendChild(this.bottom_right);
	
	return item;
};
draw2d.Task.prototype.setDimension = function(w, h) {
	try{
		draw2d.Node.prototype.setDimension.call(this, w, h);
		if (this.top_left !== null) {
			this.top_right.style.left = (this.width - this.cornerWidth) + "px";
			this.bottom_right.style.left = (this.width - this.cornerWidth) + "px";
			this.bottom_right.style.top = (this.height - this.cornerHeight) + "px";
			this.bottom_left.style.top = (this.height - this.cornerHeight) + "px";
			this.textarea.style.width = (this.width - 2) + "px";
			this.textarea.style.height = (this.height - this.cornerHeight * 2)
					+ "px";
			this.header.style.width = (this.width - this.cornerWidth * 2) + "px";
			this.footer.style.width = (this.width - this.cornerWidth * 2) + "px";
			this.footer.style.top = (this.height - this.cornerHeight) + "px";
		}
		if (this.rightOutputPort !== null) {
			this.rightOutputPort.setPosition(this.width + 5, this.height / 2);
		}
		if (this.bottomOutputPort !== null) {
			this.bottomOutputPort.setPosition(this.width/2, this.height + 5);
		}
		if (this.leftOutputPort !== null) {
			this.leftOutputPort.setPosition(-5, this.height / 2);
		}
		if (this.topOutputPort !== null) {
			this.topOutputPort.setPosition(this.width/2, -5);
		}
	}catch(e){
	}
};
draw2d.Task.prototype.setTitle = function(title) {
	this.header.innerHTML = title;
};
draw2d.Task.prototype.setContent = function(_5014) {
	this.textarea.innerHTML = _5014;
};
draw2d.Task.prototype.onDragstart = function(x, y) {
	var _5017 = draw2d.Node.prototype.onDragstart.call(this, x, y);
	if (this.header === null) {
		return false;
	}
	if (y < this.cornerHeight && x < this.width
			&& x > (this.width - this.cornerWidth)) {
		this.toggle();
		return false;
	}
	if (this.originalHeight == -1) {
		if (this.canDrag === true && x < parseInt(this.header.style.width)
				&& y < parseInt(this.header.style.height)) {
			return true;
		}
	} else {
		return _5017;
	}
};
draw2d.Task.prototype.setCanDrag = function(flag) {
	draw2d.Node.prototype.setCanDrag.call(this, flag);
	this.html.style.cursor = "";
	if (this.header === null) {
		return;
	}
	if (flag) {
		this.header.style.cursor = "move";
	} else {
		this.header.style.cursor = "";
	}
};
draw2d.Task.prototype.setWorkflow = function(_5019) {
	draw2d.Node.prototype.setWorkflow.call(this, _5019);
	if (_5019 !== null && this.leftOutputPort === null) {
		this.leftOutputPort = new draw2d.MyOutputPort();
		this.leftOutputPort.setWorkflow(_5019);
		this.leftOutputPort.setName("leftOutputPort");
		this.addPort(this.leftOutputPort, -5, this.height / 2);
		
		this.topOutputPort = new draw2d.MyOutputPort();
		this.topOutputPort.setWorkflow(_5019);
		this.topOutputPort.setName("topOutputPort");
		this.addPort(this.topOutputPort, this.width/2, -5);
		
		this.rightOutputPort = new draw2d.MyOutputPort();
		this.rightOutputPort.setMaxFanOut(5);
		this.rightOutputPort.setWorkflow(_5019);
		this.rightOutputPort.setName("rightOutputPort");
		this.addPort(this.rightOutputPort, this.width + 5, this.height / 2);
		
		this.bottomOutputPort = new draw2d.MyOutputPort();
		this.bottomOutputPort.setMaxFanOut(5);
		this.bottomOutputPort.setWorkflow(_5019);
		this.bottomOutputPort.setName("bottomOutputPort");
		this.addPort(this.bottomOutputPort, this.width/2, this.height + 5);
	}
};
draw2d.Task.prototype.toggle = function() {
	if (this.originalHeight == -1) {
		this.originalHeight = this.height;
		this.setDimension(this.width, this.cornerHeight * 2);
		this.setResizeable(false);
	} else {
		this.setDimension(this.width, this.originalHeight);
		this.originalHeight = -1;
		this.setResizeable(true);
	}
};

draw2d.Task.prototype.onDoubleClick=function(){
	if(typeof this.openPropertiesCallBack == "function"){
			this.openPropertiesCallBack(this);
		}
};

draw2d.Task.prototype.getContextMenu=function(){
	if(this.workflow.disabled)return null;
	var menu =new draw2d.ContextMenu(100, 50);
	var data = {task:this};
	var userTask = data.task.taskId;
	//发起人右键无菜单
	if(userTask!="startuser"){
		menu.appendMenuItem(new draw2d.ContextMenuItem("属性", "properties-icon",data,function(x,y)
		{
			var data = this.getData();
			var task = data.task;
			//var tid = task.getId();
			if(typeof task.openPropertiesCallBack == "function"){
				task.openPropertiesCallBack(task);
			}
		}));
		menu.appendMenuItem(new draw2d.ContextMenuItem("删除", "icon-remove",data,function(x,y)
		{
			var data = this.getData();
			var task = data.task;
			var tid = task.getId();
			var wf = task.getWorkflow();
			wf.getCommandStack().execute(new draw2d.CommandDelete(task));
			//wf.removeFigure(task);
		}));
		return menu;
	}
	return null;
};
draw2d.Task.prototype.getIconClassName = function(){
};
draw2d.Task.prototype.setIcon = function(){
	this.icon = document.createElement("div");
	this.icon.style.position = "absolute";
	this.icon.style.width = this.cornerWidth + "px";
	this.icon.style.height = this.cornerHeight + "px";
	this.icon.style.left = "10px";
	this.icon.style.top = "2px";
	this.icon.className = this.getIconClassName();
	this.getHTMLElement().appendChild(this.icon);
	return this.icon;
};
draw2d.Task.prototype.getListener=function(id){
	for(var i=0;i<this.listeners.getSize();i++){
		var listener = this.listeners.get(i);
		if(listener.getId()=== id){
			return listener;
		}
	}
};
draw2d.Task.prototype.deleteListener=function(id){
	var listener = this.getListener(id);
	this.listeners.remove(listener);
};
draw2d.Task.prototype.addListener=function(listener){
	this.listeners.add(listener);
};
draw2d.Task.prototype.setListeners=function(listeners){
	this.listeners = listeners;
};
draw2d.Task.prototype.setHighlight=function(){
	this.getHTMLElement().className="task-highlight";
};
draw2d.Task.prototype.onMouseEnter=function(){
	if(typeof onTaskMouseEnter != "undefined"){
		onTaskMouseEnter(this);
	}
};
draw2d.Task.prototype.onMouseLeave=function(){
	if(typeof onTaskMouseLeave != "undefined"){
		onTaskMouseLeave(this);
	}
};
draw2d.Task.prototype.getStartElementXML=function(){
	
};
draw2d.Task.prototype.getGeneralXML=function(){
	var name = this.taskId;
	var taskName = trim(this.taskName);
	if(taskName != null && taskName != "")
		name = taskName;
	var xml=' id="'+this.taskId+'" name="'+name+'" ';
	if(this.asynchronous){
		xml=xml+'activiti:async="true" ';
	}
	if(!this.exclusive){
		xml=xml+'activiti:exclusive="false" ';
	}
	return xml;
};
draw2d.Task.prototype.getMultiInsJson=function(){
	var json=",multiInsRule:'"+this._multiInstanceRule+"',multiInsVal:'"+this._multiInstanceVal+"',";
	return json;
};
draw2d.Task.prototype.getEndElementXML=function(){
	
};
draw2d.Task.prototype.getDocumentationXML=function(){
	return "";
};
draw2d.Task.prototype.getMultiInstanceXML=function(){
	var xml = '';
	var hasHuiQian = true;
	if(this.multiCheckbox==null||this.multiCheckbox==false){
		hasHuiQian =false;
	}
	if(this.isSequential==null){
		hasHuiQian =false;
	}
	if(hasHuiQian){
		var express = '';
		if(this.candidateUsers!=null){
			for(var i=0;i<this.candidateUsers.getSize();i++){
				var user = this.candidateUsers.get(i);
				if(i!=this.candidateUsers.getSize()-1){
					express=express+user.userId+',';
				}else{
					express=express+user.userId;
				}
			}
		}
		xml=xml+'<extensionElements>\n';
		//添加任务节点监听器
		xml=xml+"<activiti:taskListener class=\"com.gdsp.platform.workflow.helper.listener.NotificationTaskListener\" event=\"create\" >" +
				"</activiti:taskListener>";
		var nodifyListenerXML = this.getNodifyListenerXML();
		if(nodifyListenerXML!=""){
			xml=xml+nodifyListenerXML;
		}
		xml=xml+"<activiti:taskListener class=\"com.gdsp.platform.workflow.helper.listener.MultiInstanceTaskListener\" event=\"complete\" >"+
				"</activiti:taskListener>";
		xml=xml+'</extensionElements>\n';
		
		xml=xml+'<multiInstanceLoopCharacteristics ';
		if(this.isSequential!=null)
			xml=xml+'isSequential="'+this.isSequential+'" ';
		//if(this._elementVariable!=null&&this._elementVariable!='')
		xml=xml+'activiti:elementVariable="assignee" ';
		//if(this._collection!=null&&this._collection!='')
		//固定设置参与人为users，执行下一节点为会签时设置流程变量users
		xml=xml+'activiti:collection="#{users}" ';
		xml=xml+'>\n';
		//if(this._loopCardinality!=null&&this._loopCardinality!='')
		//执行下一节点为会签时设置流程变量counts,会签实例数量,自动默认所有参数人的数量
		xml=xml+'<loopCardinality>#{couts}</loopCardinality>\n';
		//if(this._completionCondition!=null&&this._completionCondition!='')
		xml=xml+'<completionCondition>#{Complete==1}</completionCondition>\n';
		xml=xml+'</multiInstanceLoopCharacteristics>\n';
	}else{
		xml=xml+'<extensionElements>\n';
		var nodifyListenerXML = this.getNodifyListenerXML();
		if(nodifyListenerXML!=""){
			xml=xml+nodifyListenerXML;
		}
		//添加任务节点监听器
		xml=xml+"<activiti:taskListener class=\"com.gdsp.platform.workflow.helper.listener.NotificationTaskListener\" event=\"create\" >" +
		        "</activiti:taskListener>";
		xml=xml+'</extensionElements>\n';
	}
	return xml;
};
/**
 * 为扩展事件绑定监听器
 */
draw2d.Task.prototype.getNodifyListenerXML=function(){
	if(this.extendEvent){
		return "<activiti:taskListener class=\""+this.extendEvent+"\" event=\"complete\" >" +
				"</activiti:taskListener>";
	}else{
		return "";
	}
}
draw2d.Task.prototype.getExtensionElementsXML=function(){
	if(this.listeners.getSize()==0)return '';
	var xml = '<extensionElements>\n';
	xml=xml+this.getListenersXML();
	xml=xml+'</extensionElements>\n';
	return xml;
};
draw2d.Task.prototype.getListenersXML=function(){
	var xml = '';
	for(var i=0;i<this.listeners.getSize();i++){
		var listener = this.listeners.get(i);
		xml=xml+listener.toXML();
	}
	return xml;
};

draw2d.Task.prototype.getMainConfigXML=function(){
	return "";
};
draw2d.Task.prototype.toXML=function(){
	return "";
};
draw2d.Task.prototype.formPropertyToJson=function(){
	return "";
};
draw2d.Task.prototype.toJson=function(){
	return "";
};

draw2d.Task.prototype.toBpmnDI=function(){
	var w=this.getWidth();
	var h=this.getHeight();
	var x=this.getAbsoluteX();
	var y=this.getAbsoluteY();
	var xml='<bpmndi:BPMNShape bpmnElement="'+this.taskId+'" id="BPMNShape_'+this.taskId+'">\n';
	xml=xml+'<omgdc:Bounds height="'+h+'" width="'+w+'" x="'+x+'" y="'+y+'"/>\n';
	xml=xml+'</bpmndi:BPMNShape>\n';
	return xml;
};
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta content="text/html; charset=UTF-8" http-equiv="content-type">
	<%@ include file="/gdsp/common/global.jsp"%>
		<%String category=request.getParameter("category");%>
		<%String categoryName=request.getParameter("categoryName");%>	
	<title>流程设计器</title>
	<!-- JQuery EasyUi CSS-->
	<link type="text/css" as="${ctx }" href="${ctx }/gdsp/js/wfdesignerjs/jquery-easyui/themes/default/easyui.css" rel="stylesheet" title="blue">
	<link href="${ctx }/gdsp/js/wfdesignerjs/jquery-easyui/themes/icon.css" type="text/css" rel="stylesheet"/>
	
	<!-- JQuery-->
	<script src="${ctx }/gdsp/js/wfdesignerjs/jquery-1.4.4.min.js" type="text/javascript"></script>
	<!--<script src="${ctx }/gdsp/js/jquery-1.6.min.js" type="text/javascript"></script>-->
	
	<!-- JQuery EasyUi JS-->
	<script src="${ctx }/gdsp/js/wfdesignerjs/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/jquery-easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		
	<!-- JSON JS-->
	<script src="${ctx }/gdsp/js/wfdesignerjs/json2.js" type="text/javascript" ></script>
	
	<!-- framework JS -->
	<script src="${ctx }/gdsp/js/wfdesignerjs/skin.js" type="text/javascript"></script>
	<link href="${ctx }/gdsp/js/wfdesignerjs/designer/designer.css" type="text/css" rel="stylesheet"/>
	<!-- common, all times required, imports -->
	<script src='${ctx }/gdsp/js/wfdesignerjs/draw2d/wz_jsgraphics.js'></script>          
	<script src='${ctx }/gdsp/js/wfdesignerjs/draw2d/mootools.js'></script>          
	<script src='${ctx }/gdsp/js/wfdesignerjs/draw2d/moocanvas.js'></script>                        
	<script src='${ctx }/gdsp/js/wfdesignerjs/draw2d/draw2d.js'></script>

	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/MyCanvas.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/ResizeImage.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/event/Start.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/event/End.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/connection/MyInputPort.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/connection/MyOutputPort.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/connection/DecoratedConnection.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/task/Task.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/task/UserTask.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/task/ManualTask.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/task/ServiceTask.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/task/ScriptTask.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/task/MailTask.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/task/ReceiveTask.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/task/BusinessRuleTask.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/task/CallActivity.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/gateway/ExclusiveGateway.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/gateway/ParallelGateway.js"></script>
	<script src="${ctx }/gdsp/js/wfdesignerjs/designer/designer.js"></script>	
</head>
<script type="text/javascript">

var processDefinitionId="";
var processDefinitionName="";
var processDefinitionVariables="";
var _process_def_provided_listeners="";
var is_open_properties_panel = false;
var task;
var line;
jq(function(){
	try{						
		_task_obj = jq('#task');
		_designer = jq('#designer');
		_properties_panel_obj = _designer.layout('panel','east');
		_properties_panel_obj.panel({
			onOpen:function(){
				is_open_properties_panel = true;
			},
			onClose:function(){
				is_open_properties_panel = false;
			}
		});
		_process_panel_obj = _designer.layout('panel','center');
		_task_context_menu = jq('#task-context-menu').menu({});
		_designer.layout('collapse','east');
		
		jq('.easyui-linkbutton').draggable({
					proxy:function(source){
						var n = jq('<div class="draggable-model-proxy"></div>');
						n.html(jq(source).html()).appendTo('body');
						return n;
					},
					deltaX:0,
					deltaY:0,
					revert:true,
					cursor:'auto',
					onStartDrag:function(){
						jq(this).draggable('options').cursor='not-allowed';
					},
					onStopDrag:function(){
						jq(this).draggable('options').cursor='auto';
					}	
		});
		jq('#paintarea').droppable({
					accept:'.easyui-linkbutton',
					onDragEnter:function(e,source){
						jq(source).draggable('options').cursor='auto';
					},
					onDragLeave:function(e,source){
						jq(source).draggable('options').cursor='not-allowed';
					},
					onDrop:function(e,source){
						//jq(this).append(source)
						//jq(this).removeClass('over');
						var wfModel = jq(source).attr('wfModel');
						var shape = jq(source).attr('iconImg');
						if(wfModel){
							var x=jq(source).draggable('proxy').offset().left;
							var y=jq(source).draggable('proxy').offset().top;
							var xOffset    = workflow.getAbsoluteX();
		                    var yOffset    = workflow.getAbsoluteY();
		                    var scrollLeft = workflow.getScrollLeft();
		                    var scrollTop  = workflow.getScrollTop();
		                  //alert(xOffset+"|"+yOffset+"|"+scrollLeft+"|"+scrollTop);
		                  //alert(shape);
		                    addModel(wfModel,x-xOffset+scrollLeft,y-yOffset+scrollTop,shape);
						}
					}
				});
		//jq('#paintarea').bind('contextmenu',function(e){
			//alert(e.target.tagName);
		//});
	}catch(e){
		alert(e.message);
	};
	jq(window).unload( function () { 
		if(window.opener && window.opener['_list_grid_obj'])window.opener._list_grid_obj.datagrid('reload');
	} );
	//var activitiXML = jq{metaInfo};
	//alert(activitiXML);
	openProcessDef();
});
function setZindex(){
	jq("#task-candidate-win").parent().css("z-Index",10003);
}
function addModel(name,x,y,icon){
	var model = null;
	if(icon!=null&&icon!=undefined){
		model = eval("new draw2d."+name+"('"+icon+"')");
	}else{
		model = eval("new draw2d."+name+"(openTaskProperties)");
	}
	//userTask.setContent("DM Approve");
	model.generateId();
	//var id= task.getId();
	//task.id=id;
	//task.setId(id);
	//task.taskId=id;
	//task.taskName=id;
	//var parent = workflow.getBestCompartmentFigure(x,y);
	//workflow.getCommandStack().execute(new draw2d.CommandAdd(workflow,task,x,y,parent));
	workflow.addModel(model,x,y);
}

function openTaskProperties(t){
	if(!is_open_properties_panel)
		_designer.layout('expand','east');
	task=t;
	if(task.type=="draw2d.UserTask")
		_properties_panel_obj.panel('refresh','${ctx }/gdsp/designer/userTaskProperties.jsp');
}
function openProcessProperties(id){
	//alert(id);
	if(!is_open_properties_panel)
		_designer.layout('expand','east');
	_properties_panel_obj.panel('refresh','${ctx }/gdsp/designer/processProperties.jsp');
}
function openFlowProperties(l){
	//alert(id);
	if(!is_open_properties_panel)
		_designer.layout('expand','east');
	line=l;
	_properties_panel_obj.panel('refresh','${ctx }/gdsp/designer/flowProperties.jsp');
}
function deleteModel(id){
	var task = workflow.getFigure(id);
	workflow.removeFigure(task);
}
function redo(){
	workflow.getCommandStack().redo();
}
function undo(){
	workflow.getCommandStack().undo();
}
//编码及单据验证
function validateCode(){
	//任务编码
	var taskFalg = document.getElementById("taskFalg").value;
	//任务名称
	var taskNameFalg = document.getElementById("taskNameFalg").value;
	//流程编码
	var processCode = document.getElementById("processCode").value;
	//默认单据
	var defaultForm = document.getElementById("defaultForm").value;
	//超时处理方式 
	var dealMethod = document.getElementById("dealMethod").value;
	//超时时长
	var timeout = document.getElementById("timeout").value;
	if(taskFalg=="false"){
		jq.messager.alert('提示','任务编码有误！','info');
	}else if(taskNameFalg=="false"){
		jq.messager.alert('提示','任务名称有误！','info');
	}else if(dealMethod=="false"){
		jq.messager.alert('提示','请选择超时处理方式！','info');
	}else if(timeout=="false"){
		jq.messager.alert('提示','请填写超时时长！','info');
	}else if (processCode=="false") {
		jq.messager.alert('提示','流程编码有误！','info');
	}else if(defaultForm=="false"||defaultForm==""){
		jq.messager.alert('提示','请选择默认单据！','info');
	}else{
		saveProcessDef();
	}
}

//保存流程
function saveProcessDef(){
	var jsonPeroperties = workflow.toJSON();

	var xml = workflow.toXML();
	jq.ajax({
		url:"${ctx }/workflow/model/save.d",
		type: 'POST',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		data:{
			"processDescriptor":xml,
			"processName":workflow.process.name,
			"processVariables":workflow.process.getVariablesJSONObject(),
			//"task":task,
			"key":workflow.process.id,
			"jsonPeroperties":jsonPeroperties
		},
		dataType:'json',
		async:true,
		success:function(data){
			if(data){				
				jq.messager.alert('提示','流程定义保存成功!','info');				
				setTimeout("closeIndex()",2000);
				
			}else{
				jq.messager.alert('提示','流程定义保存失败!','error');				
			}
		},
		error:function(){			
			jq.messager.alert('提示','异常！','info');								
			return "";
		}
	}); 
	
}





function closeIndex() {	
	window.opener.location.reload();	
	window.close();	
}

function exportProcessDef(obj){
	//obj.href="/example-workflow/workflow/model/save?procdefId="+processDefinitionId+"&processName="+processDefinitionName;
}
//流程模型回显
function parseProcessDescriptor(data){
	var descriptor = jq(data);
	var definitions = descriptor.find('definitions');
	var process = descriptor.find('process');
	var startEvent = descriptor.find('startEvent');
	var endEvent = descriptor.find('endEvent');
	var userTasks = descriptor.find('userTask');
	var exclusiveGateway = descriptor.find('exclusiveGateway');
	var parallelGateway = descriptor.find('parallelGateway');
	var lines = descriptor.find('sequenceFlow');
	var shapes = descriptor.find('bpmndi\\:BPMNShape,BPMNShape');
	var edges = descriptor.find('bpmndi\\:BPMNEdge,BPMNEdge');
	
	workflow.process.category=definitions.attr('targetNamespace');
	workflow.process.id="process"+Sequence.create();
	workflow.process.name="process"+Sequence.create();
	
	var documentation = trim(descriptor.find('process > documentation').text());
	if(documentation != null && documentation != "")
		workflow.process.documentation=documentation;
	var extentsion = descriptor.find('process > extensionElements');
	if(extentsion != null){
		var listeners = extentsion.find('activiti\\:executionListener,executionListener');
		var taskListeners = extentsion.find('activiti\\:taskListener,taskListener');
		workflow.process.setListeners(parseListeners(listeners,"draw2d.Process.Listener","draw2d.Process.Listener.Field"));
	}
	jq.each(processDefinitionVariables,function(i,n){
			var variable = new draw2d.Process.variable();
			variable.name=n.name;
			variable.type=n.type;
			variable.scope=n.scope;
			variable.defaultValue=n.defaultValue;
			variable.remark=n.remark;
			workflow.process.addVariable(variable);
		});
	startEvent.each(function(i){
			var start = new draw2d.Start("${ctx }/gdsp/js/wfdesignerjs/designer/icons/type.startevent.none.png");
			start.id=jq(this).attr('id');
			start.eventId=jq(this).attr('id');
			start.eventName=jq(this).attr('name');
			shapes.each(function(i){
				var id = jq(this).attr('bpmnElement');
				if(id==start.id){
					var x=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('x'));
					var y=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('y'));
					workflow.addFigure(start,x,y);
					return false;
				}
			});
		});
	endEvent.each(function(i){
			var end = new draw2d.End("${ctx }/gdsp/js/wfdesignerjs/designer/icons/type.endevent.none.png");
			end.id=jq(this).attr('id');
			end.eventId=jq(this).attr('id');
			end.eventName=jq(this).attr('name');
			shapes.each(function(i){
				var id = jq(this).attr('bpmnElement');
				if(id==end.id){
					var x=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('x'));
					var y=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('y'));
					workflow.addFigure(end,x,y);
					return false;
				}
			});
		});
	
	userTasks.each(function(i){
		   //发起人不需要配置属性
			var task = new draw2d.UserTask();
			var tid = jq(this).attr('id');
			task.id=tid;
			var tname = jq(this).attr('name');
			var assignee=jq(this).attr('activiti:assignee');
			var candidataUsers=jq(this).attr('activiti:candidateUsers');
			var candidataGroups=jq(this).attr('activiti:candidateGroups');
			var formKey=jq(this).attr('activiti:formKey');
			if(assignee!=null&&assignee!=""){
				task.isUseExpression=true;
				task.performerType="assignee";
				task.expression=assignee;
			}else if(candidataUsers!=null&&candidataUsers!=""){
				task.isUseExpression=true;
				task.performerType="candidateUsers";
				task.expression=candidataUsers;
			}else if(candidataGroups!=null&&candidataGroups!=""){
				task.isUseExpression=true;
				task.performerType="candidateGroups";
				task.expression=candidataGroups;
			}
			if(formKey!=null&&formKey!=""){
				task.formKey=formKey;
			}
			var documentation = trim(jq(this).find('documentation').text());
			if(documentation != null && documentation != "")
				task.documentation=documentation;
			task.taskId=tid;
			task.taskName=tname;
			if(tid!= tname)
				task.setContent(tname);
			var listeners = jq(this).find('extensionElements').find('activiti\\:taskListener,taskListener');
			task.setListeners(parseListeners(listeners,"draw2d.Task.Listener","draw2d.Task.Listener.Field"));
			var performersExpression = jq(this).find('potentialOwner').find('resourceAssignmentExpression').find('formalExpression').text();
			if(performersExpression.indexOf('user(')!=-1){
				task.performerType="candidateUsers";
			}else if(performersExpression.indexOf('group(')!=-1){
				task.performerType="candidateGroups";
			}
			var performers = performersExpression.split(',');
			jq.each(performers,function(i,n){
				var start = 0;
				var end = n.lastIndexOf(')');
				if(n.indexOf('user(')!=-1){
					start = 'user('.length;
					var performer = n.substring(start,end);
					task.addCandidateUser({
							sso:performer
					});
				}else if(n.indexOf('group(')!=-1){
					start = 'group('.length;
					var performer = n.substring(start,end);
					task.addCandidateGroup(performer);
				}
			});
			shapes.each(function(i){
				var id = jq(this).attr('bpmnElement');
				if(id==task.id){
					var x=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('x'));
					var y=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('y'));
					workflow.addModel(task,x,y);
					return false;
				}
			});
		});
	exclusiveGateway.each(function(i){
			var gateway = new draw2d.ExclusiveGateway("${ctx }/gdsp/js/wfdesignerjs/designer/icons/type.gateway.exclusive.png");
			var gtwid = jq(this).attr('id');
			var gtwname = jq(this).attr('name');
			gateway.id=gtwid;
			gateway.gatewayId=gtwid;
			gateway.gatewayName=gtwname;
			shapes.each(function(i){
				var id = jq(this).attr('bpmnElement');
				if(id==gateway.id){
					var x=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('x'));
					var y=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('y'));
					workflow.addModel(gateway,x,y);
					return false;
				}
			});
		});
	parallelGateway.each(function(i){
		var gateway = new draw2d.ExclusiveGateway("${ctx }/gdsp/js/wfdesignerjs/designer/icons/type.gateway.parallel.png");
		var gtwid = jq(this).attr('id');
		var gtwname = jq(this).attr('name');
		gateway.id=gtwid;
		gateway.gatewayId=gtwid;
		gateway.gatewayName=gtwname;
		shapes.each(function(i){
			var id = jq(this).attr('bpmnElement');
			if(id==gateway.id){
				var x=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('x'));
				var y=parseInt(jq(this).find('omgdc\\:Bounds,Bounds').attr('y'));
				workflow.addModel(gateway,x,y);
				return false;
			}
		});
	});
	lines.each(function(i){
			var lid = jq(this).attr('id');
			var name = jq(this).attr('name');
			var condition=jq(this).find('conditionExpression').text();
			var sourceRef = jq(this).attr('sourceRef');
			var targetRef = jq(this).attr('targetRef');
			var source = workflow.getFigure(sourceRef);
			var target = workflow.getFigure(targetRef);
			edges.each(function(i){
					var eid = jq(this).attr('bpmnElement');
					if(eid==lid){
						var startPort = null;
						var endPort = null;
						var points = jq(this).find('omgdi\\:waypoint,waypoint');
						var startX = jq(points[0]).attr('x');
						var startY = jq(points[0]).attr('y');
						var endX = jq(points[1]).attr('x');
						var endY = jq(points[1]).attr('y');
						var sports = source.getPorts();
						for(var i=0;i<sports.getSize();i++){
							var s = sports.get(i);
							var x = s.getAbsoluteX();
							var y = s.getAbsoluteY();
							if(x == startX&&y==startY){
								startPort = s;
								break;
							}
						}
						var tports = target.getPorts();
						for(var i=0;i<tports.getSize();i++){
							var t = tports.get(i);
							var x = t.getAbsoluteX();
							var y = t.getAbsoluteY();
							if(x==endX&&y==endY){
								endPort = t;
								break;
							}
						}
						if(startPort != null&&endPort!=null){
							var cmd=new draw2d.CommandConnect(workflow,startPort,endPort);
							var connection = new draw2d.DecoratedConnection();
							connection.id=lid;
							connection.lineId=lid;
							connection.lineName=name;
							if(lid!=name)
								connection.setLabel(name);
							if(condition != null && condition!=""){
								connection.condition=condition;
							}
							cmd.setConnection(connection);
							workflow.getCommandStack().execute(cmd);
						}
						return false;
					}
				});
		});
	if(typeof setHightlight != "undefined"){
		setHightlight();
	}
}
function parseListeners(listeners,listenerType,fieldType){
	var parsedListeners = new draw2d.ArrayList();
	listeners.each(function(i){
		var listener = eval("new "+listenerType+"()");
		
		listener.event=jq(this).attr('event');
		var expression = jq(this).attr('expression');
		var clazz = jq(this).attr('class');
		if(expression != null && expression!=""){
			listener.serviceType='expression';
			listener.serviceExpression=expression;
		}else if(clazz != null&& clazz!=""){
			listener.serviceType='javaClass';
			listener.serviceExpression=clazz;
		}
		var fields = jq(this).find('activiti\\:field,field');
		fields.each(function(i){
			var field = eval("new "+fieldType+"()");
			field.name=jq(this).attr('name');
			//alert(field.name);
			var string = jq(this).find('activiti\\:string,string').text();
			var expression = jq(this).find('activiti\\:expression,expression').text();
			//alert("String="+string.text()+"|"+"expression="+expression.text());
			if(string != null && string != ""){
				field.type='string';
				field.value=string;
			}else if(expression != null && expression!= ""){
				field.type='expression';
				field.value=expression;
			}
			listener.setField(field);
		});
		parsedListeners.add(listener);
	});
	return parsedListeners;
}
</script>
<body id="designer" class="easyui-layout">
	<div region="west" split="true" title="流程元素" style="width:150px;">
		<div class="easyui-accordion" fit="true" border="false">
			<!--<div id="event" title="事件" iconCls="palette-menu-icon" class="palette-menu">
					<a href="##" class="easyui-linkbutton" plain="true" iconCls="start-event-icon">开始</a><br>
					<a href="##" class="easyui-linkbutton" plain="true" iconCls="end-event-icon">结束</a><br>
				</div> -->
				<div id="task" title="任务" iconCls="palette-menu-icon" selected="true" class="palette-menu">
					<a href="##" class="easyui-linkbutton" plain="true" iconCls="user-task-icon" wfModel="UserTask">用户任务</a><br>
				</div>
				<div id="gateway" title="网关" iconCls="palette-menu-icon" class="palette-menu">
					<a href="##" class="easyui-linkbutton" plain="true" iconCls="parallel-gateway-icon" wfModel="ParallelGateway" iconImg="${ctx }/gdsp/js/wfdesignerjs/designer/icons/type.gateway.parallel.png">并行网关</a><br>
					<a href="##" class="easyui-linkbutton" plain="true" iconCls="exclusive-gateway-icon" wfModel="ExclusiveGateway" iconImg="${ctx }/gdsp/js/wfdesignerjs/designer/icons/type.gateway.exclusive.png">排他网关</a><br>
				</div>
		</div>
	</div>
	<div id="process-panel" region="center" split="true"  iconCls="process-icon" title="流程">
		<div id="process-definition-tab">
			<div id="designer-area" title="设计" style="POSITION: absolute;width:100%;height:100%;padding: 0;border: none;overflow:auto;">
				<div id="paintarea" style="position: absolute;width: 3000px;height: 3000px" ></div>
			</div>
			<div id="xml-area" title="源码" style="width:100%;height:100%;overflow:hidden;overflow-x:hidden;overflow-y:hidden;">
				<textarea id="descriptorarea" rows="38" style="width: 100%;height:100%;padding: 0;border: none;" readonly="readonly"></textarea>
			</div>
		</div>
	<script type="text/javascript">
		<!--
		var workflow;
		jq('#process-definition-tab').tabs({
			fit:true,
			onSelect:function(title){
				if(title=='设计'){
					
				}else if(title=='源码'){
					jq('#descriptorarea').val(workflow.toXML());
				}
			}
		});
		function openProcessDef(){
			jq.ajax({
				url:"${ctx }/workflow/model/getDefaultProcessXml.d",
				type: 'POST',
				data : {
					//processId : id
				},
				dataType:'xml',
				error:function(){
					$.messager.alert("<s:text name='label.common.error'></s:text>","System Error","error");
					return "";
				},
				success: function(data)
				{
					parseProcessDescriptor(data);
				}
			}); 
		}
	
		function createCanvas(disabled){
			try{				
				//initCanvas();
				workflow  = new draw2d.MyCanvas("paintarea");
				workflow.scrollArea=document.getElementById("designer-area");
				if(disabled)
					workflow.setDisabled();
				if(typeof processDefinitionId != "undefined" && processDefinitionId != null &&  processDefinitionId != "null" && processDefinitionId != "" && processDefinitionId != "NULL"){
					openProcessDef();
				}else{
						//var id = "process"+Sequence.create();
						//var id = workflow.getId();
						workflow.process.category='www.activiti.org';
						//workflow.process.id=id;
						//workflow.process.name=id;
						workflow.process.flowcategory=<%=category%>;
						workflow.process.flowcategoryname="<%=categoryName%>";						
					 // Add the start,end,connector to the canvas
					  /* var startObj = new draw2d.Start("${ctx }/gdsp/js/designer/icons/type.startevent.none.png");
					  startObj.setId("start");
					  workflow.addFigure(startObj, 200,50);
					  
					  var endObj   = new draw2d.End("${ctx }/gdsp/js/designer/icons/type.endevent.none.png");
					  endObj.setId("end");
					  workflow.addFigure(endObj,200,400); */
				} 
			}catch(e){
				alert(e.message);
			}
		}
		//-->
	</script>
	</div>
	<div id="properties-panel" region="east" split="true" iconCls="properties-icon" title="流程属性" style="width:315px;">
	</div>
	<!-- 工具条 -->
	<div id="toolbar-panel" region="north" border="false" style="height:29px;background:#25ABF4;">
	   <img style="cursor: pointer;margin-left: 5px;margin-top: 5px;" height="18" width="20" onclick="validateCode()" src="${ctx }/gdsp/images/tools/save.png" title="保存流程" />
	   <img style="cursor: pointer;margin-left: 5px;margin-top: 5px;" height="18" width="20" onclick="undo()" src="${ctx }/gdsp/images/tools/back.png" title="撤消" />
	   <img style="cursor: pointer;margin-left: 5px;margin-top: 5px;" height="18" width="20" onclick="redo()" src="${ctx }/gdsp/images/tools/next.png" title="恢复" />
	   <!-- img style="cursor: pointer;margin-left: 5px;margin-top: 5px;" height="18" width="20" onclick="exportProcessDef(this)" src="${ctx }/gdsp/image/tools/printer.png" title="导出" /> -->
	</div>
	<!-- 菜单右键 -->
	<div id="task-context-menu" class="easyui-menu" style="width:120px;">
		<div id="properties-task-context-menu" iconCls="properties-icon">属性</div>
		<div id="delete-task-context-menu" iconCls="icon-remove">删除</div>
	</div>
	<!-- 表单配置 -->
	<div id="form-win" title="Form Configuration" style="width:750px;height:500px;">
	</div>
	<!-- candidate configuration window -->
	<div id="task-candidate-win" title="" style="width:750px;height:500px;">
	</div>
	<div id="win" class="easyui-window" title="My Window" closed="true" 
		style="width:600px;height:350px;padding:5px;">		
	</div>
</body>
</html>
<script type="text/javascript">
<!--
	createCanvas(false);
//-->
</script>
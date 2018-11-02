<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ include file="/gdsp/common/global.jsp"%>
<script type="text/javascript">

	jq(function() {
		//loadEvent();
		loadForm();
		loadTaskGeneral();

	});

	function loadEvent() {
		var c = new Option("", "");
		document.getElementById("extendEvent").options.add(c);

		var jsonDef = "";
		jq.ajax({
			url : "${ctx }/workflow/model/getEvent.d",
			type : 'POST',
			async : false,
			//dataType:'json',
			error : function(data) {
				return "";
			},
			success : function(data) {
				jsonDef = data;
			}
		});

		var json = eval('(' + jsonDef + ')');
		for (var i = 0; i < json.events.length; i++) {
			var a = new Option();
			a.value = json.events[i].code;
			a.text = json.events[i].name;
			document.getElementById("extendEvent").options.add(a);
		}

	}
	function loadForm() {
		var c = new Option("", "");
		document.getElementById("form").options.add(c);
		var jsonDef = "";
		jq.ajax({
			url : "${ctx }/workflow/model/getForm.d",
			type : 'POST',
			async : false,
			//dataType:'json',
			error : function(data) {
				return "";
			},
			success : function(data) {
				jsonDef = data;
			}
		});
		var json = eval('(' + jsonDef + ')');
		for (var i = 0; i < json.forms.length; i++) {
			var a = new Option();
			a.value = json.forms[i].code;
			a.text = json.forms[i].name;
			document.getElementById("form").options.add(a);
		}
	}

	function saveTaskGeneral() {
		task.taskId = jq('#id').val();
		task.taskName = jq('#name').val();
		task.setContent(jq('#name').val());

		task.form = jq('#form').val();

		task.systeminform = true;
		task.emailinform = false;
		if (!jq('#systeminform').attr('checked')) {
			task.systeminform = false;
		}

		if (jq('#emailinform').attr('checked')) {
			task.emailinform = true;
		}

		task.overtime = jq('#overtime').val();

		task.unit = jq('#unit').val();

		task.workingday = false;
		if (jq('#workingday').attr('checked')) {
			task.workingday = true;
		}

		if (jq('#informPerson').attr('checked')) {
			task.dealMethod = 'informPerson';
		} else if (jq('#autoPass').attr('checked')) {
			task.dealMethod = 'autoPass';
		} else if (jq('#autoFail').attr('checked')) {
			task.dealMethod = 'autoFail';
		} else if (jq('#autoReturn').attr('checked')) {
			task.dealMethod = 'autoReturn';
		} else if (jq('#autoReturnPerson').attr('checked')) {
			task.dealMethod = 'autoReturnPerson';
		}

		task.extendEvent = jq('#extendEvent').val();
		
		//超时时间不为空，则处理方式必须选中一项
		if((task.overtime!= "")&&(task.dealMethod == null)){
			document.getElementById("dealMethod").value="false";
		}else{
			document.getElementById("dealMethod").value="true";
		}
		//处理方式不为空，则必须填写超时时间
		if((task.overtime == "")&&(task.dealMethod != null)){
			document.getElementById("timeout").value="false";
		}else{
			document.getElementById("timeout").value="true";
		}
		/* if (jq('#asynchronous').attr('checked')) {
			task.asynchronous = true;
		} else {
			task.asynchronous = false;
		}
		if (!jq('#exclusive').attr('checked')) {
			task.exclusive = false;
		} else {
			task.exclusive = true;
		} */
	}
	
	function loadTaskGeneral() {
		jq('#id').val(task.taskId);
		jq('#name').val(task.taskName);

		jq('#form').val(task.form);

		if (task.systeminform == "true") {
			jq('#systeminform').attr('checked', true);
		}

		if (task.emailinform == "true") {
			jq('#emailinform').attr('checked', true);
		}

		jq('#overtime').val(task.overtime);

		jq('#unit').val(task.unit);

		if (task.workingday) {
			jq('#workingday').attr('checked', true);
		} else {
			jq('#workingday').attr('checked', false);
		}

		if (task.dealMethod == 'informPerson') {
			jq('#informPerson').attr('checked', true);
		}
		if (task.dealMethod == 'autoPass') {
			jq('#autoPass').attr('checked', true);
		}
		if (task.dealMethod == 'autoFail') {
			jq('#autoFail').attr('checked', true);
		}
		if (task.dealMethod == 'autoReturn') {
			jq('#autoReturn').attr('checked', true);
		}
		if (task.dealMethod == 'autoReturnPerson') {
			jq('#autoReturnPerson').attr('checked', true);
		}

		jq('#extendEvent').val(task.extendEvent);

		/* if (task.asynchronous) {
			jq('#asynchronous').attr('checked', true);
		} else {
			jq('#asynchronous').attr('checked', false);
		}
		if (!task.exclusive) {
			jq('#exclusive').attr('checked', false);
		} else {
			jq('#exclusive').attr('checked', true);
		} */
	}
	//验证任务编码是否正确
	function valiPropertiesId(id){
		var taskFalg = document.getElementById('taskFalg').value;
		var taskIds = ','+document.getElementById('taskIds').value;
		document.getElementById('taskPropertiesMessage').innerHTML = " ";
		if(!id.match(/^[A-Za-z]/)){
			document.getElementById('taskFalg').value = "false";
			document.getElementById('taskPropertiesMessage').innerHTML = '<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编码必须以字母开头！</font>';
		}else{
			document.getElementById('taskFalg').value = "true";
			var currentId = ','+document.getElementById('id').value+',';
			//判断id是否存在，大于-1则存在
			if(taskIds.indexOf(currentId)>-1){
				document.getElementById('taskFalg').value = "false";
				document.getElementById('taskPropertiesMessage').innerHTML = '<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编码重复，请修改！</font>';
			}else{
				document.getElementById('taskFalg').value = "true";
				document.getElementById('taskIds').value += document.getElementById('id').value+',';
				
			}
		}
	}
	//验证任务名称是否重复
	function valiPropertiesName(name){
		var taskNames = ','+document.getElementById('taskNames').value;
		document.getElementById('taskPropertiesMessageName').innerHTML = " ";
		var currentName = ','+document.getElementById('name').value+',';
		//判断name是否存在，大于-1则存在
		if(taskNames.indexOf(currentName)>-1){
			document.getElementById('taskNameFalg').value = "false";
			document.getElementById('taskPropertiesMessageName').innerHTML = '<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名称重复，请修改！</font>';
		}else{
			document.getElementById('taskNameFalg').value = "true";
			document.getElementById('taskNames').value += document.getElementById('name').value+',';
		}		
	}

</script>

<style>
<!--
-->
table td{
	height: 20px;
}

table  p {
	width: 80px;
	text-align: right;
	margin-top: 5px;
	margin-bottom: 5px;
}

table input{
	border-radius:3px;
	background-color: #f6f6f6;
	border: 1px solid #e4e4e4; 
	margin-left: 3px;
}
.textbox{
	height:22px;
	width:180px;
}
table select{
	height:26px;
	border-radius:3px;
	background-color: #f6f6f6;
	border: 1px solid #e4e4e4; 
	margin-left: 3px;
}
#form{
	width:185px;
}
#systeminform{
	margin-top:9px;
}

#timeout{
	width:50px;
}
.timeformat{
	width:40px;
}
.inform{
	margin-top: -6px;
}
.listlabel{
	margin-top: -50px;
}
</style>
<table id="general-properties" style="margin-top: 10px;">
	<tr  height="24px;">
		<td><p class="label">任务编码:</p></td>
		<td><input type="text" id="id" class="textbox" value="" onBlur="valiPropertiesId(this.value);" onchange="saveTaskProperties();" /></td>
	</tr>
	<tr id="taskPropertiesId">
		<td colspan="2">
			<span id="taskPropertiesMessage">
		</span></td>
	</tr>
	<tr>
		<td><p class="label">任务名称:</p></td>
		<td><input type="text" id="name" class="textbox" value="" onBlur="valiPropertiesName(this.value);" onchange="saveTaskProperties();" /></td>
	</tr>
	<tr id="taskPropertiesName">
		<td colspan="2">
			<span id="taskPropertiesMessageName">
		</span></td>
	</tr>
	<tr style="display: none;">
		<td><p class="label">任务单据:</p></td>
		<td>
			<select  id="form"  dataType="Require" msg="请选择任务单据!" onchange="saveTaskProperties();"></select>
		</td>
	</tr>
	<tr>
		<td><p class="inform">通知处理人:</p></td>
	<!-- </tr>
	<tr> -->
		<!-- <td><input type="hidden" name="sdfdf" style="text-align: right"value="" /></td> -->
		<td>
			<input type="checkbox" id="systeminform" name="systeminform" value="" checked="true" 
						onchange="saveTaskProperties();" />系统通知<br>
						<!-- 暂未实现该部分功能 -->
					<!-- <input type="checkbox" id="emailinform" name="emailinform" value=""
						onchange="saveTaskProperties();" />邮件通知<br> -->
		</td>
	</tr>
	<tr>
		<td align="left">
			<p >超时时间:</p>
		</td>
		<td>
			<input  type="text" id="overtime" style="width:50px;" name="timeout" class="textbox" value="" onchange="saveTaskProperties();" /> 
			<select name="unit" id="unit" class="timeformat" onchange="saveTaskProperties();">
			<!-- 暂时仅支持以"天"为频度单位 -->
				<!-- <option value="min">分</option>
				<option value="hour">时</option> -->
				<option value="day">天</option>
			</select> 
			<input type="checkbox" id="workingday" name="workingday" value="" 
				onchange="saveTaskProperties();" />工作日
		</td>
	</tr>
	<tr>
		<td>
			<p class="listlabel">处理方式:</p>
		</td>
		<td>
			<input type="radio" id="informPerson" name="dealMethod" value="" 
				onchange="saveTaskProperties();" />通知待办人<br>
			<input type="radio" id="autoPass" name="dealMethod" value=""
				onchange="saveTaskProperties();" />自动通过<br> 
			<input type="radio" id="autoFail" name="dealMethod" value=""
				onchange="saveTaskProperties();" />自动不通过<br>
			<input type="radio" id="autoReturn" name="dealMethod" value=""
				onchange="saveTaskProperties();" />自动驳回上级<br> 
			<input type="radio" id="autoReturnPerson" name="dealMethod" value=""
				onchange="saveTaskProperties();" />自动驳回发起人<br>
		</td>
	</tr>
	<tr>
		<td>
			<p>扩展事件:</p>
		</td>
		<td>
			<input  type="text" id="extendEvent" class="textbox" name="extendEvent" onchange="saveTaskProperties();" />
		</td>
	</tr>

	<!-- <tr>
		<td align="left">
			<p style="width: 70px;text-align: left;font-size: 14px;">异步:</p>
		</td>
		<td><input type="checkbox" id="asynchronous" name="asynchronous"
			value="true" /></td>
	</tr> -->
	<!-- <tr>
		<td align="left">
			<p style="width: 70px;text-align: left;font-size: 14px;">Exclusive:</p>
		</td>
		<td><input type="checkbox" id="exclusive" name="exclusive"
			value="true" checked /></td>
	</tr> -->
</table>
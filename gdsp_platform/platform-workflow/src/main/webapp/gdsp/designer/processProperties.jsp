<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ include file="/gdsp/common/global.jsp"%>
<script type="text/javascript">
var pid = "";
var process = workflow.process;
var processVariablesEditCount = 0;
/* var Request = new Object();
Request = GetRequest();
var formType = Request['']; */
jq(function(){	
	loadForm();
	populateProcessProperites();
});
function loadForm(){
	var c=new Option("","");
	document.getElementById("form").options.add(c);
	var jsonDef = "";
	jq.ajax({
		url:"${ctx }/workflow/model/getForm.d",
		type: 'POST',
		async: false,
		//dataType:'json',
		error : function(data) {
			return "";
		},
		success : function(data)
		{
			jsonDef=data;
		}
	});
	var json =eval('('+jsonDef+')');
	for(var i=0;i<json.forms.length;i++){
		var a=new Option();
		a.value=json.forms[i].code;
		a.text=json.forms[i].name;
		document.getElementById("form").options.add(a);
	}
}
//保存流程属性
function saveProcessProperties(){
	process.id=jq('#id').val();
	process.name=jq('#name').val();
	process.category=jq('#category').val();
	process.documentation=jq('#documentation').val();

	process.form=jq('#form').val();
	process.flowcategory=jq('#flowcategory').val();
	process.flowcategoryname=jq('#flowcategoryname').val();
	process.tableID=jq('#tableID').val();
	//jq.messager.alert('提示','保存成功!','info');
}
//回显流程属性
function populateProcessProperites(){
	jq('#id').val(process.id);
	jq('#name').val(process.name);
	jq('#category').val(process.category);
	jq('#documentation').val(process.documentation);
	
	jq('#form').val(process.form);
	jq('#flowcategory').val(process.flowcategory);
	jq('#flowcategoryname').val(process.flowcategoryname);
	jq('#tableID').val(process.tableID);	
	
	if(process.tableID != null && process.tableID!='') {
		jq('#id').attr('readonly','true');
		jq('#id').removeAttr('onBlur');
		jq('#validateTR').remove();
		jq('#name').attr('readonly','true');
		
	}
	//loadProcessListeners();
	//loadProcessVariables();
}
//编码唯一性校验及命名规则验证
function validate(val) {
	var valiMess = document.getElementById('validateMessage');
	if (!(val.match(/^\S+$/))) {
		valiMess.innerHTML = '<font color="red">请输入编码</font>';
		document.getElementById('processCode').value = false;
	} else {
		jq.post('${ctx }/workflow/model/validateCode.d', {codeValue: val}, function(data){
			valiMess.innerHTML = data;
			if(data == "<font color=\"red\">编码存在！</font>"||data == "<font color=\"red\">编码必须以字母或下划线字符开头，且不包含冒号 ！</font>") {
				document.getElementById('processCode').value = false;
			}else{
				document.getElementById('processCode').value = true;
			}
		});
	}
}
//光标聚焦事件
function setval() {
	var valiMess = document.getElementById('validateMessage');
	valiMess.innerHTML = '';
}
//默认单据
function valiform(form){
	var valiformMess = document.getElementById('valiformMessage');
	if(form==""){
		valiformMess.innerHTML = '<font color="red">请选择默认单据！</font>';
		document.getElementById('defaultForm').value = false;
	}else{
		document.getElementById('defaultForm').value = true;
		valiformMess.innerHTML = '';
	}
}
function setform() {
	var valiformMess = document.getElementById('valiformMessage');
	valiformMess.innerHTML = '';
}



</script>
<div id="process-properties-layout" class="easyui-layout" fit="true">
	<!-- <div id="task-properties-toolbar-panel" region="north" border="false" style="height:30px;background:#E1F0F2;">
		<a href="##" id="sb2" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="saveProcessProperties()">保存</a>
	</div> -->
	<div id="process-properties-panel" region="center" border="true">
		<div class="easyui-accordion" fit="true" border="false">
			<div id="general" title="流程属性面板" selected="true" class="properties-menu">
				<table id="general-properties">
				   <tr>
						<td align="right">
							<p style="width: 70px;text-align: left;font-size: 14px;">流程分类:</p>
						</td>
						<td><input style="width: 200px;background: #eeeeee;" type="text" id="flowcategoryname" name="flowcategoryname" size="50" value="" readonly=true/></td>
					    <td><input type="hidden" id="flowcategory" name="flowcategory"/></td>
					    <td><input type="hidden" id="tableID" name="tableID"/></td>
					</tr>
					<tr>
						<td align="right">
							<p style="width: 70px;text-align: left;font-size: 14px;">流程编码:</p>
						</td>
						<td><input style="width: 200px;" type="text" id="id" name="id" size="50" value="" onBlur="validate(this.value);" onfocus="setval(this.id)" onchange="saveProcessProperties();" /></td>
					</tr>
					<tr id="validateTR">
						<td colspan="2">
							<span id="validateMessage"></span>
						</td>
					<tr>
						<td align="right">
							<p style="width: 70px;text-align: left;font-size: 14px;">流程名称:</p>
						</td>
						<td><input style="width: 200px;" type="text" id="name" name="name" size="50" value="" onchange="saveProcessProperties();"/></td>
					</tr>
					
					<tr>
						<td align="left">
							<p style="width: 70px;text-align: left;font-size: 14px;">默认单据:</p>
						</td>
						<td><select name="form" id="form" style="width: 200px;" dataType= "Require" msg ="请选择任务单据!" onBlur="valiform(this.value);" onfocus="setform(this.id)" onchange="saveProcessProperties();"></select>
						</td>
					</tr>
					<tr id="valiformTR">
						<td colspan="2">
							<span id="valiformMessage"></span>
						</td>
					<tr>
					
					<!-- <tr>
						<td align="right">
							<p style="width: 70px;text-align: left;font-size: 14px;">命名空间:</p>
						</td>
						<td><input style="width: 200px;" type="text" id="category" name="category" size="50" value=""/></td>
					</tr> -->
					<tr>
						<td align="right">
							<p style="width: 70px;text-align: left;font-size: 14px;">备注:</p>
						</td>
						<td><textarea style="width: 200px;" id="documentation" name="documentation" cols="1" rows="1" onchange="saveProcessProperties();"></textarea></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/gdsp/common/global.jsp"%>
<script type="text/javascript">
	//var fid = "";
	//var line = workflow.getLine(fid);
	jq(function() {
		jq('#formType').combobox({    
		    url:'${ctx }/workflow/model/getForm.d?type=datagrid',    
		    method:'post',
			valueField:'code',
			textField:'name',
			panelHeight:'auto',
			editable:false,
			onSelect: function(rec){   
	            var url = '${ctx }/workflow/model/getFormVariable.d?formId='+rec.code;
	            jq('#formVar').combobox('clear'); 
	            jq('#formVar').combobox('reload', url);    
	        }
		});  
		jq('#formVar').combobox({    
		    method:'post',
			valueField:'code',
			textField:'name',
			panelHeight:'auto',
			editable:false
		}); 
		populateLineProperites();
		//alert("after1");
	});
	//保存连线属性
	function saveLineProperties() {
		line.lineId = jq('#id').val();
		line.lineName = jq('#name').val();
		line.setLabel(jq('#name').val());
		line.condition = jq('#condition').val();
		//jq.messager.alert('提示', '保存成功!', 'info');
	}
	//回显连线属性
	function populateLineProperites() {
		jq('#id').val(line.lineId);
		jq('#name').val(line.lineName);

		jq('#condition').val(
				(line.condition).replace(/[ ]/g, "").replace(/[\r\n]/g, ""));
		//loadLineListeners();
	}
	//拼接，弹出窗体控制
	
	function showdiv() {
		test_win = jq('#win').window({
			href:'',   
			width:630,
			closed : true,
			cache:false,
			draggable : true,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			modal : false,
			shadow : true
		});
		test_win.window("open");
		test_win.window("setTitle","添加表达式");
		test_win.window("refresh","${ctx }/gdsp/designer/addExpression.jsp");
	}
</script>
<style type="text/css" >

table{
	margin-buttom: 10px;
	}
table input{
	border-radius: 3px;
	background-color: #F6F6F6;
	border: 1px solid #E4E4E4;
	
}
.ntitle{
	color: #666; 
	font-weight: bold; 
	font-size: 15px;
	margin-left: 10px;
}
.label{
	width: 70px; 
	text-align: right; 
	font-size: 14px;
	}
.nodeinput{
	border-radius: 3px;
	background-color: #F6F6F6;
	border: 1px solid #E4E4E4;
	width:186px;
	height: 24px;
	margin-bottom: 3px;
	margin-top: 3px;
}
.formvar{
	width:80px;
}
</style>
<!-- <div id="line-properties-layout" class="easyui-layout" fit="true"> -->
	<div id="line-properties-panel" region="center" border="true">
		<div class="easyui-accordion" fit="true" border="false">
			<div id="general" title="连线属性" selected="true" class="properties-menu">
				<div>
					<br /> 
					<span class="ntitle" style="">节点属性</span>
					<br />
					<table id="general-properties" style="margin-left: 20px;">
						<tr>
							<td><span  class="label" style="">分支编码:</span></td>
							<td><input class="nodeinput" style="" type="text" id="id" name="id" value="" onchange="saveLineProperties();"  /></td>
						</tr>
						<tr>
							<td><span class="label" style="">分支名称:</span></td>
							<td><input class="nodeinput" style="" type="text" id="name" name="name"  value=""  onchange="saveLineProperties();"  /></td>
						</tr>
					</table>
					
					<span class="ntitle" style="">单据变量</span>
					<br>
					<table id="main-properties" style="margin-left: 20px;">
						<tr>
							<td><span  class="label" style="">单据类型:</span></td>
							<td ><input class="formvar" name="formType" id="formType" readonly="readonly"/></td>
						</tr>
						<tr>
							<td><span  class="label" style="">变量名称:</span></td>
							<td ><input class="formvar" name="formVar" id="formVar" readonly="readonly"/></td>
						</tr>
					</table>
					
					<span class="ntitle" style="">分支条件</span>
					<table id="main-properties" style="margin-left: 20px;">
						<tr>
							<td>
								<p style="width: 60px;text-align: right;font-size: 14px;
								 margin-top: -50px;"
								>表达式:</p>
							</td>
							<td>
								<textarea style="width: 184px; height: 80px;" 
									onchange="saveLineProperties();" id="condition" name="condition" cols="1" rows="1">
								</textarea>
							</td>
						</tr>
					</table>
					<br>
					<fieldset style="line-height: 21px;">
						<legend>分支条件设置说明</legend>
						<div>1.表达式必须写在\#{}中。</div>
						<div>2.根据单据查看单据下的变量名称进行表达式书写。</div>
						<div>3.表达时候支持&&,||符号,基于UEL表达式。</div>
						<div>4.支持>,<,=等逻辑判断</div>
						<div>5.例如请假单的天数判断：\#{leaveDay>5 && leaveDay<10},表示5-10天的请假会走此分支。</div>
					</fieldset>
					<!-- <div align="center">
						<input type="button" id="btn_Set" name="btn_Set"
								value="设置" onclick="showdiv()" />
					</div> -->
				</div>
			</div>
		</div>
	</div>
</div>

<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	<@c.BoxHeader class="border header-bg">
		<p class="box-title" style="color:#010101;font-size:14px;padding-left:8px;"><#if dataDicVO??&& dataDicVO.id??>修改<#else>添加</#if></p>
	</@c.BoxHeader>
	<@c.BoxBody>
		<@c.Form  id="DataDicForm" class="validate" action="${ContextPath}/conf/datadic/saveDim.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#diclistContent"}>
			<@c.FormIdVersion id="${dataDicVO?if_exists.id?if_exists}" version="${dataDicVO?if_exists.version?default(0)}"/>
        	<@c.FormInput name="dic_code" label="类型编码" value="${dataDicVO?if_exists.dic_code?if_exists}"   validation={"required":true,"minlength":1,"maxlength":20,"remote":"${ContextPath}/conf/datadic/synchroCheck.d?id=${dataDicVO?if_exists.id?if_exists}"} helper="请输入1-20位字符" messages={"remote":"类型编码不能重复，请确认！"}/>
        	<@c.FormInput name="dic_name" label="类型名称" value="${dataDicVO?if_exists.dic_name?if_exists}"  validation={"required":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/conf/datadic/synchroCheck.d?id=${dataDicVO?if_exists.id?if_exists}"} helper="请输入1-60位字符" messages={"remote":"类型名称不能重复，请确认！"}/>
        	<@c.FormText  name="dic_desc" label="类型描述"  validation={"minlength":1,"maxlength":100} >${dataDicVO?if_exists.dic_desc?if_exists}</@c.FormText>
		</@c.Form>
	</@c.BoxBody>
	<@c.BoxFooter class="text-center">
		<@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#DataDicForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.opentab("#mainPanel")]>取消</@c.Button>
	</@c.BoxFooter>
</@c.Box>
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	<@c.BoxHeader>
		<h3 class="box-title"><#if delegate?? &&delegate.id??>修改<#else>新增</#if></h3>
	</@c.BoxHeader>
	<@c.BoxBody>
		<@c.Form id="delegateForm" class="validate" action="${ContextPath}/workflow/delegate/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#delegatesContent"}>
			<@c.FormIdVersion id="${delegateVO?if_exists.id?if_exists}" version="${delegateVO?if_exists.version?default(0)}"/>
			<#-- 以下拉框效果选取
			<@c.FormComboBox name="acceptId" label="代理人" validation={"required":true} value="${delegate?if_exists.acceptId?if_exists}">
				<#list users as user>
					<@c.Option value="${user?if_exists.id?if_exists}">${user?if_exists.userName?if_exists}</@c.Option>
				</#list>
			</@c.FormComboBox>
			-->
			<#-- 以参照框效果选取-->
			<@c.FormRef name="acceptId" label="代理人" validation={"required":true} url="${ContextPath}/workflow/delegate/acceptList.d" value="${delegateVO?if_exists.acceptId?if_exists}" showValue="${acceptName?if_exists}"/>
			<@c.FormDate type="datetime" name="startTime" label="开始时间" value="${delegateVO?if_exists.startTime?if_exists}" validation={"required":true}></@c.FormDate>
			<@c.FormDate type="datetime" name="endTime" label="结束时间" value="${delegateVO?if_exists.endTime?if_exists}" validation={"required":true}></@c.FormDate>
			<br/><br/><br/>
			<#--
			<h5 class="box-title">选取委托流程</h5>
			<#--<@c.SimpleTable checkboxName="deploymentid" checkedfield="ischecked" data=deploymentAltcategory striped=false titles=["流程编号","流程名称","流程类型"] keys=["deploymentCode","deploymentName","categoryName"] checkbox=true/>
			<@c.FormRef name="deploymentid" label="委托流程" validation={"required":true} url="${ContextPath}/workflow/delegate/deploymentList.d?delId=${delegate?if_exists.id?if_exists}" value="${deploymentIdsString?if_exists}" showValue="${deploymentNames?if_exists}"/>
			  -->
		</@c.Form>
	</@c.BoxBody>
	<@c.BoxFooter class="text-center">
		<@c.Button type="primary" icon="fa fa-save" size="sm" action=[c.saveform("#delegateForm")]>保存</@c.Button>
		<@c.Button type="cancel" icon="glyphicon glyphicon-off" size="sm" action=[c.opentab("#mainPanel")]>取消</@c.Button>
	</@c.BoxFooter>
</@c.Box>
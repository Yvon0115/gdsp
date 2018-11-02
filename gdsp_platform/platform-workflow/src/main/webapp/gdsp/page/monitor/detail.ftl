<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
	<@c.Tab id="mainPanel" active=true>
		<@c.Box style="min-height:0px">
			<@c.BoxHeader>
				<@c.Button  size="md" class="pull-right" icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
				<#--<@c.Button title="查看该节点代办人" class="pull-right" size="md" type="primary" icon="glyphicon glyphicon-search "  action=[c.opendlg("#assigneeDlg","${ContextPath}/workflow/monitor/loadAssignee.d?deploymentId=${deploymentId}&actId=${actId}","400px","500px")] >代办人信息</@c.Button>-->
			</@c.BoxHeader>
			<@c.BoxBody>
				<@c.Form  id="dpVOform"  class="validate" action="" >
				    <@c.FormInput name="categoryName" label="流程分类"  readonly=true value="${taskVO?if_exists.categoryName?if_exists}" />
				    <@c.FormInput name="deploymentCode" label="流程编码"  readonly=true value="${taskVO?if_exists.deploymentCode?if_exists}" />
				    <@c.FormInput name="deploymentName" label="流程名称"  readonly=true value="${taskVO?if_exists.deploymentName?if_exists}" />
				    <@c.FormInput name="nodeName" label="当前节点"  readonly=true value="${taskVO?if_exists.nodeName?if_exists}" />
				    <@c.FormInput name="createTime" label="任务创建时间"  readonly=true value="${taskVO?if_exists.createTime?if_exists}" />
				    <@c.FormInput name="dueTime" label="任务持续时间"  readonly=true value="${dueTime?if_exists}" />
				</@c.Form>
			</@c.BoxBody>
		</@c.Box>
		<@c.Box style="min-height:200px">
			<@c.BoxHeader title="办理历史"></@c.BoxHeader>
			<@c.BoxBody>
				<#include "detail-data.ftl">
			</@c.BoxBody>
		</@c.Box>
		<@c.Box style="min-height:200px">
			<@c.BoxHeader title="代办人信息"></@c.BoxHeader>
			<@c.BoxBody>
				<#include "assigneeList-data.ftl">
			</@c.BoxBody>
		</@c.Box>
		
	</@c.Tab>
</@c.Tabs>
<@c.PageData page=phdetail />
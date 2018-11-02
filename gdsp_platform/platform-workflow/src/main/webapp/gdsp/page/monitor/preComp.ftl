<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_taskManage.js"/>-->
<@c.Script src="script/wf_taskManage" />
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="mainPanel2" active=true>
        <@c.Box style="min-height:100px;">
        	<@c.BoxHeader title="表单"></@c.BoxHeader>
        	<@a.Include src="${formURL}"/>
        </@c.Box>
        <@c.Box style="min-height:200px;">
        	<@c.BoxHeader title="办理历史 "></@c.BoxHeader>
        	<@c.SimpleTable striped=false titles=["节点名称","办理人","办理结果","附加意见"] keys=["actName","userId","result","options"] data=hdetail.content/>
			<@c.PageData page=hdetail />
			<@c.Form id="optionForm" class="" action="${ContextPath}/workflow/process/complete.d" after={"switchtab":"#mainPanel","dataloader":"#monitorListContent"} method="get" help=false>
    			<@c.FormInput name="taskId" type="hidden" value="${taskId}"></@c.FormInput>
    			<@c.FormInput name="result" type="hidden" value="default"></@c.FormInput>
    			<@c.FormInput name="formid" type="hidden" value="${formid}"></@c.FormInput>
        		<@c.FormInput name="options" style="width:650px;" value="" label="附加意见：" messages={"required":"附加意见"}></@c.FormInput>
		    </@c.Form>
		    <#if downloadUrl??>
				<@c.Button size="sm" title="下载流程附件" icon="glyphicon glyphicon-download" click="downloadAttachments('${downloadUrl}')" >下载附件</@c.Button>
			</#if>
			<@c.BoxBody class="text-center">
	    		<@c.Button size="sm" type="primary" icon="glyphicon glyphicon-ok" click="ModiResult(7)" >同意</@c.Button>
	            <@c.Button size="sm" icon="glyphicon glyphicon-remove" click="ModiResult(8)" >不同意</@c.Button>
	            <@c.Button size="sm" icon="glyphicon glyphicon-repeat" click="ModiResult(9)" >驳回发起人</@c.Button>
		    	<@c.Button size="sm" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
			</@c.BoxBody>
        </@c.Box>
	    	
    </@c.Tab>
</@c.Tabs>

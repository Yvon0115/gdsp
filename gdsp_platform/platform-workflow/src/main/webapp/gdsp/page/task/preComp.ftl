<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_taskManage.js"/>-->
<@c.Script src="script/wf_taskManage" />
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="mainPanel2" active=true>
        <@c.Box>
        	<@c.BoxHeader title="表单"></@c.BoxHeader>
        	<@a.Include src="${formURL}"/>
    		<@c.BoxBody class="text-center" >
				<@c.Form id="optionForm" class="" action="${ContextPath}/workflow/process/complete.d" after={"switchtab":"#mainPanel","dataloader":"#taskContent"} method="get" help=false>
	    			<@c.FormInput name="taskId" type="hidden" value="${taskId}"></@c.FormInput>
	    			<@c.FormInput name="result" type="hidden" value="default"></@c.FormInput>
	    			<@c.FormInput name="formid" type="hidden" value="${formid}"></@c.FormInput>
	        		<@c.FormInput name="options" value="" label="附加意见：" messages={"required":"附加意见"}></@c.FormInput>
			    </@c.Form>
	    	</@c.BoxBody>
			    <#if downloadUrl??>
					<@c.Button size="sm" title="下载流程附件" icon="glyphicon glyphicon-download" click="downloadAttachments('${downloadUrl}')" >下载附件</@c.Button>
				</#if>
	    	<@c.BoxBody class="text-center">
	    		<@c.Button size="sm" type="primary" icon="glyphicon glyphicon-ok" click="ModiResult(1)" >同意</@c.Button>
	            <@c.Button size="sm" icon="glyphicon glyphicon-remove" click="ModiResult(2)" >不同意</@c.Button>
	            <@c.Button size="sm" icon="glyphicon glyphicon-repeat" click="ModiResult(3)" >驳回发起人</@c.Button>
	            <@c.Button size="sm" icon="glyphicon glyphicon-fast-backward" click="ModiResult(4)" >驳回上一节点</@c.Button>
		    	<@c.Button size="sm" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
			</@c.BoxBody>
	        <@c.BoxHeader title="办理历史 "></@c.BoxHeader>
	    	<#--<@c.SimpleTable striped=false titles=["节点名称","审批人","审批结果","附加意见"] keys=["actName","userId","result","options"] data=hdetail.content/>
			<@c.PageData page=hdetail />-->
			<@c.BoxBody>
			<@c.TableLoader id="taskPreCompContent" url="${ContextPath}/workflow/task/preCompleteListData.d?procInsId="+procInsId?if_exists>
	        	<#include "taskDetailListData.ftl">
	        </@c.TableLoader>
	        </@c.BoxBody>
	        <@c.BoxFooter class="text-center" >
	        <@c.Pagination class="pull-right" target="#taskPreCompContent" page=hdetail/>
	        </@c.BoxFooter>
    	</@c.Box>
    </@c.Tab>
</@c.Tabs>

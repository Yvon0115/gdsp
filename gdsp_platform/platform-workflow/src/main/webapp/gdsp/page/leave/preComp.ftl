<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_taskManage.js"/>-->
<@c.Script src="script/wf_taskManage" />
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="mainPanel2" active=true>
        <@c.Box>
        	<@c.BoxHeader title="表单"></@c.BoxHeader>
        	<#include "leave_c.ftl">
        </@c.Box>
        <@c.Box>
        	<@c.BoxHeader title="审批历史 "></@c.BoxHeader>
	        <@c.SimpleTable striped=false titles=["节点名称","审批人","审批结果","附加意见","审批时间"] keys=["actName","userId","result","options","createTime"] data=hdetail.content/>
			<@c.PageData page=hdetail />
        </@c.Box>
        <@c.Box>
    	<@c.BoxBody>
    		<@c.Form id="optionForm" class="" cols=1 action="${ContextPath}/workflow/process/complete.d" after={"switchtab":"#mainPanel","dataloader":"#taskContent"} method="get" help=false>
    			<@c.FormInput name="taskId" type="hidden" value="${taskId}"></@c.FormInput>
    			<@c.FormInput name="result" type="hidden" value="default"></@c.FormInput>
    			<@c.FormInput name="formid" type="hidden" value="${formid}"></@c.FormInput>
        		<@c.FormInput name="options" style="width:500px;" value="" label="附加意见：" validation={"required":true} messages={"required":"附加意见"}></@c.FormInput>
		    </@c.Form>
		</@c.BoxBody>
        <@c.BoxFooter class="text-center" >
            <@c.Button size="sm" click="ModiResult(1)" >同意</@c.Button>
            <@c.Button size="sm" click="ModiResult(2)" >不同意</@c.Button>
            <#--<@c.Button size="sm" click="ModiResult(3)" >驳回发起人</@c.Button>-->
            <@c.Button size="sm" click="ModiResult(4)" >驳回上一节点</@c.Button>
	    	<@c.Button type="canel" size="sm" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    	</@c.BoxFooter>
    	</@c.Box>
    </@c.Tab>
</@c.Tabs>

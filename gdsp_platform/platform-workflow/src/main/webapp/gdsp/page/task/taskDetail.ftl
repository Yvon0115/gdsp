<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/wf_taskManage" />
<@c.Hidden name="taskid" value="${taskId}" />
<@c.Hidden name="formid" value="${formid}" />
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="mainPanel3" active=true>
        <@c.Box id="taskDetail">
        	<@c.BoxHeader title="表单">
        	<@c.Button class="pull-right" type="canel" icon="glyphicon glyphicon-arrow-left" size="sm" action=[c.opentab("#mainPanel")]>返回</@c.Button>
        	<#if nodeId?? && "startuser" == nodeId>
				<@c.Button type="primary" class="pull-right" click="restartProcess()">重新发起</@c.Button>
			</#if>
			</@c.BoxHeader>
        	<@a.Include src="${formURL}" />
        	<@c.BoxHeader title="办理历史 "></@c.BoxHeader>
	        <#--<@c.SimpleTable striped=false titles=["节点名称","审批人","审批结果","附加意见"] keys=["actName","userId","result","options"] data=hdetail/>-->
	        <@c.BoxBody>
	        <@c.TableLoader id="taskDetailContent" url="${ContextPath}/workflow/task/taskDetailListData.d?procInsId="+procInsId?if_exists>
	        	<#include "taskDetailListData.ftl">
	        </@c.TableLoader>
	        </@c.BoxBody>
	        <@c.BoxFooter class="text-center" >
	        	<@c.Pagination class="pull-right" target="#taskDetailContent" page=hdetail/>
	    	</@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
</@c.Tabs>

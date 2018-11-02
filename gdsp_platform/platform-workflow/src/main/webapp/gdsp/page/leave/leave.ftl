<#import "/gdsp/tags/castle.ftl" as c>
<@c.BoxBody>
    <@c.Form id="leaveForm" class="validate" cols=1 action="${ContextPath}/workflow/leave/save.d" method="post" after={"switchtab":"#mainPanel"} help=false>
    	<#if editable ?? && editable == "true">
	    	<@c.FormIdVersion id="${leave?if_exists.id?if_exists}" version="${leave?if_exists.version?default(0)}"/>
	        <@c.FormInput name="leaveDay" label="请假天数" value="${leave?if_exists.leaveDay?if_exists}" validation={"required":true} messages={"required":"请假天数"}/>
	        <@c.FormInput type="hidden" name="deploymentid" value="${deploymentid?if_exists}" />
	        <@c.FormComboBox  name="leaveType" label="请假类型"  validation={"required":true} value="1">
	            <@c.Option value="1">事假</@c.Option>
	            <@c.Option value="2">病假</@c.Option>
	            <@c.Option value="3">带薪休假</@c.Option>
	        </@c.FormComboBox>
	        <@c.FormText name="reason" label="请假事由" validation={"required":true} colspan=2>${leave?if_exists.reason?if_exists}</@c.FormText>
    	<#else>
	        <@c.FormIdVersion id="${leave?if_exists.id?if_exists}" version="${leave?if_exists.version?default(0)}"/>
	        <@c.FormInput name="leaveDay" readonly=true label="请假天数" value="${leave?if_exists.leaveDay?if_exists}" validation={"required":true} messages={"required":"请假天数"}/>
	        <@c.FormInput type="hidden" readonly=true name="deploymentid" value="${deploymentid?if_exists}" />
	        <@c.FormComboBox disabled=true name="leaveType" label="请假类型"  validation={"required":true} value="1">
	            <@c.Option value="1">事假</@c.Option>
	            <@c.Option value="2">病假</@c.Option>
	            <@c.Option value="3">带薪休假</@c.Option>
	        </@c.FormComboBox>
        	<@c.FormText readonly=true name="reason" label="请假事由" validation={"required":true} colspan=2>${leave?if_exists.reason?if_exists}</@c.FormText>
        </#if>
    </@c.Form>
</@c.BoxBody>
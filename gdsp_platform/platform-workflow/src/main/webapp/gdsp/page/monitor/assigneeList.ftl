<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>${nodeName}</modal-title>
<@c.Box style="min-height:0px;height:100%;width:100%;">
	<@c.BoxHeader title="代办人">
	</@c.BoxHeader>
	<@c.BoxBody style="height:80%">
		<@c.TableLoader id="assigneeListContent" url="${ContextPath}/workflow/monitor/loadAssigneeData.d?deploymentId=${deploymentId}&actId=${actId}">
			<#include "assigneeList-data.ftl">
		</@c.TableLoader>
	</@c.BoxBody>
	<@c.BoxFooter class="text-center">
    	<@c.Pagination class="pull-right"  target="#assigneeListContent" page=users/>
	 </@c.BoxFooter>
</@c.Box>

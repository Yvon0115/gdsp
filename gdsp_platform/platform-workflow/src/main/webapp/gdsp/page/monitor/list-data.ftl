<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
	<#if userType?? && "admin"==userType>
		<@c.Link title="办理" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/workflow/monitor/preComplete.d?taskId=${row.id?if_exists}&procInsId=${row.procInsId?if_exists}&formId=${row.formid?if_exists}")]>办理</@c.Link>
	</#if>
	<@c.Link title="查看详情" icon="glyphicon glyphicon-search "  action=[c.opentab("#detailPanel","${ContextPath}/workflow/monitor/detail.d?deploymentId=${row.deploymentId}&actId=${row.nodeId}&taskId=${row.id?if_exists}&procInsId=${row.procInsId?if_exists}&formId=${row.formid?if_exists}")] >查看</@c.Link>
	<#--<@c.Link title="查看该节点代办人" icon="glyphicon glyphicon-search "  action=[c.opendlg("#assigneeDlg","${ContextPath}/workflow/monitor/loadAssignee.d?deploymentId=${row.deploymentId}&actId=${row.nodeId}")] >代办人</@c.Link>-->
</#noparse>
</#assign>
<#assign nodeName>!<#noparse>
	<@c.Link title="点击查看流程图" action=[c.opendlg("#pictureDlg","${ContextPath}/workflow/process/picture.d?procInsId=${row.procInsId?if_exists}","500px","1000px")]>${row.nodeName}</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["流程编码","流程名称","流程类型","发起人","发起时间","当前节点",""] keys=["deploymentCode","deploymentName","categoryName","userId","createTime",nodeName,op] data=monitorList.content />
<@c.PageData page=monitorList />
<#import "/gdsp/tags/castle.ftl" as c>
<#-- 流程历史数据表格页 -->
<#assign op>!
	<#noparse>
	<#-- action=[c.opentab("#detailPanel","${ContextPath}/workflow/history/detail.d?procInsId=${row.id}")] -->
	<#-- action=[c.opentab("#detailPanel","${ContextPath}/${row.url}?taskId=${row.id}&procInsId=${row.procInsId}&formid=${row.formid?if_exists}&type=2")] -->
		<@c.Link title="查看详细信息" icon="glyphicon glyphicon-search " action=[c.opentab("#detailPanel","${ContextPath}/workflow/history/taskDetail.d?taskId=${row.id?if_exists}&procInsId=${row.procInsId?if_exists}&formId=${row.formId?if_exists}")] >查看</@c.Link>
	</#noparse>
</#assign>
<#assign deploymentName>!
	<#noparse>
		<@c.Link title="点击查看流程图" action=[c.opendlg("#detailPanel","${ContextPath}/workflow/process/picture.d?procInsId=${row.procInsId}","500px","1000px")]>${row.deploymentName}</@c.Link>
	</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["","流程名称","流程类型","发起人","开始时间","结束时间","持续时长","操作"] keys=["",deploymentName,"categoryName","startUsers","startTime","endTime","duration",op] data=historyList.content checkbox=false/>
<@c.PageData page=historyList />
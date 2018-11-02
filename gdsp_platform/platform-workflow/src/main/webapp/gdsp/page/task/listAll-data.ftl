<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
	<#-- 可被撤回的流程实例布尔型标识 -->
	<#assign withdrawFlag=true>
	<#list irrevocableProcInstIds as irrId>
		<#if row['procInsId']?default("") == irrId>
			<#assign withdrawFlag=false>
			<#break>
		</#if>
	</#list>
	<#if withdrawFlag>
		<@c.Link title="撤回" icon="fa fa-undo" action=[c.rpc("${ContextPath}/workflow/task/retract.d?procInsId=${row.procInsId?if_exists}",{"dataloader":"#taskAllContent"},{"confirm":"确认撤回流程？"})]>撤回</@c.Link>
	</#if>
	<@c.Link title="查看" icon="glyphicon glyphicon-eye-open" action=[c.opentab("#detailPanel","${ContextPath}/workflow/task/taskDetail.d?taskId=${row.id?if_exists}&procInsId=${row.procInsId?if_exists}&formId=${row.formId?if_exists}")]>查看</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>
<#assign deploymentName>!<#noparse>
	<@c.Link title="点击查看流程图" icon="glyphicon glyphicon-link" action=[c.opendlg("#detailPanel","${ContextPath}/workflow/process/picture.d?procInsId=${row.procInsId}","500px","1000px")]>${row.deploymentName}</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["流程名称","流程类型","开始时间","结束时间",""] keys=[deploymentName,"categoryName","startTime","endTime",op] data=tasks.content/>
<@c.PageData page=tasks />
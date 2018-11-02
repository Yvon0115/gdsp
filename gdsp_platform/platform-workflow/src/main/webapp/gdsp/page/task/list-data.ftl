<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<!--<@c.Link title="办理" action=[c.rpc("${ContextPath}/workflow/process/complete.d?result=1&taskId=${row.id!''}",{"dataloader":"#taskContent"},{"confirm":"确认办理流程？"})]>办理</@c.Link>-->
<@c.TableLinks>
	<#if row["nodeId"]?default("")=="startuser">
		<@c.Link title="办理" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/workflow/task/taskDetail.d?taskId=${row.id!''}&procInsId=${row.procInsId?if_exists}&formId=${row.formid?if_exists}&nodeId=${row.nodeId?if_exists}")]>办理</@c.Link>
	<#else>
		<@c.Link title="办理" icon="glyphicon glyphicon-edit" action=[c.opentab("#compPanel","${ContextPath}/workflow/task/preComplete.d?taskId=${row.id!''}&procInsId=${row.procInsId?if_exists}&formId=${row.formid?if_exists}")]>办理</@c.Link>
		<@c.Link title="查看" icon="glyphicon glyphicon-eye-open" action=[c.opentab("#detailPanel","${ContextPath}/workflow/task/taskDetail.d?taskId=${row.id!''}&procInsId=${row.procInsId?if_exists}&formId=${row.formid?if_exists}")]>查看</@c.Link>
	</#if>
</@c.TableLinks>
</#noparse>
</#assign>
<#assign nodeName>!<#noparse>
	<#if row['nodeName']??>
	<@c.Link title="点击查看流程图" icon="glyphicon glyphicon-link" action=[c.opendlg("#detailPanel","${ContextPath}/workflow/process/picture.d?procInsId=${row.procInsId?if_exists}","500px","1000px")]>${row.nodeName!''}</@c.Link>
	<#else>
	<span>已被驳回</span>
	</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["流程编码","流程名称","流程类型","发起人","当前节点",""] keys=["deploymentCode","deploymentName","categoryName","userId",nodeName,op] data=tasks.content/>
<@c.PageData page=tasks />
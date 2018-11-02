<#--任务列表数据页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
 	<li><@c.Link title="查看任务" icon="glyphicon glyphicon-eye-open" action=[c.opentab("#detailPanel","${ContextPath}/schedule/job/showJob.d?name=${row.name?url}&group=${row.group}")]>查看</@c.Link></li>
	<li><@c.Link title="删除任务" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/schedule/job/delete.d?name=${row.name?url}&group=${row.group}&status=${row.jobState!}",{"dataloader":"#jobContent"},{"confirm":"确认删除任务‘${row.name?if_exists}’？"})]>删除</@c.Link></li>
	<li><@c.Link title="立即执行任务" icon="glyphicon glyphicon-eject" action=[c.rpc("${ContextPath}/schedule/job/startNowJob.d?name=${row.name?url}&group=${row.group}",{"dataloader":"#jobContent"},{"confirm":"确认立即执行任务‘${row.name?if_exists}’？"})]>执行</@c.Link></li>
	<li><@c.Link title="挂起任务" icon="glyphicon glyphicon-pause" action=[c.rpc("${ContextPath}/schedule/job/stopJob.d?name=${row.name?url}&group=${row.group}",{"dataloader":"#jobContent"},{"confirm":"确认挂起任务‘${row.name?if_exists}’？"})]>挂起</@c.Link></li>
	<li><@c.Link title="恢复任务" icon="glyphicon glyphicon-play" action=[c.rpc("${ContextPath}/schedule/job/restartJob.d?name=${row.name?url}&group=${row.group}",{"dataloader":"#jobContent"},{"confirm":"确认恢复任务‘${row.name?if_exists}’？"})]>恢复</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>

<@c.SimpleTable striped=false checkboxName="name" checkboxfield="name" titles=["名称","任务类型","任务状态","开始时间","结束时间","下次执行时间","操作"] keys=["name","jobName","triggerState","startTime","endTime","nextFireTime",op] ellipsis={"name":"100px","jobName":"100px"} data=jobTriggers checkbox=true/>

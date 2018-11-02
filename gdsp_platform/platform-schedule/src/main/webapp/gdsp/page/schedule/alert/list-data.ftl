<#--预警信息列表数据页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
 	<li><@c.Link title="查看预警" icon="glyphicon glyphicon-eye-open" action=[c.opentab("#detailPanel","${ContextPath}/schedule/alert/showAlert.d?name=${row.name?url}&group=${row.group}")]>查看</@c.Link></li>
  	<li><@c.Link title="删除预警" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/schedule/alert/delete.d?name=${row.name?url}&group=${row.group}&status=${row.triggerState}",{"dataloader":"#alertContent"},{"confirm":"确认删除预警‘${row.name?if_exists}’？"})]>删除</@c.Link></li>
	<li><@c.Link title="立即执行预警" icon="glyphicon glyphicon-eject" action=[c.rpc("${ContextPath}/schedule/alert/startNowAlert.d?name=${row.name?url}&group=${row.group}",{"dataloader":"#alertContent"},{"confirm":"确认立即执行预警‘${row.name?if_exists}’？"})]>执行</@c.Link></li>
	<li><@c.Link title="挂起预警" icon="glyphicon glyphicon-pause" action=[c.rpc("${ContextPath}/schedule/alert/stopAlert.d?name=${row.name?url}&group=${row.group}",{"dataloader":"#alertContent"},{"confirm":"确认挂起预警‘${row.name?if_exists}’？"})]>挂起</@c.Link></li>
	<li><@c.Link title="恢复预警" icon="glyphicon glyphicon-play" action=[c.rpc("${ContextPath}/schedule/alert/restartAlert.d?name=${row.name?url}&group=${row.group}",{"dataloader":"#alertContent"},{"confirm":"确认恢复预警‘${row.name?if_exists}’？"})]>恢复</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>

<@c.SimpleTable striped=false checkboxName="name" checkboxfield="name" titles=["名称","预警类型","预警状态","开始时间","结束时间","下次执行时间",""] keys=["name","jobName","triggerState","startTime","endTime","nextFireTime",op] ellipsis={"name":"175px","jobName":"175px"} data=jobTriggers checkbox=true/>

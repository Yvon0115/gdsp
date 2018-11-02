<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
	<@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/workflow/delegate/edit.d?id=${row.id}")]>修改</@c.Link>
	<@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/workflow/delegate/delete.d?id=${row.id}",{"dataloader":"#delegatesContent"},{"confirm":"确认删除？"})]>删除</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["代理人","创建时间","开始时间","结束时间",""] keys=["userName","createTime","startTime","endTime",op] data=delegates.content checkbox=true/>
<@c.PageData page=delegates />
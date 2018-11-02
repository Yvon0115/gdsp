<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
	<@c.TableLinks>
	 <@c.Button title="编辑" icon="glyphicon glyphicon-pencil" action=[c.opentab("#detailPanel","${ContextPath}/widgetmgr/pub/edit.d?id=${row.id}")]>修改</@c.Button> 
	 <@c.Button title="删除" icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/widgetmgr/pub/delete.d?id=${row.id}",{"dataloader":"#widgetName"},{"confirm":"确认删除记录‘${row.rolename?if_exists}’？"})]>删除</@c.Button> 
	</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["名称","描述","链接",""] keys=["widget_name","widget_desc","widget_link",op] data=widgets.content checkbox=true/>
<@c.PageData page=widgets />
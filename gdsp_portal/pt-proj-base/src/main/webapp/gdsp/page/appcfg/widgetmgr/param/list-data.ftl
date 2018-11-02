<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
	<@c.Link title="编辑" icon="glyphicon glyphicon-edit"   action=[c.opentab("#detailParamPanel","${ContextPath}/widgetmgr/param/edit.d?id=${row.id}")]>修改</@c.Link>
   <@c.Link title="删除"   icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/widgetmgr/param/delete.d?id=${row.id}",{"dataloader":"#paramName"},{"confirm":"确认删除记录‘${row.name?if_exists}’？"})]>删除</@c.Link>
</@c.TableLinks>
 </#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["参数名称","参数说明","参数类型", ""] keys=["name","comments","type",op]  ellipsis={"name":"200px","comments":"200px"} data=params.content checkbox=true/>
 <@c.PageData page=params />
 
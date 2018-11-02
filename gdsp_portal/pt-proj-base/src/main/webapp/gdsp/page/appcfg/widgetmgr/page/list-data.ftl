<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
<li><@c.Link title="编辑" icon="glyphicon glyphicon-pencil" action=[c.opentab("#detailPanel","${ContextPath}/widgetmgr/page/edit.d?id=${row.id}")]>修改</@c.Link></li>
<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/widgetmgr/page/delete.d?id=${row.id}",{"dataloader":"#widgetName"},{"confirm":"确认删除记录‘${row.widget_name?if_exists}’？"})]>删除</@c.Link></li>
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["名称","别名", ""] keys=["res_name","res_alias",op] data=widgets.content checkbox=true/>
 
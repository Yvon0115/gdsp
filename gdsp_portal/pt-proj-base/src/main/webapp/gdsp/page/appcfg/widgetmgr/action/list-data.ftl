<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
    <@c.Link title="编辑" icon="glyphicon glyphicon-edit"  action=[c.opentab("#detailActionPanel","${ContextPath}/widgetmgr/action/edit.d?id=${row.id}&type=${type?if_exists}")]>编辑</@c.Link>
   <@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/widgetmgr/action/delete.d?id=${row.id}",{"dataloader":"#actionName"},{"confirm":"确认删除记录‘${row.name?if_exists}’？"})]>删除</@c.Link> 
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["动作编码","动作名称","动作类型","动作说明", ""] keys=["code","name","widgettype","memo",op] ellipsis={"code":"150px","name":"200px","memo":"200px"} data=actions.content checkbox=true/>
<@c.PageData page=actions />

 
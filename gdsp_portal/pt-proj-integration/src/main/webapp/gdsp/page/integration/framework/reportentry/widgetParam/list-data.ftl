<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
   <@c.Link title="删除"   icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/framework/widgetParam/delete.d?id=${row.id}",{"dataloader":"#paramName"},{"confirm":"确认删除记录‘${row.name?if_exists}’？"})]>删除</@c.Link>
</@c.TableLinks>
 </#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["参数名称","显示名称","参数类型", ""] keys=["name","displayName","type",op]  ellipsis={"name":"200px","comments":"200px"} data=params.content checkbox=true/>
 <@c.PageData page=params />
 
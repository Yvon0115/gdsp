<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
<@c.Link title="修改数据信息" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailParamsPanel","${ContextPath}/widget/cdnTemplate/edit.d?id=${row.id}")]>修改</@c.Link>
<@c.Link title="删除数据" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/widget/cdnTemplate/delete.d?id=${row.id}",{"dataloader":"#paramTypeContents"},{"confirm":"确认删除数据源 ‘${row.name?if_exists}’？"})]>删除</@c.Link>
<@c.Link title="参数维护" icon="glyphicon glyphicon-pencil" action=[c.opentab("#detailParamsPanel","${ContextPath}/widgetmgr/param/list.d?widget_id=${row.id}")]>参数维护</@c.Link>
 </@c.TableOperate>
 </#noparse>
</#assign>
 <@c.SimpleTable striped=false titles=["模板类型名称","模板类型编码","类型","备注",""] keys=["name","code","type","comments",op] data=paramsPage.content checkbox=true/>
<@c.PageData page=paramsPage />
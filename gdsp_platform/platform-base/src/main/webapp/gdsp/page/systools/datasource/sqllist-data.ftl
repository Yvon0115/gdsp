<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
<@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/systools/ds/sqlEdit.d?id=${row.id}")]>修改</@c.Link>
<@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/systools/ds/delete.d?id=${row.id}",{"dataloader":"#datasourcesContent"},{"confirm":"确认删除数据源 ‘${row.name?if_exists}’？"})]>删除</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["数据源名称","编码","描述",""] keys=["name","code","comments",op] data=dateSourceVO.content checkbox=true/>
<@c.PageData page=dateSourceVO />
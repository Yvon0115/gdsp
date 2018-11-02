<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
<@c.Link title="修改" icon="glyphicon glyphicon-edit" click="editDs('${row.id}')" <#--action=[c.opentab("#detailPanel","${ContextPath}/systools/ds/toEdit.d?id=${row.id}")]-->>修改</@c.Link>
<@c.Link title="删除" icon="glyphicon glyphicon-remove" click="deleteDs('${row.id}')" <#--action=[c.rpc("${ContextPath}/systools/ds/delete.d?id=${row.id}",{"dataloader":"#usersContent"},{"confirm":"确认删除数据源 ‘${row.name?if_exists}’？"})]-->>删除</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=true  titles=["数据源名称","数据源类型编码",""] keys=["name","type",op] ellipsis={"name":"80px","type":"100px"} data=dateSourceVO.content checkbox=true/>
<@c.PageData page=dateSourceVO />
<@c.Hidden  name="doc_code" value="${doc_code!''}" />
<@c.Hidden  name="doc_id" value="${doc_id!0}" />
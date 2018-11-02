<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/indexanddim/idxdimrel/deleteDim.d?id=${row.id}",{"dataloader":"#dimContent"},{"confirm":"确认删除维度‘${row.dimVO.dimname?if_exists}’？"})]>删除</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["维度名称","维度字段名称","操作"] keys=["dimVO.dimname","dimVO.dimfieldname",op]  data=dimList?default({"content":[]}).content checkbox=true/>
<@c.PageData page=dimList?default("")/>
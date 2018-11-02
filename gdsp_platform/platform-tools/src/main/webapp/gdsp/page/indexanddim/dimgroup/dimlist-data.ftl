<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="删除关联维度" icon="glyphicon glyphicon-remove"action=[c.rpc("${ContextPath}/indexanddim/dimgroup/deleteDimInDimGroup.d?id=${row.id}",{"dataloader":"#dimListContent"},{"confirm":"确认删除关联维度？"})] >删除</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["维度名称","维度字段名称","维度数据表","维度说明","描述","维度类型",""] keys=["dimVO.dimname","dimVO.dimfieldname","dimVO.dimtablename","dimVO.dimmemo","dimVO.memo","dimVO.dimtype",op]  data=groupDims?default({"content":[]}).content  checkbox=true/>
<@c.PageData page=groupDims?default("")/>

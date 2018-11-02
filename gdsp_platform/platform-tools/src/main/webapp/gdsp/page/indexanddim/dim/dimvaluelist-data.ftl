<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#addDimValuePanel","${ContextPath}/indexanddim/dim/editDimValue.d?id=${row.id}&dimId=${row.pk_dim}")]>修改</@c.Link>
<@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/indexanddim/dim/deleteDimValue.d?id=${row.id}",{"dataloader":"#dimValueListContent"},{"confirm":"确认删除维值 ‘${row.dimValueVO.dimvalue?if_exists}’？"})]>删除</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["维值","维值描述",""] keys=["dimValueVO.dimvalue","dimValueVO.dimvaluememo",op] data=dims.content checkbox=true/>
<@c.PageData page=dims />
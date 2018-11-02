<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="编辑" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPnl","${ContextPath}/workflow/category/edit.d?id=${row.id}")]>编辑</@c.Link>
<@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/workflow/category/delete.d?id=${row.id}",{"dataloader":"#categoryContent"},{"confirm":"确认删除此类型‘${row.categoryName?if_exists}’？"})]>删除</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["类型名称","类型描述","创建时间","操作"] keys=["categoryName","memo","createTime",op] data=categorys.content checkbox=true/>
<@c.PageData page=categorys />

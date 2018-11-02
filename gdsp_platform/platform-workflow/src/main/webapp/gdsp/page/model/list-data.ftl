<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="编辑" action=[c.opentab("#detailPanel","${ContextPath}/workflow/model/getXml.d?id=${row.id}")]>编辑</@c.Link>
<@c.Link title="部署" action=[c.rpc("${ContextPath}/workflow/model/deploy.d?modelId=${row.id}",{"dataloader":"#modelContent"},{"confirm":"确认部署流程‘${row.name?if_exists}’？"})]>部署</@c.Link>
<@c.Link title="导出" action=[c.rpc("${ContextPath}/workflow/model/export.d?id=${row.id}",{"dataloader":"#modelContent"})]>导出</@c.Link>
<@c.Link title="删除" action=[c.rpc("${ContextPath}/workflow/model/delete.d?id=${row.id}",{"dataloader":"#modelContent"},{"confirm":"确认删除流程‘${row.name?if_exists}’？"})]>删除</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["模型ID","模型名称","模型版本","操作"] keys=["id","name","version",op] data=models.content checkbox=true/>
<@c.PageData page=models />
<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="启动" action=[c.rpc("${ContextPath}/workflow/deployment/start.d?id=${row.id}",{"dataloader":"#deploymentContent"},{"confirm":"确认启动流程‘${row.id?if_exists}’？"})]>启动</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["部署名称","流程定义ID","流程状态","表单id","表单URL","操作"] keys=["deploymentid","deployid","state","fromid","formurl",op] data=deployments.content checkbox=true/>
<@c.PageData page=deployments />
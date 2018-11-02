<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/grant/mydemo/edit.d?id=${row.id}")]>修改</@c.Link></li>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/mydemo/delete.d?id=${row.id}",{"dataloader":"#demoPageContent"},{"confirm":"确认删除用户组‘${row.name?if_exists}’？"})]>删除</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>
<@c.SimpleTable striped=true highlight=true titles=["姓名","年龄","电话","邮箱",""] keys=["name","age","tel","email",op] ellipsis={"name":"140px","age":"140px","tel":"140px","email":"140px"} data=demoPage.content checkbox=true/>
<@c.PageData page=demoPage />
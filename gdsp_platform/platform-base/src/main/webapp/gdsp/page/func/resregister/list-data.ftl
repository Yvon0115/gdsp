<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改" icon="glyphicon glyphicon-pencil" action=[c.opentab("#detailResPanel","${ContextPath}/func/resource/edit.d?id=${row.id}")]>修改</@c.Link></li>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/func/resource/delete.d?id=${row.id}",{"dataloader":"#resRegisterVos"},{"confirm":"确认删除‘${row.URL?if_exists}’？"})]>删除</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["url","描述",""] keys=["url","memo",op] ellipsis={"memo":"200px"} data=resource.content checkbox=true/>
<@c.PageData page=resource />
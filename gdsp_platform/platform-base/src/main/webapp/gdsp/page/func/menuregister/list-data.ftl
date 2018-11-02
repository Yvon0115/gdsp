<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改" icon="glyphicon glyphicon-pencil" action=[c.opentab("#detailPanel","${ContextPath}/func/menu/edit.d?id=${row.id}")]>修改</@c.Link></li>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/func/menu/delete.d?id=${row.id}",{"dataloader":"#menuRegisterVos"},{"confirm":"确认删除‘${row.name?if_exists}’？"})]>删除</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["编码","名称","上级节点","内部码",""] keys=["funcode","funname","parentid","innercode",op] data=muneRegisterPages.content checkbox=true/>


<@c.PageData page=muneRegisterPages />
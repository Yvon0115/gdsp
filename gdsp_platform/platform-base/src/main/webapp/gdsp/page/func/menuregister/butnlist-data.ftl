<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanelButn","${ContextPath}/func/butn/edit.d?id=${row.id}")]>修改</@c.Link></li>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/func/butn/delete.d?id=${row.id}",{"dataloader":"#btnContent"},{"confirm":"确认删除按钮‘${row.funname?if_exists}’？"})]>删除</@c.Link></li>
</@c.TableOperate>	
</#noparse>
</#assign>

<@c.SimpleTable striped=false titles=["按钮名","按钮编码","访问URL","描述",""] keys=["funname","funcode","url","memo",op] ellipsis={"funname":"150px","memo":"200px"} data=butnRegisterPages.content checkbox=true/>
<@c.PageData page=butnRegisterPages />
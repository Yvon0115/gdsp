<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/grant/usergroup/edit.d?id=${row.id}")]>修改</@c.Link></li>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/usergroup/delete.d?id=${row.id}",{"dataloader":"#usergroupsContent"},{"confirm":"确认删除用户组‘${row.groupname?if_exists}’？"})]>删除</@c.Link></li>
	<li><@c.Link title="关联" icon="glyphicon glyphicon-share" action=[c.opentab("#detailPanel","${ContextPath}/grant/usergroup/userList.d?groupId=${row.id}")]>关联</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["用户组名","描述",""] keys=["groupname","memo",op] ellipsis={"groupname":"140px","memo":"380px"} data=usergroups.content checkbox=true/>
<@c.PageData page=usergroups />
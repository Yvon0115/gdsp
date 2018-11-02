<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/grant/role/edit.d?id=${row.id}")]>修改</@c.Link></li>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" click="deleteRole('${row.id}')" <#--action=[c.rpc("${ContextPath}/grant/role/delete.d?id=${row.id}",{"dataloader":"#rolesContent"},{"confirm":"确认删除角色‘${row.rolename?if_exists}’？"})]-->>删除</@c.Link></li>
	<li><@c.Link title="授权" icon="glyphicon glyphicon-share" action=[c.opentab("#detailPanel","${ContextPath}/grant/role/editRolePower.d?roleID=${row.id}&orgID=${row.pk_org}")]>授权</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["<th width=200px>角色名</th>","机构名称","描述",""] keys=["rolename","orgname","memo",op] ellipsis={"rolename":"200px","memo":"280px"} data=rolePage.content?default([]) checkbox=true/>
<@c.Hidden name="selOrgID" value=selOrgID/>
<@c.PageData page=rolePage />



<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="删除关联用户" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/role/deleteUserRoles.d?id=${row.id}&roleId=${roleId}",{"dataloader":"#roleListContent"},{"confirm":"确认删除关联用户‘${row.username?if_exists}’？"})]>删除</@c.Link>
</#noparse>
</#assign>
<#-- 如果系统权限时效开启，且当前编辑角色为时效角色 -->
<#if agingStatus??&&agingStatus=="Y">
	<#if isAgingRole??&&isAgingRole=="Y">
		<@c.SimpleTable striped=true titles=["账号","姓名","手机","邮箱","机构名","权限截止日期",""] keys=["account","username","mobile","email","orgname","agingEndDate",op] data=roleUsers.content?default([]) checkbox=true/>
	<#else>
		<@c.SimpleTable striped=true titles=["账号","姓名","手机","邮箱","机构名",""] keys=["account","username","mobile","email","orgname",op] data=roleUsers.content?default([]) checkbox=true/>
	</#if>
<#else>
	<@c.SimpleTable striped=false titles=["账号","姓名","手机","邮箱","机构名",""] keys=["account","username","mobile","email","orgname",op] data=roleUsers.content?default([]) checkbox=true/>
</#if>
<@c.PageData page=roleUsers />
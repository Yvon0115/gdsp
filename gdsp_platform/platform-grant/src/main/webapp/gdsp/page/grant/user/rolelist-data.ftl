<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="删除关联角色" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/user/deleteRolesOnUser.d?id=${row.id}&userId=${userId}",{"dataloader":"#roleListContent"},{"confirm":"确认删除关联角色‘${row.rolename?if_exists}’？"})]>删除</@c.Link>
</#noparse>
</#assign>
<#if agingStatus??&&agingStatus=="Y">
	<@c.SimpleTable striped=true titles=["角色名称","所属机构","描述","时效截止日期",""] keys=["rolename","orgname","memo","agingEndDate",op] ellipsis={"memo":"150px"} data=roleVO?if_exists.content?default([]) checkbox=true/>
<#else>
	<@c.SimpleTable striped=true titles=["角色名称","所属机构","描述",""] keys=["rolename","orgname","memo",op] ellipsis={"memo":"150px"} data=roleVO?if_exists.content?default([]) checkbox=true/>
</#if>
<@c.Hidden id="agingroleId" name="agingroleId" value="${agingroleId?if_exists}" />
<@c.PageData page=roleVO?if_exists />

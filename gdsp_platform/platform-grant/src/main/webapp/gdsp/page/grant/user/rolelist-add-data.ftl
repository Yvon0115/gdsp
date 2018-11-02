<#import "/gdsp/tags/castle.ftl" as c>

<#if agingStatus??&&agingStatus=="Y">
	<#assign limit>!<#noparse>
		<#if row.agingLimit??&&row.agingLimit == "Y">是<#else>否</#if>
	</#noparse></#assign>
	<@c.SimpleTable striped=true titles=["角色名称","所属机构","描述","是否为时效角色"] keys=["rolename","orgname","memo",limit] ellipsis={"memo":"150px"} data=roleVO?if_exists.content?default([]) checkbox=true/>
<#else>
	<@c.SimpleTable striped=true titles=["角色名称","所属机构","描述"] keys=["rolename","orgname","memo"] ellipsis={"memo":"150px"} data=roleVO?if_exists.content?default([]) checkbox=true/>
</#if>
<@c.PageData page=roleVO?if_exists />

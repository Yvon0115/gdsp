<#import "/gdsp/tags/castle.ftl" as c>

<#assign isDisabled>!<#noparse>
<#if row.isdisabled??&& row.isdisabled=="Y">停用<#else>启用</#if>
</#noparse>
</#assign>
<#assign isLocked>!<#noparse>
<#if row.islocked??&&row.islocked=="Y">是<#else>否</#if>
</#noparse>
</#assign>
<#assign op>!<#noparse>
	<@c.Link title="机构分配" icon="glyphicon glyphicon-wrench" click="multiDistribute('${row.id}')" >机构分配</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable id="userDistribute" checkbox=true titles=["用户名","创建时间","最后修改时间","启用状态","锁定状态","操作"] keys=["name","usercreateTime","userlastModifyTime",isDisabled,isLocked,op] ellipsis={} data=(tmpUsers2Allocated.content)?default([]) />
	
<@c.PageData page=tmpUsers2Allocated?default('') />	
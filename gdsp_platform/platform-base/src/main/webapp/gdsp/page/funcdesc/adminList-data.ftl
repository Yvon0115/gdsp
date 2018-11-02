<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/funcdesc/functionDec.js"/>-->
<@c.Script src="script/funcdesc/functionDec" />
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/appcfg/widgetmgr/widgetmgr.js"/>-->
<#-- <@c.Script src="script/appcfg/widgetmgr/widgetmgr" /> -->
<#if functionPages??>
<#assign op>!<#noparse>
<@c.Link icon="glyphicon glyphicon-eye-open"  click="viewDetail('${row.id}')" >查看</@c.Link>
</#noparse>
</#assign>
<#assign ispower>!<#noparse>
<#if row.ispower??>
		<small class="label   bg-green">有权限</small>
    <#else>
</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["功能名称","功能描述","权限",""] keys=["funname","memo",ispower,op] ellipsis={"funname":"150px","memo":"200px"}  data=functionPages.content />
<@c.PageData page=functionPages />
<#else>
</#if>
<#--预警信息参数列表页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign required>!<#noparse>
<#if row.required??&& row.required=="Y">是<#else>否</#if>
</#noparse>
</#assign>
<@c.SimpleTable id="paraTable" checkboxName="paraname" checkboxfield="paraname" striped=false titles=["名称","是否必输","描述","值"] keys=["paraname",required,"description","value"] data=parameters checkbox=false/>
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/sysnotice/simple-list.js"/>-->
<@c.Script src="script/sysnotice/simple-list" />
<#assign title>!<#noparse>
<#if row.title??>
<@c.Link title=row.title click="showDetai(e,'${row.id}')">
${row.title}
</@c.Link>
</#if>
</#noparse>
</#assign>

<#assign publish_date>!<#noparse>
<#if row.publish_date??>
	${row.publish_date}
</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=false   keys=[title,publish_date] data=noticeVoPages.content/>
<@c.PageData page=noticeVoPages />
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="script/sysnotice/simple-list"/>-->
<@c.Script src="script/sysnotice/simple-list" />
<!--标题 -->
<#assign title>!<#noparse>
<#if row.title??>
<@c.Link title=row.title click="readSysNotice('${row.id}');">${row.title}</@c.Link>
</#if>
</#noparse>
</#assign>
<!--简要内容 -->
<#assign content>!<#noparse>
<#if row?if_exists.content?length gte 20>
${row?if_exists.content?if_exists?substring(0,20)}
<#else>
${row?if_exists.content?if_exists?substring(0,row?if_exists.content?length)}
</#if>
</#noparse>
</#assign>
<!--发布日期-->
<#assign publish_date>!<#noparse>
<#if row.publish_date??>
	${row.publish_date}
</#if>
</#noparse>
</#assign>
<#assign readflag>!<#noparse>
<#if row.readflag??>
<#if row.readflag==1>
<small class="label   bg-green">未读</small>
    <#else>

</#if>
</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["公告标题","公告内容","","发布时间"]   keys=[title,"content",readflag,publish_date] data=notices.content/>
<@c.PageData page=notices />
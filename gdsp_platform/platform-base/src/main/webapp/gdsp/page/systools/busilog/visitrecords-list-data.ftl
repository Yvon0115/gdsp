<#import "/gdsp/tags/castle.ftl" as c>
<#-- 最近访问top10表格 -->
<#assign link>!<#noparse>
	<#if row.funname??>
		<#-- <#assign subidx=row.name?index_of('_')+1 /> -->
		<@c.Link title=row.funname action=["href='${ContextPath}/${row.url}'"]>${row.funname?if_exists}</@c.Link>
	</#if>
</#noparse></#assign>
<#assign crtime >!<#noparse>
	<#if row.createTime??>
		${row.createTime?substring(0,10)} 
	</#if>
</#noparse></#assign>
<@c.SimpleTable striped=true  keys=[link,crtime] data=visitTop10 />

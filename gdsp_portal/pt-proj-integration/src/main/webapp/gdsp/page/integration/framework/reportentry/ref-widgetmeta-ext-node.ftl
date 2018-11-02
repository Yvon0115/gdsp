<#import "/gdsp/tags/castle.ftl" as c>
<#if nodes?exists>
 <#list nodes as node>
		<li tlevel="runqianTreeLevel" <#if node.leaf??&&node.leaf == 'Y'>hasCheck="true"<#else>hasChild="true"</#if>  id="${node.id}" path="${node.report_path}"><a value="${node.id}" >${node.report_name}</a></li>
</#list>
</#if>
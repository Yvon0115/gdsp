<#import "/gdsp/tags/castle.ftl" as c>
<#if nodes?exists>
 <#list nodes as node>
		<li tlevel="dirTree"  hasChild="true" id="${node.id}"><a value="${node.id}">${node.name}</a></li>
</#list>
</#if>
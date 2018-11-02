<#import "/gdsp/tags/castle.ftl" as c>
<#if nodes?exists>
 <#list nodes as node>
		<li tlevel="commonDirTree"  hasChild="true" id="${node.id}"><a value="${node.id}">${node.dir_name}</a></li>
</#list>
</#if>
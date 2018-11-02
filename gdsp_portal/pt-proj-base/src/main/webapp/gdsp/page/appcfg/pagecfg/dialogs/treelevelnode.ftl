<#import "/gdsp/tags/castle.ftl" as c>
<#if nodes?exists>
 <#list nodes as node>
		<li tlevel="${treeid}" btreeid="${node.btreeid}"  hasChild="<#if node.isdir == 'Y'>true<#else>flase</#if>" id="${node.id}"><a>${node.text}</a></li>
</#list>
</#if>
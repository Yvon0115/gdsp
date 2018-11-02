<#import "/gdsp/tags/castle.ftl" as c>
<#if nodes?exists>
 <#list nodes as node>
		<li tlevel="cognosTreeLevel" report_path="${node.report_path?if_exists}" parent_path="${node.parent_path?if_exists}" nodeId="${node.id}"  <#if node.leaf??&&node.leaf == 'Y'><#else>hasChild="true"</#if> id="${node.id}"><a value="${node.id}"  wmtype='cognos'><#if node.parent_path=="">[${node.report_name?if_exists}].${node.data_source_name?if_exists}<#else>${node.report_name?if_exists}</#if></a></li>
</#list>
</#if>
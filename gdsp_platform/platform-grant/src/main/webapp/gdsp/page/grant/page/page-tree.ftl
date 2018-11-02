<#import "/gdsp/tags/castle.ftl" as c>
<#--以前的树结构被抛弃-->
<#--<@c.SimpleTree id="orgTree" expand=0 >
		<@c.TreeMapBuilder map=pageMap nameField="funname" linkexp=r"onclick='changePageNode(&quot;${node.id}&quot;,&quot;${node?if_exists.menuid?if_exists}&quot;)'" >
		</@c.TreeMapBuilder>
	</@c.SimpleTree>-->	
	<@c.EasyTreeBuilder id="pageTree" url="/grant/page/loadPage.d" levels="2" checkOption="1" events="{onNodeSelected:changePageNode}" selectFirstNode=true>
	</@c.EasyTreeBuilder>
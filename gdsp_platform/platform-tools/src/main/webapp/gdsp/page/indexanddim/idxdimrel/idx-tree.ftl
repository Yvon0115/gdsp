<!-- 指标树 -->
<#import "/gdsp/tags/castle.ftl" as c>

	<@c.SimpleTree id="indexTree" expand=-1 >
		<@c.TreeMapBuilder map=idxTreeMap nameField="disname" linkexp=r"onclick='changeIdxNode(&quot;${node.id}&quot;,&quot;${node.leaf}&quot;)'" >
		</@c.TreeMapBuilder>
	</@c.SimpleTree>	

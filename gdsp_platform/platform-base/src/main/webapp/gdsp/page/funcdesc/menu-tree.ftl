<#import "/gdsp/tags/castle.ftl" as c>
<div>
	<#-- <@c.SimpleTree id="menuTree" >
		<@c.TreeMapBuilder map=voTreeMap nameField="funname" linkexp=r"onclick='selectfunctionNode(&quot;${node.id}&quot;,&quot;${node.funtype}&quot;)'">
		</@c.TreeMapBuilder>
	</@c.SimpleTree>-->
	<@c.EasyTreeBuilder id="menuTree" searchAble=true showBorder=false url="/portal/functionDec/getTree.d" levels="2" events="{onNodeSelected:selectNode}">
	</@c.EasyTreeBuilder>
</div>
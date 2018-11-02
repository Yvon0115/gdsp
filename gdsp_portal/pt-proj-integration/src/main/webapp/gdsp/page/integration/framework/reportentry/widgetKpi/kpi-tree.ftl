<#import "/gdsp/tags/castle.ftl" as c>
<div >
	<#-- <@c.SimpleTree id="menuTree"  events="{clickNode: selectKpiLibNode}" expand="0">
		<@c.TreeMapBuilder map=kpiMap nameField="groupName">
		</@c.TreeMapBuilder>
	</@c.SimpleTree>-->
	<@c.EasyTreeBuilder id="Tree"  events="{onNodeSelected:selectKpiLibNode}" url="/framework/widgetKpi/loadtreenode.d" checkOption="1">
	</@c.EasyTreeBuilder>	

</div>
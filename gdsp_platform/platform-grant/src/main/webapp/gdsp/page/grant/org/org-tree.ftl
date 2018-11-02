<#--机构树页面-->
<#import "/gdsp/tags/castle.ftl" as c>
	<#--<@c.SimpleTree id="orgTree"  events="{clickNode: selectNode}" defaultSelect="true">
		<@c.TreeListBuilder nodes=nodes nameField="orgname">
		</@c.TreeListBuilder>
	</@c.SimpleTree>-->	
<@c.EasyTreeBuilder id="orgTree" url="/grant/org/listOrg.d" levels="2" events="{onNodeSelected:selectNode}" selectFirstNode=true>
</@c.EasyTreeBuilder>
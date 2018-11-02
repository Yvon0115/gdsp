<#--机构树页面-->
<#import "/gdsp/tags/castle.ftl" as c>
	<@c.SimpleTree id="orgTree" events="{clickNode: selectNode}" defaultSelect="true">
		<@c.TreeListBuilder nodes=nodes nameField="orgname" linkexp=r>
		</@c.TreeListBuilder>
	</@c.SimpleTree>	

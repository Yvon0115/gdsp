<#import "/gdsp/tags/castle.ftl" as c>
<div >
	
	<#--<@c.SimpleTree id="menuTree"  events="{clickNode: menuPageJS.selectNode}" expand="0">
		<@c.TreeMapBuilder map=voTreeMap nameField="funname">
		</@c.TreeMapBuilder>
	</@c.SimpleTree>
		-->
	
	 <@c.EasyTreeBuilder id="menuTree" url="/func/menu/listmenu.d"  showBorder=false events="{onNodeSelected: selectNode}">
	
	</@c.EasyTreeBuilder>

</div>
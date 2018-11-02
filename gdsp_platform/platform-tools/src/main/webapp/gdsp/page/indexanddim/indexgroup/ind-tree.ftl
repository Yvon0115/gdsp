<#import "/gdsp/tags/castle.ftl" as c>
<#--<@c.SimpleTree id="indTree"  events="{clickNode: selectNode}" defaultSelect="true">
	<@c.TreeListBuilder nodes=nodes nameField="groupName" valueField="id" linkexp=r"${dataloader('#indsContent',{'innercode':node.innercode,'groupId':node.id},true,'init')}">
	</@c.TreeListBuilder>
</@c.SimpleTree>-->
<@c.EasyTreeBuilder id="Tree" events="{onNodeSelected:selectNode}" url="/indexanddim/indexgroup/dimGroupTree.d" checkOption="1">
</@c.EasyTreeBuilder>		


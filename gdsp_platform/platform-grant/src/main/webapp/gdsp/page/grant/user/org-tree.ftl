<#import "/gdsp/tags/castle.ftl" as c>
<#-- <@c.SimpleTree id="orgTree"  events="{clickNode: selectNode}" defaultSelect="true">
	<@c.TreeListBuilder nodes=nodes nameField="orgname" linkexp=r"${dataloader('#usersContent',{'pk_org':node.id},true,'init')}">
	</@c.TreeListBuilder>
</@c.SimpleTree>-->
<@c.EasyTreeBuilder id="userOrgTree" url="/grant/user/orgTree.d" levels="2" events="{onNodeSelected:selectNode}" selectFirstNode=true >
</@c.EasyTreeBuilder>	

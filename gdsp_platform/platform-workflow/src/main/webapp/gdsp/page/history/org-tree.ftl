<#import "/gdsp/tags/castle.ftl" as c>
<#-- 机构树 -->
	<@c.SimpleTree id="orgTree"  events="{clickNode: selectNode}" defaultSelect="true">
		<@c.TreeListBuilder nodes=nodes nameField="orgname" linkexp=r"${dataloader('#usersContent',{'pk_org':node.id})}" />
	</@c.SimpleTree>	

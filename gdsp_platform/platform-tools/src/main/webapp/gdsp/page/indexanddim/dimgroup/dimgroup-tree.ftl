<#--维度分组树页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTree id="dimgroupTree"  expand=1 defaultSelect="false">
		<@c.TreeListBuilder nodes=nodes nameField="groupname" valueField="id" linkexp=r"onclick='changeGroupNode(&quot;${node.innercode}&quot;,&quot;${node.id}&quot;,&quot;${node.groupname}&quot;)'">
		</@c.TreeListBuilder>
</@c.SimpleTree>
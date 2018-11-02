<#import "/gdsp/tags/castle.ftl" as c>
<#--<@c.SimpleTree id="commonDirTree" events="{clickNode: resManagerPageJS.selectNode}"  expand="-1">
	<@c.RecuiveLevel id="commonDirTree" url="${ContextPath}/appcfg/resourceManage/loaddirtreenode.d" recuiveField="id" recuiveParameter="parent_id">
	</@c.RecuiveLevel>
</@c.SimpleTree>-->
<@c.EasyTreeBuilder id="Tree" events="{onNodeSelected:resManagerPageJS.selectNode}" url="/appcfg/resourceManage/dirTree.d" checkOption="1">
</@c.EasyTreeBuilder>	
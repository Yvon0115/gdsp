<#import "/gdsp/tags/castle.ftl" as c>
	<@c.SimpleTree id="favoritesTree">
			<@c.TreeMapBuilder map=nodesMap nameField="name" linkexp=r"onclick='selectFavoriteNode(&quot;${node.fileType}&quot;,&quot;${node.url?if_exists?default(1)}&quot;,&quot;${node.name}&quot;)'">
			</@c.TreeMapBuilder>
	</@c.SimpleTree>
<#--<@c.EasyTreeBuilder id="favoritesTree"  url="/favorites/showTree.d" checkOption="1">
</@c.EasyTreeBuilder>-->	
		

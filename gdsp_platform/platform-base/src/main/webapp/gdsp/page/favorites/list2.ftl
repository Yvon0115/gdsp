<#import "/gdsp/tags/castle.ftl" as c>

    	
	    	<@c.SimpleTree id="manageTree">
				<@c.TreeMapBuilder map=nodesMap nameField="name" linkexp=r"onclick='manageNode(&quot;${node.id}&quot;)'">
				</@c.TreeMapBuilder>
			</@c.SimpleTree>
		

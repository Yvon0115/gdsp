<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>选择上级目录</modal-title>	
<@c.Box >	
     <@c.BoxBody class="no-padding scrollbar"  style="width:100%;max-height:500px;min-height:200px;">
     		<@c.Hidden id="refParentId" name="refParentId" value=""/>
			<@c.Hidden id="refParentName" name="refParentName" value=""/>
			<@c.SimpleTree id="dirTree" events="{clickNode: selectParentNode}">
				<@c.TreeMapBuilder map=dirTree nameField="name" linkexp=r"href='javascript:void(0);' ondblclick='$.closeReference({value:[&quot;${node.id}&quot;],text:[&quot;${node.name}&quot;]});'">
				</@c.TreeMapBuilder>
			</@c.SimpleTree>
	 </@c.BoxBody>
	</@c.Box>
	<@c.BoxFooter class="text-center">
    <@c.Button type="primary"  icon="fa fa-save"  click="saveParentNode()" action=[c.attrs({"data-dismiss":"modal"})]>确定</@c.Button>
	<@c.Button type="canel"  icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
	
    </@c.BoxFooter>
 
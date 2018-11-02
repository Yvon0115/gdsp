<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>批量添加参数</modal-title>	
<@c.Box >	
     <@c.BoxBody class="no-padding scrollbar"  style="width:100%;max-height:600px;min-height:500px;">
			<@c.SimpleTree id="dirTree" checkbox=true  >
				<@c.TreeMapBuilder map=dirTree nameField="name" linkexp=r"href='javascript:void(0);' ondblclick='$.closeReference({value:[&quot;${node.id}&quot;],text:[&quot;${node.name}&quot;]});'">
				</@c.TreeMapBuilder>
			</@c.SimpleTree>
	 </@c.BoxBody>
	 	<@c.BoxFooter class="text-center">
	 	
	 		<@c.Button type="primary" icon="fa fa-save"   click="batchImpParamFromParamLibrary(e, '${widget_id?if_exists}')">保存</@c.Button>
			<button class="btn btn-canel" data-dismiss="modal" type="button"><i class="glyphicon glyphicon-off"></i>关闭</button>
    </@c.BoxFooter>
</@c.Box>

 
<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>选择文件</modal-title>	
<@c.Box>
	<@c.BoxBody class="padding:5px">
	     <div class="box padding:5px scrollbar"  style="width:100%;max-height:200px">
			<@c.SimpleTree id="queryTree" expand="-1" events="{clickNode: selectTemplateNode}">
				<@c.TreeMapBuilder map=nodesMap nameField="fileName" idField="filePath" valueField="filePath" titleShowWidth="200px" linkexp=r"href='javascript:void(0);' ondblclick='$.closeReference();'">
                </@c.TreeMapBuilder>
			</@c.SimpleTree>
		</div>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        	<button type="button" class="btn btn-primary" click="saveTemplate()" data-dismiss="modal"><i class="fa fa-save"></i>确定</button>
			<button class="btn btn-canel" data-dismiss="modal" type="button"><i class="glyphicon glyphicon-off"></i>关闭</button>
    </@c.BoxFooter>
</@c.Box>

<@c.Hidden id="paramterPath" name="paramterPath"  value="" />
<@c.Hidden id="rootPath" name="rootPath"  value="${rootPath}" />
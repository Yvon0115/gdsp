<#import "/gdsp/tags/castle.ftl" as c>
<modal-max></modal-max>
<modal-title>选择父级维度组</modal-title>
<div class="modal-body autoscroll">
	<@c.Hidden id="refParentId" name="refParentId" value=""/>
	<@c.Hidden id="parentname" name="parentname" value=""/>
	<@c.Hidden id="innercode" name="innercode" value=""/>
	<@c.SimpleTree id="menuTree" expand="1"  events="{clickNode: selectParentNode}">
		<@c.TreeListBuilder nodes=nodes  nameField="groupname" valueField="innercode" linkexp=r"href='javascript:void(0);' ondblclick='$.closeReference({value:[&quot;${node.innercode}&quot;],text:[&quot;${node.groupname}&quot;]});'">
		</@c.TreeListBuilder>
	</@c.SimpleTree>
</div>
<div class="modal-footer">
    <button type="button"  class="btn btn-primary" click="saveParentNode()" data-dismiss="modal"><li class="fa fa-save"></li>确定</button>
    <button type="button"  class="btn btn-default" data-dismiss="modal"><li class="glyphicon glyphicon-off"></li>取消</button>
</div>
<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>选择父级机构</modal-title>
<div class="modal-body autoscroll" style="height:255px;">
	<@c.Hidden id="refParentId" name="refParentId" value=""/>
	<@c.Hidden id="refParentName" name="refParentName" value=""/>
	<#--老树换新树
	<@c.SimpleTree id="menuTree" expand="1"  events="{clickNode: selectParentNode}">
		<@c.TreeListBuilder nodes=nodes nameField="orgname" linkexp=r"href='javascript:void(0);' ondblclick='$.closeReference({value:[&quot;${node.id}&quot;],text:[&quot;${node.orgname}&quot;]});'">
		</@c.TreeListBuilder>
	</@c.SimpleTree>-->
	<@c.EasyTreeBuilder id="menuTree" url="/grant/org/listOrg.d" levels="2" events="{onNodeSelected:selectParentNode}" selectFirstNode=true>
	</@c.EasyTreeBuilder>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" click="saveParentNode()" data-dismiss="modal"><i class="fa fa-save"></i>确定</button>
    <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
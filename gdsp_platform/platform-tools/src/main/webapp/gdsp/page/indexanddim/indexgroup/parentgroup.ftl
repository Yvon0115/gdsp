<#import "/gdsp/tags/castle.ftl" as c>
<modal-max></modal-max>
<style>
	.modal-title{
	size: 14px;
	}
	.modal-header{
	background:#f4f4f4;
	height:45px;
	}
</style>
<modal-title>选择上级指标组</modal-title>
<div class="modal-body autoscroll">
	<@c.Hidden id="refParentId" name="refParentId" value=""/>
	<@c.Hidden id="refParentName" name="refParentName" value=""/>
	<#-- <@c.SimpleTree id="menuTree" expand="1"  events="{clickNode: selectParentNode}">
		<@c.TreeListBuilder nodes=nodes nameField="groupName" valueField="innercode" linkexp=r"href='javascript:void(0);' ondblclick='$.closeReference({value:[&quot;${node.innercode}&quot;],text:[&quot;${node.groupName}&quot;]});'">
		</@c.TreeListBuilder>
	</@c.SimpleTree>-->
	<@c.EasyTreeBuilder id="ParentTree" events="{onNodeSelected:selectParentNode}" url="/indexanddim/indexgroup/parentGroup.d" checkOption="1">
	</@c.EasyTreeBuilder>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" click="saveParentNode()" data-dismiss="modal"><i class="fa fa-save"></i>确定</button>
    <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>

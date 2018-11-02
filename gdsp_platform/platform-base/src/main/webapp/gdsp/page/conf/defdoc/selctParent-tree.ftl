<#import "/gdsp/tags/castle.ftl" as c>
<modal-max></modal-max>
<modal-title>选择上级名称</modal-title>
<div class="modal-body autoscroll">
	<@c.Hidden id="refParentId" name="refParentId" value="" />
     <@c.Hidden id="refParentName" name="refParentName" value="" />
<#--  	<@c.SimpleTree id="parentTree" events="{clickNode: selectParentNode}">
		<@c.TreeMapBuilder map=voTreeMap nameField="doc_name" linkexp=r"href='javascript:void(0);'ondblclick='$.closeReference({value:[&quot;${node.id}&quot;],text:[&quot;${node.doc_name}&quot;]});'">
		</@c.TreeMapBuilder>
	</@c.SimpleTree>-->
		<@c.EasyTreeBuilder id="defDocTree" events="{onNodeSelected: selectParentNode}"  searchAble=true url="/conf/defdoc/selectParent.d?type_id=${type_id}" >
		</@c.EasyTreeBuilder>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" click="saveParentNode()" data-dismiss="modal"><i class="fa fa-save"></i>确定</button>
    <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
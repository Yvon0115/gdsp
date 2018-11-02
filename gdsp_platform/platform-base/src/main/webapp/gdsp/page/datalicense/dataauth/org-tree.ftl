<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>选择机构</modal-title>
<div class="modal-body autoscroll" style="height:500px">
	<@c.Hidden id="refId" name="refId" value=""/>
	<@c.Hidden id="refName" name="refName" value=""/>
	
	<#--  <@c.SimpleTree id="menuTree" expand="1"  events="{clickNode: selectParentNode}">
		<@c.TreeListBuilder nodes=nodes nameField="orgname" linkexp=r"href='javascript:void(0);' ondblclick='$.closeReference({value:[&quot;${node.id}&quot;],text:[&quot;${node.orgname}&quot;]});findRoles(&quot;${node.id}&quot;);reloadDataDic()'">
		</@c.TreeListBuilder>
	</@c.SimpleTree>-->
		<@c.EasyTreeBuilder id="orgTree" events="{onNodeSelected: selectParentNode}"  searchAble=true url="/power/datalicense/queryOrgNode.d" >
	</@c.EasyTreeBuilder>

</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" click="saveParentNode()" data-dismiss="modal"><i class="fa fa-save"></i>确定</button>
    <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
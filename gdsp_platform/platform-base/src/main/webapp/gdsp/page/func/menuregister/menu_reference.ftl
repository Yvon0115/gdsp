<#import "/gdsp/tags/castle.ftl" as c>
<#-- 选择上级节点  弹出层 -->
<modal-max></modal-max>
<modal-title>选择父级菜单</modal-title>
<div class="modal-body autoscroll" style="height:280px;">
	<@c.Hidden id="refParentId" name="refParentId" value=""/>
	<@c.Hidden id="refParentName" name="refParentName" value=""/>
<#--	<@c.SimpleTree id="menuTree"  events="{clickNode: selectParentNode}" expand="0">
		<@c.TreeListBuilder nodes=menuVos nameField="funname" linkexp=r"href='javascript:void(0);' ondblclick='$.closeReference({value:[&quot;${node.id}&quot;],text:[&quot;${node.funname}&quot;]});'">
		</@c.TreeListBuilder>
	</@c.SimpleTree> -->
	
	 <@c.EasyTreeBuilder id="addTree" url="/func/menu/checkparentmenu.d"  showBorder=false events="{onNodeSelected: selectParentNode}">
	
	</@c.EasyTreeBuilder>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" click="saveParentNode()" data-dismiss="modal"><i class="fa fa-save"></i>确定</button>
    <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
 
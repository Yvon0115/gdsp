<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>选择父节点</modal-title>	
<div class="autoscroll">
	<@c.Hidden id="refParentId" name="refParentId" value=""/>
	<@c.Hidden id="refParentName" name="refParentName" value=""/>
	<@c.SimpleTree id="commonDirTree" events="{clickNode: selectParentNode}">
		<@c.RecuiveLevel id="commonDirTree" url="${ContextPath}/appcfg/resourceManage/loaddirtreenode.d" recuiveField="id" recuiveParameter="parent_id" >
		</@c.RecuiveLevel>
	</@c.SimpleTree>
</div>
<div class="modal-footer">
    <button type="button"  class="btn btn-primary" data-dismiss="modal" click="saveParentNode()"><li class="fa fa-save"></li>确定</button>
    <button type="button" class="btn btn-default" data-dismiss="modal"><li class="glyphicon glyphicon-off"></li>取消</button>
</div>
 
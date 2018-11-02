<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>选择父级供应商</modal-title>
<div class="modal-body autoscroll" style="height:255px;">
	<@c.Hidden id="refParentId" name="refParentId" value=""/>
	<@c.Hidden id="refParentName" name="refParentName" value=""/>
	<@c.EasyTreeBuilder id="menuTree" url="/grant/supplier/listSup.d" levels="2" events="{onNodeSelected:selectParentNode}" selectFirstNode=true>
	</@c.EasyTreeBuilder>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" click="saveParentNode()" data-dismiss="modal"><i class="fa fa-save"></i>确定</button>
    <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
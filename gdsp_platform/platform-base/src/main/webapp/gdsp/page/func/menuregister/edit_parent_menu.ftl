<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/func/menu.js"/>-->

<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/func/menu" />
<modal-title>修改父菜单</modal-title>

<@c.Box class="modal-body" style="min-height:100%;">
 	<@c.BoxBody id="treeBody" style="width:414px;height:280px;" class="autoscroll">
 		 <@c.EasyTreeBuilder id="parentTree" url="/func/menu/editparent.d?id=${id?if_exists}"  showBorder=false events="{onNodeSelected: selectNode}">
		</@c.EasyTreeBuilder>
	</@c.BoxBody>
 	<@c.BoxFooter class="text-center" style="height : 20px">
	    <@c.Button type="primary" icon="glyphicon glyphicon-saved" action=[c.attrs({"data-dismiss":"modal"})] click="checkParentMenu('${menuRegisterVO?if_exists.id?if_exists}')">保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
 	</@c.BoxFooter>
</@c.Box>

<#-- 
xue
2017.3.9
老树换新树，在修改父菜单时，显示的选择父菜单树
增减了ui设计的显示树的样式414px*361px
-->

<#--<div class="modal-body autoscroll" style="width:414px;height:280px;">
<#--	<#include "menu-tree.ftl"> 
	 <@c.EasyTreeBuilder id="parentTree" url="/func/menu/editparent.d?id=${id?if_exists}"  showBorder=false events="{onNodeSelected: selectNode}">
	
	</@c.EasyTreeBuilder>
</div>
<div  class="modal-footer">
	<@c.Button type="primary" icon="glyphicon glyphicon-saved" action=[c.attrs({"data-dismiss":"modal"})] click="checkParentMenu('${menuRegisterVO?if_exists.id?if_exists}')">保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</div> -->
<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>收藏夹</modal-title>	
<@c.Box  class="box ">
	<@c.BoxBody >
	     <div class="box scrollbar"  style="width:100%;max-height:200px">
     		<@c.Hidden id="refParentId" name="refParentId" value=""/>
			<@c.Hidden id="refParentName" name="refParentName" value=""/>
			 <#include "tree.ftl">
		</div>
	    <@c.Form id="favoritesForm" class="form-horizontal validate" action="${ContextPath}/portal/favorites/doFavoriteFile.d" after={"afterSaveFavorites()":""} method="post">
	        <!--<@c.FormRef id="pid" name="pid" label="收藏夹" value="" showValue="" url="${ContextPath}/portal/favorites/toFavorites.d" />-->
	        <@c.FormText id="comments"  name="comments" label="备注" rows=2></@c.FormText>
	    	<@c.Hidden name="widget_id" value="${widget_id?if_exists}" />
	    	<@c.Hidden name="dirId"  />
	    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
  		<@c.Button type="primary" click="divCognosTreeSwith('treeDiv')" action=[c.saveform("#favoritesForm")]>保存</@c.Button>
  		 <button type="button" class="btn btn-default" data-dismiss="modal"><i class="glyphicon glyphicon-off"></i>关闭</button>
    </@c.BoxFooter>
</@c.Box>
 
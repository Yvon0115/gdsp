<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/favorites/favorites.js"/>-->
<@c.Script src="${__scriptPath}/favorites/favorites.js"/>
<modal-title>添加网页到收藏夹</modal-title>
<@c.Box class="modal-body">
	<@c.BoxHeader>
	<@c.Form id="favoriteForm" class="validate" action="${ContextPath}/favorites/saveFavorites.d" method="post" after={"favoritespagereload()":""}>	
			<@c.FormInput id="favoritesName" name="name" label="名称" value="${name?if_exists}"  helper="1-10位字符,仅支持汉字、字母、数字组合" validation={"required":true,"alphanumer":true,"repetitiveNameCheck":true,"minlength":1,"maxlength":10} messages={"repetitiveNameCheck":"名称重复或者未填写，请确认！"}/>
			<@c.Hidden name="pid" id="favorites_pid" value=""/>
			<@c.Hidden name="url" id="favorites_url" value="${url}"/> 
			<@c.Hidden id="fileType" name="fileType" value="2"/>
	</@c.Form>
			<@c.Hidden name="menuId"  id="menuId" value="${menuId}"/>
			<@c.Hidden name="hide_name" id="hide_name" value="${name}"/>
    </@c.BoxHeader>
    <@c.Tabs>
    <@c.Tab  id="filePanel" active=true title="目录" tabclass="liheight">
    <@c.BoxBody id="treeBody" style="height : 330px; width : 375px;" class="autoscroll">

		<@c.SimpleTree id="favoritesFileTree">
			<@c.TreeMapBuilder map=nodesMap nameField="name" linkexp=r"onclick='changeFavoritesFileNode(&quot;${node.id}&quot;)'">
			</@c.TreeMapBuilder>
		</@c.SimpleTree>

    </@c.BoxBody>
    </@c.Tab>
	</@c.Tabs>
    <@c.BoxFooter style="height : 20px">
    	<@c.Button type="primary" class="text-left" icon="fa fa-save"  click="addFavoritesFile()">新建目录</@c.Button>
        <@c.Button type="canel" class="pull-right" icon="glyphicon glyphicon-off" click="cancel()">取消</@c.Button>
        <@c.Button type="primary" class="pull-right" icon="fa fa-save"  action=[c.saveform("#favoriteForm")]>保存</@c.Button>
    </@c.BoxFooter>
</@c.Box>
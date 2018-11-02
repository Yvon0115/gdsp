<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/favorites/favorites.js"/>-->
<modal-title>创建收藏夹</modal-title>
<@c.Script src="${__scriptPath}/favorites/favorites.js"/>
<@c.Box class="modal-body">
	<@c.BoxHeader>
	<@c.Form id="input_form" class="validate" action="${ContextPath}/favorites/saveFavorites.d" method="post" after={"retunToFavorites()":""}>			
			<@c.FormInput id="favoritesFileName" name="name" label="名称" value="${name?if_exists}"  helper="1-60位字符,仅支持汉字、字母、数字组合;收藏目录与收藏网页不能同名" validation={"required":true,"alphanumer":true,"repetitiveNameCheck":true,"minlength":1,"maxlength":60} messages={"repetitiveNameCheck":"名称重复或者未填写，请确认！"}/>
			<@c.Hidden id="file_pid" name="pid" value="${pid?if_exists?default(0)}"/>  
			<@c.Hidden id="fileType" name="fileType" value="1"/>  
	</@c.Form>	
		<@c.Hidden id="menuId" name="menuId" value="${menuId}"/>  
		<@c.Hidden id="hide_name" name="hide_name" value="${name}"/>  
    </@c.BoxHeader>
    <@c.Tabs>
    <@c.Tab  id="filePanel" active=true title="目录" tabclass="liheight">
    <@c.BoxBody id="treeBody" style="height : 330px; width : 375px;" class="autoscroll">
		<@c.SimpleTree id="favoritesFileTree">
			<@c.TreeMapBuilder map=nodesMap nameField="name" linkexp=r"onclick='changeNode(&quot;${node.id}&quot;)'">
			</@c.TreeMapBuilder>
		</@c.SimpleTree>
    </@c.BoxBody>
    </@c.Tab>
	</@c.Tabs>
    <@c.BoxFooter class="text-center" style="height : 20px">
        <@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform("#input_form")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" click="cancel()">取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
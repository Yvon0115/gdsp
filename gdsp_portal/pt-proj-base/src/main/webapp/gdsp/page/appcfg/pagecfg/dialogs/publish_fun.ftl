<#import "/gdsp/tags/castle.ftl" as c>
<modal-title><#if type?exists && type=="2" >发布功能<#else>发布页面</#if></modal-title>
<@c.Box class="modal-body" style="min-height:400px;">
	<@c.BoxHeader>
		<@c.Form action="">
			<@c.FormInput name="pagename1" id="pagename1" label="发布名称:"  value="${pagename1?if_exists}" />
		</@c.Form>
	</@c.BoxHeader>
	<@c.BoxBody style="min-height:320px" class="scrollbar">
	    <@c.EasyTreeBuilder id="tree1" url="/appcfg/pagecfg/toPubTree.d?type=${type}" levels=2 events="{onNodeSelected:selectPubNode}">
	    </@c.EasyTreeBuilder>
	</@c.BoxBody>
	<@c.BoxFooter class="text-center">
		<@c.Button type="primary"  icon="fa fa-save" action=[c.attrs({"click":"doPublishFun()"})]>确定</@c.Button>
		<@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
	</@c.BoxFooter>
</@c.Box>
<@c.Hidden name="page_id" value="${page_id?if_exists}" />
<@c.Hidden name="type" value="${type?if_exists}" />
<@c.Hidden name="folderid" value="" />
		
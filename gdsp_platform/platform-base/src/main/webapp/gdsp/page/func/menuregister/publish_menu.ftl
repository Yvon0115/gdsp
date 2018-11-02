<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>发布功能</modal-title><#if callback?exists&&callback!=""><#assign after=("{"+callback+",\"$.closeDialog()\":\"\"}")?eval><#else><#assign after={"$.closeDialog()":""}></#if>
<div class="modal-body autoscroll" id="modalBodyId"><#if !(url?exists)|| url==""><#assign url="${ContextPath}/func/menu/doPublishMenu.d"></#if>
	<@c.Form id="publishForm" class="validate" action="${url}" method="post"  after=after help=false>
		<@c.FormInput name="funname" label="菜单名称" value="${menu.funname?if_exists}" validation={"required":true}/>
		<@c.Hidden name="funtype" value="${menu.funtype?if_exists}" />
		<@c.Hidden name="parentid" value="${menu.parentid?if_exists}" />
		<@c.Hidden name="url" value="${menu.url?if_exists}" />
		<@c.Hidden name="memo" value="${menu.memo?if_exists}" />
		<@c.Hidden name="pageid" value="${menu.pageid?if_exists}" />
		<@c.Hidden name="dispcode" value="${menu.dispcode?if_exists}" />
	</@c.Form>
	<@c.SimpleTree id="selectPublishMenuTree"   expand="3">
		<@c.TreeMapBuilder    map=nodes idField="id" valueField="id" nameField="funname" nexp="" linkexp="">
		</@c.TreeMapBuilder>
	</@c.SimpleTree>
</div>
<div class="modal-footer">
	<@c.Button type="primary" click="publishExternalMenu()">确定</@c.Button>
	<@c.Button type="canel" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</div>
<@c.Script src="cas/form" />
<@c.Script src="script/func/menu" />
<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>选择组件</modal-title>
<@c.Script id="widgetRef" src="script/appcfg/widgetmeta/ref"/>
<div class="modal-body" id="modalBodyId">
	<#if result??&&result?size gt 0>
	<@c.Tabs>
		<#list result["__null_key__"] as root>
			<#if root.type?? && root.type?trim!="">
				<@c.Tab id="${root.id?if_exists}" title="${root.name?if_exists}" class="padding" url="${ContextPath?if_exists}/framework/reportentry/refwidgetmetaext.d?type=${root.type?if_exists}&id=${root.id?if_exists}" active=(root_index==0)>
				</@c.Tab>
		    <#elseif root.TYPE?? && root.TYPE?trim!="">
		    	<@c.Tab id="${root.ID?if_exists}" title="${root.NAME?if_exists}" class="padding" url="${ContextPath?if_exists}/framework/reportentry/refwidgetmetaext.d?type=${root.TYPE?if_exists}&id=${root.ID?if_exists}" active=(root_index==0)>
				</@c.Tab>
		    <#else>
			    <@c.Tab id="${root.id?if_exists}" title="${root.name?if_exists}" class="padding" active=(root_index==0)>
				    <@c.SimpleTree id="tree1"   expand="-1">
					    <@c.TreeMapFilterBuilder map=result leafexp="node.leaf==1" key="${root.id?if_exists}" idField="id" valueField="id" nameField="name" nexp="<#if node.leaf==1> hasCheck='true'</#if>" linkexp=r" wmtype='${node.type?default('default')}'"/>
				    </@c.SimpleTree>
			    </@c.Tab>
			</#if>
		</#list>
	</@c.Tabs>
	</#if>
</div>
<div class="modal-footer">
	<@c.Button type="primary" icon="glyphicon glyphicon-saved" click="widgetRef.okRefMultiTree()">确定</@c.Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</div>

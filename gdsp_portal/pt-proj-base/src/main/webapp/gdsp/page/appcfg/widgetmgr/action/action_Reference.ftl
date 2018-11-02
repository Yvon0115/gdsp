<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/appcfg/widgetmgr/widget_action"/>
<modal-title>选择资源</modal-title>
<div class="modal-body" id="modalBodyId">
	<#if result??&&result?size gt 0>
	<@c.Tabs>
		<#list result["__null_key__"] as root>
			<#if root.def1?? && root.def1?trim!="">
				<@c.Tab id="${root.id}" title="${root.name}" class="padding" url="${ContextPath}/${root.def1}" active=(root_index==0)>
				</@c.Tab>
		    <#else>    
			    <@c.Tab id="${root.id}" title="${root.name}" class="padding" active=(root_index==0)>
			        <@c.Hidden id="refParentId" name="refParentId" value=""/>
	                <@c.Hidden id="refParentName" name="refParentName" value=""/>
				    <@c.SimpleTree id="tree1"   expand="-1" events="{clickNode: selectNode}">
					   <@c.TreeMapFilterBuilder map=result leafexp="node.leaf==1" key="${root.id}" idField="id" valueField="id" nameField="name" nexp="<#if node.leaf==1> hasCheck='false'</#if>" linkexp=r" wmtype='${node.type?default('default')}'"/>
				    </@c.SimpleTree>
			    </@c.Tab>
			</#if>
		</#list>
	</@c.Tabs>
	</#if>
</div>
<div class="modal-footer">
	<@c.Button type="primary" click="saveNodes()" icon="glyphicon glyphicon-saved"  action=[c.attrs({"data-dismiss":"modal"})]>确定</@c.Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-step-backward" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</div>
 
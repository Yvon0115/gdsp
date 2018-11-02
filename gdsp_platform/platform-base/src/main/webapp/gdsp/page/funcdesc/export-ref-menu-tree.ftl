<#import "/gdsp/tags/castle.ftl" as c>
<div class="scrollbar">
	<@c.SimpleTree id="menuTree" checkbox=true>
		<@c.TreeMapBuilder map=voTreeMap nameField="funname">
		</@c.TreeMapBuilder>
	</@c.SimpleTree>
</div>
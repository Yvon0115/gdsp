<#import "/gdsp/tags/castle.ftl" as c>
<div class="autoscroll">
	<@c.SimpleTree id="commonDirTree"  events="{clickNode: selectNode}">
		<@c.TreeMapBuilder map=commonDirTree nameField="dir_name">
		</@c.TreeMapBuilder>
	</@c.SimpleTree>
</div>

<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTree id="commonDirTree" events="{clickNode: selectNode}"  expand="-1">
	<@c.RecuiveLevel id="commonDirTree" url="${ContextPath}/appcfg/resourceManage/loadcognosfuntree.d" recuiveField="report_path" recuiveParameter="parent_id">
	</@c.RecuiveLevel>
</@c.SimpleTree>
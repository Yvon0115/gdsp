<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTree id="catTree" events="{clickNode: kpiLibPageJS.selectNode}"  expand="-1">
	<@c.RecuiveLevel id="catTree" url="${ContextPath}/portal/kpilibrary/loaddirtreenode.d" recuiveField="id" recuiveParameter="pid">
	</@c.RecuiveLevel>
</@c.SimpleTree>
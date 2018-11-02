<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTree id="refwidgetexttree" expand="-1">
	<@c.RecuiveLevel id="runqianTreeLevel" url="${ContextPath}/framework/reportentry/loadrunqiantreenode.d?id=${id?if_exists}&type=${type?if_exists}" recuiveField="id" recuiveParameter="parent_id">
	</@c.RecuiveLevel>
</@c.SimpleTree>


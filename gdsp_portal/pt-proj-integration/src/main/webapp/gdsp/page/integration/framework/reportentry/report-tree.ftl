<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTree id="refwidgetexttree" events="{clickNode: selectCognosNode}"  expand="-1">
	<@c.RecuiveLevel id="cognosTreeLevel" url="${ContextPath}/framework/reportentry/loadReportfuntree.d" recuiveField="id" recuiveParameter="id">
	</@c.RecuiveLevel>
</@c.SimpleTree>
<#--<@c.EasyTreeBuilder id="Tree" events="{onNodeSelected:selectCognosNode}" url="/framework/reportentry/loadReportfuntree.d" checkOption="1">
</@c.EasyTreeBuilder>	-->
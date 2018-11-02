<#import "/gdsp/tags/castle.ftl" as c>
<#-- 数据源类型树 -->
<@c.EasyTreeBuilder id="typeTree" url="/systools/ds/loadTypeTree.d" levels="1" events="{onNodeSelected:selectTypeTreeNode}" selectFirstNode=false>
</@c.EasyTreeBuilder>
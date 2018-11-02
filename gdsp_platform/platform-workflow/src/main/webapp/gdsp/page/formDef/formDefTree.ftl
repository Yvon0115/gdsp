<#import "/gdsp/tags/castle.ftl" as c>
<#-- 单据注册树 -->
<@c.EasyTreeBuilder id="formDefTypeTree" url="/workflow/category/getTreeData.d" levels="2" events="{onNodeSelected:selectNode}" selectFirstNode=true >
</@c.EasyTreeBuilder>
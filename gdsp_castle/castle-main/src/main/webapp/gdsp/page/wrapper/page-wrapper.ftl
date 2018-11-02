<#import "/gdsp/tags/castle.ftl" as c>
<@c.PageHeaderCsdc/>
<@c.PageContentCsdc>
    <#if __wrapperContent?ends_with(".jsp")>
        <@include_page path="/gdsp/page/"+__wrapperContent/>
    <#else>
        <#include "/gdsp/page/${__wrapperContent}.ftl">
    </#if>
</@c.PageContentCsdc>
<#-- 用户最近访问TOP10 -->
<#import "/gdsp/tags/castle.ftl" as c>
<#if __currentPortlet.getPreference( "height")??>
	<#assign autoHeight=false>
	<#assign height=__currentPortlet.getPreference( "height")>
<#else>
	<#assign autoHeight=true>
</#if>
<!--是否显示标题 -->
<#if __currentPortlet.getPreference("title_show")??>
	<#assign title_show=__currentPortlet.getPreference("title_show")>
	<#if title_show=="Y">
		<#assign title_show=true>
	<#else>
		<#assign title_show=false>	
	</#if>
<#else> 
	<#assign title_show=false>
</#if> 

<@c.Box class="box-portlet" style="min-height:0px;height:${height}px;overflow:auto">
	<#if title_show>
		<@c.BoxHeader class="with-border header-bg">
			<h5 class="box-title"><i class=""></i>${__currentPortlet.portletTitle}</h5>
			<div class="box-tools pull-right">
				<#if __currentPortlet.portletActions?? && __currentPortlet.portletActions?size gt 0>
					<#list __currentPortlet.portletActions as action>
						${action.template?if_exists}
					</#list>
				</#if>
			</div>
		</@c.BoxHeader>
	</#if>
    <@c.BoxBody   >
        <@c.TableLoader id="top10Vos" url="${ContextPath}/systools/log/recentVisitTop10.d">
            <#include "visitrecords-list-data.ftl">
        </@c.TableLoader>
    </@c.BoxBody>
</@c.Box>

<#import "/gdsp/tags/castle.ftl" as c>
<#-- 访问记录排行TOP10 -->
<@c.Box class="box-portlet">
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
    <@c.BoxBody   >
        <@c.TableLoader id="top10Vos" url="${ContextPath}/systools/log/res_Access_top10.d">
            <#include "simple-list-data.ftl">
        </@c.TableLoader>
    </@c.BoxBody>
</@c.Box>

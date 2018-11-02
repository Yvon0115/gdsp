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
        <@c.Box style="min-height:0px;height:${height}px;overflow:auto">
	        <#if title_show>
				<div class="box-header with-border">
					<#if __currentPortlet.getPreference("icon")??><i class="${__currentPortlet.getPreference("icon")}"></i></#if>
					<h3 class="box-title">${__currentPortlet.portletTitle}</h3>
					<div class="box-tools pull-right">
						<#if __currentPortlet.portletActions?? && __currentPortlet.portletActions?size gt 0>
							<#list __currentPortlet.portletActions as action>
								${action.template?if_exists}
							</#list>
						</#if>
					</div>
				</div>
			</#if>
	
            <@c.BoxBody class="no-padding">
                <@c.TableLoader id="noiticeVos" url="${ContextPath}/sysnotice/simpleListData.d">
                    <#include "simple-list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
        </@c.Box>

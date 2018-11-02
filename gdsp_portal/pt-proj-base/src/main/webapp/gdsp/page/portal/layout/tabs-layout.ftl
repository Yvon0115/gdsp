<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
	<#if page.layouts?? && page.layouts?size gt 0>
	<#assign layout = page.layouts[0]>
	<#if layout.portlets?? && layout.portlets?size gt 0>
		<#list layout.portlets as portlet>
	<@c.Tab  id=portlet.id title=portlet.title?default("") active=portlet_index==0>
			<#if portlet.meta.getPreference('externalResource')?? && portlet.meta.getPreference('externalResource')=='yes'>
					<#include "default-external-layout.ftl">
			<#else>
	            	<@pt.Portlet config=portlet/>
			</#if>
	</@c.Tab>
		</#list>
	</#if>
	</#if>
</@c.Tabs>

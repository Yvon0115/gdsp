<#if page.layouts?? && page.layouts?size gt 0>
	<#list page.layouts as layout>
	<#assign layoutmetacolspan = layout.meta.colspan>
	<#if layoutmetacolspan<13 >
    <section class="col-md-${layout.meta.colspan?default('4')} portlet-column connectedSortable" colspan="${colspan?default('4')}"  >
	<#else>
	<!-- 设置为md,如果是自定义布局，colspan除15获取宽度百分比，15为自定义系数，用来区别boostrap的12栅栏，设置排序样式-->
	<section class="col-md-${layout.meta.colspan?default('4')} portlet-column connectedSortable" colspan="${colspan?default('4')}" style="float: left; width:${layoutmetacolspan/15}%;"   >
	</#if>
		<#if layout.portlets?? && layout.portlets?size gt 0>
			<#list layout.portlets as portlet>
				<#if portlet.meta.getPreference('externalResource')?? && portlet.meta.getPreference('externalResource')=='yes'>
					<#include "default-external-layout.ftl">
				<#else>
	            	<@pt.Portlet config=portlet/>
				</#if>
			</#list>
		</#if>
    </section>
	</#list>
</#if>
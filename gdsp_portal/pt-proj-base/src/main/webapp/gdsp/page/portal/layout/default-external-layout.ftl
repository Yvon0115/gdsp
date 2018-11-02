<!-- 外部资源页面  -->
<#if portlet.getPreference("height")??>
<#assign autoHeight = false>
<#assign height = portlet.getPreference("height")>
<#assign bodyHeight = height?number-50 >
<#else>
<#assign autoHeight = true>
</#if>
<!-- 是否显示标题  -->
<#if portlet.getPreference("title_show")??>
	<#assign title_show=portlet.getPreference("title_show")>
	<#if title_show=="Y">
		<#assign title_show=true>
	<#else>
		<#assign title_show=false>	
	</#if>
<#else> 
	<#assign title_show=false>
</#if>
<div class="box box-portlet" <#if !autoHeight>style="min-height:0px;height:${height}px"</#if>>
	<#if title_show>
	    <div class="box-header border header-bg">
	        <h5 class="box-title">${portlet.portletTitle}</h5>
	    </div>
	   <!-- 显示标题，则iframe的高度用bodyHeight，该高度是height减去标题栏的高度值  -->
	    <div class="box-body" style="height:${bodyHeight}px">
	        <iframe src="${portlet.meta.portletURL}" frameborder="0" width="100%" height="100%" scrolling="${(!autoHeight)?string}"></iframe>
	   	</div>
    <#else>
    	<!-- 不显示标题，直接用height高度  -->
	    <div class="box-body" style="height:${height}px">
	        <iframe src="${portlet.meta.portletURL}" frameborder="0" width="100%" height="100%" scrolling="${(!autoHeight)?string}"></iframe>
	   	</div>
    </#if>
    
    
</div>
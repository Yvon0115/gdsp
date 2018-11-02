<#-- 页面布局控件封装 -->
<#-- 页面头部 -->
<#macro PageHeader title="" breads=[] 
	jtabstitlesid="jtabs-titlesid-xg" jtabscontentsid="jtabs-contentsid-xg" jtabstitleclass="col-md-12">
	
	  <#if __modeRefreshMain=='yes'>
	   	<section class="content-header" style="height:38px;">
	  		<div id="${jtabstitlesid}" 
	  			jtabs-init="add" class="col-md-12"
	  			jtabs-titlesid="${jtabstitlesid}" jtabs-contentsid="${jtabscontentsid}" jtabs-monitor="jtabs-sidebar-menu-xg"
	  			jtabs-init-id="${(__tab_id__)!''}" jtabs-init-url="${(__tab_url__)!''}" jtabs-init-title="${(__tab_title__)!''}" 
	  			jtabs-size="8" jtabs-titlelength="5">
	  		</div>
	  	</section>
	  <#elseif __modeRefreshMain=='no'>
	  	<#if breads?size gt 0>
	    	<section class="content-header" >
    			<ol class="breadcrumb" breads=[]>
				    <#list breads as path>
					    <#if !path_has_next>
		                    <li class="active"><#if path_index ==0><i class="fa fa-dashboard"></i></#if>${path.key}</li>
					    <#elseif path_index ==0>
		                    <li><a href="${ContextPath}/${path.value}"><i class="fa fa-dashboard"></i>${path.key}</a></li>
					    <#else>
		                    <li><a>${path.key}</a></li>
					    </#if>
				    </#list>
		        </ol>
			</section>
	    <#elseif Context.requestInfo?? && Context.requestInfo.breadCrumb?? && Context.requestInfo.breadCrumb?size gt 0>
	    	<section class="content-header" >
    			<ol class="breadcrumb" breads=[]>
				    <#local breads=Context.requestInfo.breadCrumb/>
				    <#list breads as path>
					    <#if !path_has_next>
		                    <li class="active"><#if path_index ==0><i class="fa fa-home"></i></#if>${path.key}</li>
				        <#else>
		                    <li><a <#if path.value??&& path.value?length gt 0>href="${ContextPath}/${path.value}"</#if>><#if path_index ==0><i class="fa fa-home"></i></#if>${path.key}</a></li>
				        </#if>
				    </#list>
		        </ol>
			</section>
	    <#else>
            <section class="content-header" ><div class="breadcrumb-placeholder"></div></section>
	    </#if>
	  <#else>
	  </#if>
</#macro>
<#-- 页面内容框 -->
<#macro PageContent id="jtabs-contentsid-xg">
<section class="content" <#if __modeRefreshMain=='yes'> style="padding-left:5px;padding-top:10px;padding-right:1px;" </#if>>
<#if __modeRefreshMain=='yes'>
<div class="tab-content" <#if id!=''> id="${id}"</#if>>
	<#nested >
</div>
<#else>
	<#nested >
</#if>
</section>
</#macro>

<#-- 页面Grid布局 行-->
<#macro GRow style="">
<div class="row" style="${style}">
<#nested >
</div>
</#macro>
<#-- 页面Grid布局 单元格-->
<#macro GCell cols=12>
<div class="col-xs-${cols}">
	<#nested >
</div>
</#macro>
<#-- 页面Grid布局 单行单列-->
<#macro SRCell cols=12>
<div class="row">
	<div class="col-xs-${cols}">
		<#nested >
	</div>
</div>
</#macro>

<#-- 页面内容框 -->
<#macro Box type="" class="" style="" id="">
<#if type !="" ><#local type=" "+type?ensure_starts_with("box-")></#if>
<div class="box${type} ${class}" ${attrs({"style":style,"id":id})}>
	<#nested >
</div>
</#macro>
<#-- 页面内容框头部 -->
<#macro BoxHeader title="" class="" style="" id="">
<div class="box-header ${class}" ${attrs({"style":style,"id":id})}>
	<#if title!=""><h3 class="box-title">${title}</h3></#if>
	<#nested >
</div>
</#macro>
<#-- 页面内容框头部 -->
<#macro BoxTools right=false class=""  style="" id="">
<div class="box-tools <#if right>pull-right </#if>${class}" ${attrs({"style":style,"id":id})}>
    <#nested >
</div>
</#macro>
<#-- 页面内容框内容部分 -->
<#macro BoxBody class="" style="" id="">
<div class="box-body ${class}" ${attrs({"style":style,"id":id})}><#nested ></div>
</#macro>
<#-- 页面内容框内容部分 -->
<#macro BoxFooter class="" style="" id="">
<div class="box-footer ${class}" ${attrs({"style":style,"id":id})}>
	<#nested >
</div>
</#macro>

<#-- 简单页面内容框 -->
<#macro SimpleBox title="" type="primary" class="" hclass="" bclass="">
<div class="box box-${type} ${class}">
	<#if title!=""><div class="box-header ${hclass}">
		<h3 class="box-title">Inbox</h3>
    </div></#if>
    <div class="box-body ${bclass}">
	<#nested >
    </div>
</div>
</#macro>

<#macro ToolBar class="">
<div class="box-toolbar ${class}">
    <#nested >
</div>
</#macro>

<#-- 页面头部 -->
<#macro PageHeaderCsdc title="" breads=[] 
	jtabstitlesid="jtabs-titlesid-xg" jtabscontentsid="jtabs-contentsid-xg" jtabstitleclass="col-md-12">
	
	  <#if __modeRefreshMain=='yes'>
	   	<section class="content-header-csdc" style="height:35px;margin-left: 5px;">
	  		<div id="${jtabstitlesid}" 
	  			jtabs-init="add" class="col-md-12"
	  			jtabs-titlesid="${jtabstitlesid}" jtabs-contentsid="${jtabscontentsid}" jtabs-monitor="jtabs-sidebar-menu-xg"
	  			jtabs-init-id="${(__tab_id__)!''}" jtabs-init-url="${(__tab_url__)!''}" jtabs-init-title="${(__tab_title__)!''}" 
	  			jtabs-size="8" jtabs-titlelength="5" style="height:100%;padding:5px 0px 0px 0px;z-index:0;">
	  		</div>
	  	</section>
	  <#elseif __modeRefreshMain=='no'>
	  	<#if breads?size gt 0>
	    	<section class="content-header" >
    			<ol class="breadcrumb" breads=[]>
				    <#list breads as path>
					    <#if !path_has_next>
		                    <li class="active"><#if path_index ==0><i class="fa fa-dashboard"></i></#if>${path.key}</li>
					    <#elseif path_index ==0>
		                    <li><a href="${ContextPath}/${path.value}"><i class="fa fa-dashboard"></i>${path.key}</a></li>
					    <#else>
		                    <li><a>${path.key}</a></li>
					    </#if>
				    </#list>
		        </ol>
			</section>
	    <#elseif Context.requestInfo?? && Context.requestInfo.breadCrumb?? && Context.requestInfo.breadCrumb?size gt 0>
	    	<section class="content-header" >
    			<ol class="breadcrumb" breads=[]>
				    <#local breads=Context.requestInfo.breadCrumb/>
				    <#list breads as path>
					    <#if !path_has_next>
		                    <li class="active"><#if path_index ==0><i class="fa fa-home"></i></#if>${path.key}</li>
				        <#else>
		                    <li><a <#if path.value??&& path.value?length gt 0>href="${ContextPath}/${path.value}"</#if>><#if path_index ==0><i class="fa fa-home"></i></#if>${path.key}</a></li>
				        </#if>
				    </#list>
		        </ol>
			</section>
	    <#else>
            <section class="content-header" ><div class="breadcrumb-placeholder"></div></section>
	    </#if>
	  <#else>
	  </#if>
</#macro>
<#-- 页面内容框 -->
<#macro PageContentCsdc id="jtabs-contentsid-xg">
<section <#if __modeRefreshMain=='yes'> style="padding-left:5px;padding-right:1px;" <#else>class="content"</#if>>
<#if __modeRefreshMain=='yes'>
<div class="tab-content" <#if id!=''> id="${id}"</#if>>
	<#nested >
</div>
<#else>
	<#nested >
</#if>
</section>
</#macro>
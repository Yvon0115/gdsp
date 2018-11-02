<#if __currentPortlet.getPreference("height")??><#assign autoHeight = false><#assign height = __currentPortlet.getPreference("height")><#else><#assign autoHeight = true></#if>
<div class="box box-portlet" <#if !autoHeight>style="height:${height}"</#if>>
    <div class="box-header border header-bg">
        <h5 class="box-title"><#if __currentPortlet.getPreference("icon")??><i class="${__currentPortlet.getPreference("icon")}"></i></#if>${__currentPortlet.portletTitle}</h5>
        <#if __currentPortlet.portletActions?? &&  __currentPortlet.portletActions?size gt 0>
        <div class="box-tools pull-right">
	        <#list __currentPortlet.portletActions as action>
	        <#assign _temp = action.template?interpret>
            <@_temp/>
	        </#list>
        </div>
        </#if>
    </div><!-- /.box-header -->
    <div class="box-body"><#assign url = __currentPortlet.getPreference("url")?default("about:blank")>
        <iframe src="<#if url?starts_with("/")>${ContextPath}</#if>${url}" frameborder="0" width="100%" height="100%" scrolling="${(!autoHeight)?string}"></iframe>
    </div><!-- /.box-body -->
</div>
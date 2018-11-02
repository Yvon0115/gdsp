<#-- 动作指令封装 -->
<#--utils-->
<#function toJsonString var first=true>
	<#if var?is_hash>
		<#if var?keys?size == 0>
			<#return "">
		</#if>
		<#local r="">
		<#list var?keys as k><#local v = toJsonString(var[k],false)><#if v != ""><#local r=r+"\""+k+"\":"+v><#if k_has_next><#local r=r+","></#if></#if></#list>
		<#if r == "">
			<#return "">
		</#if>
		<#return "{"+r+"}">
	<#elseif var?is_indexable>
		<#if var?size == 0>
			<#return "">
		</#if>
		<#local r="">
		<#list var as k><#local v = toJsonString(var[k],false)><#if v != ""><#local r=v><#if k_has_next><#local r=r+","></#if></#if></#list>
		<#if r == "">
			<#return "">
		</#if>
		<#return "["+r+"]">
	<#elseif var?is_boolean>
		<#return var?string>
	<#elseif var?is_number>
		<#return var?string>
	<#elseif var?trim == "">
		<#return "">
	<#elseif first>
		<#return var?string>
	<#else>
		<#return "\""+var?string+"\"">
	</#if>
</#function>
<#function attr name value>
	<#local value = toJsonString(value)?html>
	<#if value == "">
		<#return "">
	<#else>
		<#return name+"=\""+value+"\"">
	</#if>
</#function>
<#function attrs values>
	<#local r><#list values?keys as key> ${attr(key,values[key])}</#list></#local>
	<#return r>
</#function>
<#function booleanValue value>
	<#if value?is_boolean>
		<#return value>
	<#elseif (value?is_string&&value!="")||(value?is_number&&value!=0)||(value?is_collection&&value?size gt 0)||(value?is_hash&&value?size gt 0)>
		<#return true>
	<#else>
		<#return false>
	</#if>
</#function>
<#function out value out>
	<#if value!="">
		<#return out>
	<#else>
		<#return "">
	</#if>
</#function>

<#--command-->
<#function cmd_confirm message>
	<#return "confirm=\""+message+"\"">
</#function>
<#--command-->
<#function cmd_dataloader target>
	<#return "dataloader=\""+target+"\"">
</#function>
<#--command-->
<#function cmd_checker checkbox>
	<#local box="">
	<#if checkbox?is_hash>
		<#if checkbox["box"]??><#local box=" checker-box=\""+checkbox["box"]+"\""></#if>
		<#local name=checkbox["name"]>
	<#elseif checkbox?is_indexable>
		<#if checkbox?size gt 1><#local box=" checker-box=\""+checkbox[1]+"\""></#if>
		<#local name=checkbox[0]>
	<#else>
		<#local name=checkbox>
	</#if>
	<#return "checker=\""+name+"\""+box >
</#function>
<#function cmd_switchtab target>
	<#local url="">
	<#local reload="">
	<#local callback="">
	<#if target?is_hash>
		<#if target["url"]??><#local url=" switchtaburl=\""+target["url"]+"\""></#if>
		<#if target["reload"]??><#local reload=" switchtabreload=\""+target["reload"]+"\""></#if>
		<#if target["callback"]??><#local callback=" switchtabcallback=\""+target["callback"]+"\""></#if>
		<#local target=target["target"]>
	<#elseif target?is_indexable>
		<#if target?size gt 1><#local url=" switchtaburl=\""+target[1]+"\""></#if>
		<#if target?size gt 2><#local reload=" switchtabreload=\""+target[2]+"\""></#if>
		<#if target?size gt 3><#local callback=" switchtabcallback=\""+target[3]+"\""></#if>
		<#local target=target[0]>
	#
	</#if>
	<#return "switchtab=\""+target+"\""+url+reload+callback >
</#function>
<#function cmd_pageload target>
	<#local url="">
	<#local global="">
	<#if target?is_hash>
		<#if target["url"]??><#local url=" pageurl=\""+target["url"]+"\""></#if>
		<#if target["global"]??><#local reload=" globalload=\""+target["global"]+"\""></#if>
		<#if target["target"]??><#local target=target["target"]><#else><#local target=""></#if>
	<#elseif target?is_indexable>
		<#if target?size gt 1><#local url=" pageurl=\""+target[1]+"\""></#if>
		<#if target?size gt 2><#local reload=" globalload=\""+target[2]+"\""></#if>
		<#local target=target[0]>
	</#if>
	<#return "pageload=\""+target+"\""+url+global >
</#function>
<#--actions-->
<#function dismiss target="modal">
	<#return "data-dismiss=\"${target}\"">
</#function>
<#function opentab target url="" reload=true>
<#return "data-toggle=\"activetab\" href=\"${url}\" data-target=\"${target}\" reload=\"${reload?string}\"">
</#function>

<#function opendlg target url="" height="" width="" max=false cached=false wrapped=true>
<#return "modal-toggle=\"modal\" href=\"${url}\" data-target=\"${target}\" modal-cached=\"${cached?string}\" modal-wrapped=\"${wrapped?string}\""+max?string(" modal-max=\"true\"","")  + attrs({"modal-height":height,"modal-width":width})>
</#function>

<#function saveform target>
	<#return "cas-form=\"${target}\"">
</#function>

<#function xquery target>
	<#return "cas-query=\"${target}\"">
</#function>

<#function rpc url after={} before={} method="GET">
	<#if before?keys?size gt 0>
		<#local b> ajax-before="${before?keys?join(";")}" <#list before?keys as key><#local md="cmd_"+key><#if md?eval?is_macro><#if before[key]?is_string&&before[key]==""><#local e=(md+"()")?eval><#else><#local e=(md+"(before[key])")?eval></#if> ${e}</#if></#list></#local>
	<#else>
		<#local b="">
	</#if>
	<#if after?keys?size gt 0>
		<#local a> ajax-after="${after?keys?join(";")}" <#list after?keys as key><#local md="cmd_"+key><#if md?eval?is_macro><#if after[key]?is_string&&after[key]==""><#local e=(md+"()")?eval><#else><#local e=(md+"(after[key])")?eval></#if> ${e}</#if></#list></#local>
	<#else>
		<#local a="">
	</#if>
	<#return "href=\""+url+"\" ajax-method=\""+method+"\" ajax-toggle=\"ajax\""+b+a>
</#function>

<#function dataloader target params={} reset=false hold="">
	<#if params?keys?size gt 0>
	    <#local ps = " dataloader-param=\""+toJsonString(params)?html+"\"">
	<#else>
        <#local ps = "">
	</#if>
	<#return " dataloader-toggle=\"${target}\"" + ps +reset?string(" dataloader-reset=\"true\"","")+" "+attr("dataloader-hold",hold)>
</#function>


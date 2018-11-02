<#assign coditiontype=reportMetaVO.paramFilePath>
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

<div class="box-group portlet-kpi" id="accordion">
    <div class="panel box box-primary" style="margin-bottom: 1px;min-height:0px">
<#if title_show>
	      <div class="box-header with-border">
		        <a id="aqueryid_b${__currentPortlet.id}" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo${__currentPortlet.id}" aria-expanded="true" class="collapsed">
				<h3 class="box-title">${__currentPortlet.portletTitle}</h3>
                </a>
		    	<div class=" pull-right">
					<#include "smartbi-button.ftl">
				</div>
	      </div>
</#if>  	      
	      <@c.Hidden id="ifShow${__currentPortlet.id}" name="" value="${reportMetaVO.menuType}" />
	  <#if coditiontype?index_of(".jsp")!=-1>
	      <div id="collapseOne${__currentPortlet.id}" class="panel-collapse collapse in">
	         <div class="box-body">
	        	<div  style="max-height:500px;padding:10px;"  >
					<@c.Form method="post" id="queryForm${__currentPortlet.id}" cols=12 isAjax=false  target="report_frame${__currentPortlet.id}" action="${reportMetaVO.queryUrl?if_exists}" >
							<@include_page path= "/gdsp/page/portlet/condition/${reportMetaVO.paramFilePath?if_exists}"/>
						<#-- 数据权限项,对应smartbi报表参数项,有多少添加多少 -->
						<#if dataDic??>
							<#list dataDic?keys as key>
								<input type="hidden" name="param.${key}" id="${key}dic" value="${dataDic[key]}"/>
							</#list>
						</#if>
						 <input type="hidden" name="userOrgCode" id="${orgcode}org" value="${orgcode}" />
						 <input type="hidden" name="userAccount" id="${userAccount}account" value="${userAccount}"/>
					</@c.Form>
				</div>
	         </div>
          </div>
       <#else>

          <div id="collapseOne${__currentPortlet.id}">
	         <div>
	        	<div>
					<@c.Form method="post" id="queryForm${__currentPortlet.id}" cols=12 isAjax=false  target="report_frame${__currentPortlet.id}" action="${reportMetaVO.queryUrl?if_exists}" >
							 <#include "/gdsp/page/portlet/condition/${reportMetaVO.paramFilePath?if_exists}">
						<#-- 数据权限项,对应smartbi报表参数项,有多少添加多少 -->
						<#if dataDic??>
							<#list dataDic?keys as key>
								<input type="hidden" name="param.${key}" id="${key}dic" value="${dataDic[key]}"/>
							</#list>
						</#if>
						 <input type="hidden" name="userOrgCode" id="${orgcode}org" value="${orgcode}" />
						 <input type="hidden" name="userAccount" id="${userAccount}account" value="${userAccount}"/>
					</@c.Form>
				</div>
	         </div>
          </div> 
        </#if>        
    </div>
    
  
    
  <div class="panel" style="margin-top:-4px">
      <div id="collapseTwo${__currentPortlet.id}" class="panel-collapse collapse in">
        <div class="box-body" style="padding:0px">
        	<div class="box-body " <#if !autoHeight>style="height:${height}px;"</#if>>
				<iframe id="report_frame${__currentPortlet.id}" name="report_frame${__currentPortlet.id}" frameborder="0" width="100%" height="100%" ></iframe>
			</div>
			<div style="display:none" class="div-block"><iframe name="temp_frame" src=""></iframe></div>
				<#--<#include "kpi-explain.ftl">-->
        </div>
      </div>
 	 </div>
  </div>
  <@c.Script src="${__jsPath}/plugins/comp.js"  />
  <@c.Script src="script/integration/initQuery" onload="initReportData()" />
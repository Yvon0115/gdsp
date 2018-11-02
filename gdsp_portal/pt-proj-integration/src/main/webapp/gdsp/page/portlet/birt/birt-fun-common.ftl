<#assign coditiontype=reportMetaVO.paramFilePath>
<div class="box-group portlet-kpi" id="accordion">
    <div class="panel box box-primary" style="margin-bottom: 1px;">
	      <div class="box-header with-border">
		        <a id="aqueryid_b${__currentPortlet.id}" aria-expanded="true" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo${__currentPortlet.id}"  class="collapsed">
   					<h3 class="box-title">${__currentPortlet.portletTitle}</h3>
          		</a>
		    	<div class="box-tools pull-right">
					<#include "birt-button.ftl">
				</div>
	      </div>
	      
	      <@c.Hidden id="ifShow${__currentPortlet.id}" name="" value="${reportMetaVO.menuType}" />
	      <div id="collapseOne${__currentPortlet.id}" class="panel-collapse collapse in">
	         <div class="box-body">
	        	<div  style="max-height:500px;padding:10px;"  >
					<@c.Form method="post" id="queryForm${__currentPortlet.id}" cols=12 isAjax=false  target="report_frame${__currentPortlet.id}" action="${reportMetaVO.queryUrl?if_exists}" >
						<#if coditiontype?index_of(".jsp")!=-1>
							<@include_page path= "/gdsp/page/portlet/condition/${reportMetaVO.paramFilePath?if_exists}"/>
						<#else>
                             <#include "/gdsp/page/portlet/condition/${reportMetaVO.paramFilePath?if_exists}">
						</#if>
						<#if dataDic??>
							<#list dataDic?keys as key>
							<input type="hidden" name="${key}" id="${key}dic" value="${dataDic[key]}"/>
							</#list>
						</#if>
						 <input type="hidden" name="userOrgCode" id="${orgcode}org" value="${orgcode}" />
						 <input type="hidden" name="userAccount" id="${userAccount}account" value="${userAccount}"/>
					</@c.Form>
				</div>
	         </div>
          </div>
    </div>
     <div class="panel box "style="margin-top:-4px">
      <div id="collapseTwo${__currentPortlet.id}" class="panel-collapse collapse in" >
        <div class="box-body" style="padding:0px">
        	<div class="box-body "  <#if !autoHeight>style="height:${height}px;"</#if>>
			<iframe id="report_frame${__currentPortlet.id}" name="report_frame${__currentPortlet.id}" src="" frameborder="0" width="100%"  <#if !autoHeight>style="height:${height}px;"</#if> scrolling="${(!autoHeight)?string}" ></iframe>
			</div>
				<#include "kpi-explain.ftl">
        </div>
      </div>
  </div>
  </div>
   <@c.Script src="${__jsPath}/plugins/comp.js"  />
   <@c.Script src="script/integration/birtInitQuery"  />

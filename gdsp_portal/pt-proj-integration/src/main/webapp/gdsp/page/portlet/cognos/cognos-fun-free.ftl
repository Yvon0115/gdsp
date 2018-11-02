  <div class="box-group portlet-kpi" id="accordion">
    <!-- we are adding the .panel class so bootstrap.js collapse plugin detects it -->
    <div class="panel box box-primary">
      <div class="box-header with-border">
        <a id="aqueryid_b${__currentPortlet.id}" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo${__currentPortlet.id}" aria-expanded="true" class="collapsed">
           		<#if __currentPortlet.getPreference("icon")??><i class="${__currentPortlet.getPreference("icon")}"></i></#if>
				<h3 class="box-title">${__currentPortlet.portletTitle}</h3>
          </a>
    	<div class=" pull-right">
			<#include "cognos-button.ftl">
		</div>
      </div>
       <@c.Hidden id="ifShow${__currentPortlet.id}" name="" value="" />
      <div id="collapseOne" class="panel-collapse collapse in">
        <div class="box-body">
        	<div   style="max-height:500px;padding:10px;"  >
				<@c.Form method="post" class="validate" isAjax=false  target="reportC${__currentPortlet.id}" action="${reportMetaVO.url?if_exists}" before={"beforeSubmitReport(op,'queryForm${__currentPortlet.id}')":""}  id="queryForm${__currentPortlet.id}">
					<#include "custom-free.ftl">
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
    <div class="panel box  " style="margin-top:-4px">
      <div id="collapseTwo${__currentPortlet.id}" class="panel-collapse collapse in">
        <div class="box-body"style="padding:0px">
    		<div class="box " width="100%"  <#if !autoHeight>style="height:${height}px;"</#if>>
				<iframe name="reportC${__currentPortlet.id}" src="" frameborder="0" width="100%" height="100%" scrolling="${(!autoHeight)?string}"></iframe>
			</div> 
			<#include "kpi-explain.ftl">
        </div>
      </div>
    </div>
  </div>
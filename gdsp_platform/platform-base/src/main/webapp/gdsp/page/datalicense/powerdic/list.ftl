<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/datalicense/dicPower.js"/>-->
<@c.Script src="script/datalicense/dicPower" />
<link type="text/css" rel="stylesheet" href="${__cssPath}/datalicense/dataLicense.css" />
<@c.Hidden id="dataSourceId" name="dataSourceId" value="" />
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
		<div class="col-md-3 no-padding" >
			<@c.Box class="box-left">
				 <@c.BoxHeader class="border header-bg">
				 	 <h3 class="box-title">数据源</h3>
				 </@c.BoxHeader>
		         <@c.BoxBody class="no-padding scrollbar" style="height:555px;line-height:26px;">
					<#list dataSourceList?if_exists as dataSourceVO>
						 <a class="datas" id="${dataSourceVO.id}" href="javascript:void(0)"  onclick="selectList('${dataSourceVO.id}')">
						  [${dataSourceVO.type}].${dataSourceVO.name}
						  </a>
					<#if dataSourceVO_has_next!=true>
						<#break>
					</#if>
					</#list>
				 </@c.BoxBody>
			</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
	        <@c.Box class="box-right">
	            <@c.BoxHeader class="border header-bg">
	            <@c.Search class="pull-right"  target="#powerDicContent" conditions="dic_name,dic_code" placeholder="类型名称/类型编码"  />
		            <@c.Button type="primary"  icon="glyphicon glyphicon-plus"  click="addData()">添加</@c.Button>
		            <@c.Button icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/datalicense/powerdic/deleteData.d",{"dataloader":"#powerDicContent"},{"checker":["id","#powerDicContent"],"confirm":"确认解除关联？"})]>删除</@c.Button>
	            </@c.BoxHeader>
	            <@c.BoxBody>
	                <@c.TableLoader id="powerDicContent" url="${ContextPath}/datalicense/powerdic/listData.d">
	                    <#include "list-data.ftl">
	                </@c.TableLoader>
	            </@c.BoxBody>
		        <@c.BoxFooter>
		        	<@c.Pagination class="pull-right"  target="#powerDicContent" page=dataSource?default("")/>
		        </@c.BoxFooter>
	        </@c.Box>
		</div>
	</@c.Tab>
	<@c.Tab  id="detailPanel" >
	</@c.Tab>
</@c.Tabs>
<#import "/gdsp/tags/castle.ftl" as c>
<#if kpis??>
	<@c.SimpleTable striped=false titles=["指标名称","指标别名","说明"] checkboxfield="pk_index" keys=["indexInfoVO.indexName","indexInfoVO.indexName","indexInfoVO.businessbore"]  ellipsis={"name":"200px","comments":"200px"} data=kpis.content checkbox=true/>
	<#if (kpis.totalPages > 0)>
		<@c.PageData page=kpis />
    	<@c.Pagination class="pull-right" target="#KpiContent" page=kpis/>
    </#if>
</#if>
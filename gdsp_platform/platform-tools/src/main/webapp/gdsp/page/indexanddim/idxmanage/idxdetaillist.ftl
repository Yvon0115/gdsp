<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg">
	<@c.Box>
        <@c.BoxHeader class="border header-bg">
        	<@c.Button  icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
        </@c.BoxHeader>
        <@c.BoxBody>
		    <@c.Form  id="indexInfoForm" cols=2 class="validate" action="" method="post" after={"switchtab":"#mainPanel","dataloader":"#indexListContent"}>
		        <@c.FormIdVersion id="${idxdetail?if_exists.id?if_exists}" version="${idxdetail?if_exists.version?default(0)}"/>
		        <@c.FormInput name="indexCode" label="指标编码" readonly=true value="${idxdetail?if_exists.indexCode?if_exists}" />
		        <@c.FormInput name="indexName" label="指标名称" readonly=true value="${idxdetail?if_exists.indexName?if_exists}" />
		        <@c.FormInput name="indexCodeName" label="指标值字段名" readonly=true value="${idxdetail?if_exists.indexCodeName?if_exists}" />
			
				<@c.FormItem id="" label="统计频度"  style="disabled:true;width:1400px">
					<#list defDocList as defDocList>
						<input type="checkbox" disabled="true"  style="disabled:true;" name="statfreq" validation={"required":true,"minlength":1} class="form-box" value="${defDocList?if_exists.doc_code?if_exists}" <#if tags?if_exists?contains("${defDocList?if_exists.doc_code?if_exists}")>checked</#if>>${defDocList?if_exists.doc_name?if_exists}</input>&nbsp;&nbsp;
					</#list>
			</@c.FormItem>        	
		        <@c.FormInput name="indexColumnName" label="指标编码字段名" readonly=true value="${idxdetail?if_exists.indexColumnName?if_exists}" />
		        <@c.FormInput name="indexTableName" label="指标表名称" readonly=true value="${idxdetail?if_exists.indexTableName?if_exists}" />
		        <@c.FormInput name="comedepart" label="归属部门" readonly=true value="${idxdetail?if_exists.comedepart?if_exists}" />
		        <@c.FormInput name="datasource" label="数据来源" readonly=true value="${idxdetail?if_exists.datasource?if_exists}" />
		        <@c.FormInput name="peicision" label="精度" readonly=true value="${idxdetail?if_exists.peicision?if_exists}" />
		        <@c.FormInput name="meterunit" label="计量单位" readonly=true value="${idxdetail?if_exists.meterunit?if_exists}" />
		       <@c.FormCheckBox name="onlyflexiablequery"  disabled=true label="适用灵活查询" value=idxdetail?if_exists.onlyflexiablequery?default("N") checkValue="Y" style="margin-top:5px;" helper="此指标灵活查询功能可使用"/>
		        <@c.FormText name="businessbore" label="业务口径描述" readonly=true validation={"maxlength":512}>${idxdetail?if_exists.businessbore?if_exists}</@c.FormText>
		        <@c.FormText name="techbore" label="技术口径描述" readonly=true validation={"maxlength":512}>${idxdetail?if_exists.techbore?if_exists}</@c.FormText>
		        <@c.FormText name="remark" label="备注" readonly=true validation={"maxlength":512}>${idxdetail?if_exists.remark?if_exists}</@c.FormText>
			</@c.Form>
		</@c.BoxBody>
	</@c.Box>
</@c.Tabs>

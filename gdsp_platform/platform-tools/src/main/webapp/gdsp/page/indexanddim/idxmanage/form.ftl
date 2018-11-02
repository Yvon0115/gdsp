<#import "/gdsp/tags/castle.ftl" as c>
<#-- 指标库管理 - 添加指标 
<@c.Script id="idxform" src="script/indexanddim/idxform"/>-->
<script src="${__scriptPath}/indexanddim/idxform.js"></script>
<#--<style>
	.box-title{
	color:#010101;
	}
	.boxTitle{
	font-size:14px;
	}
	
</style>-->
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title" ><#if indexinfo??&& indexinfo.id??>修改指标<#else>添加指标</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
	    <@c.Form  id="indexInfoForm"  class="validate" action="${ContextPath}/index/indexmanager/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#indexListContent"}>
	        <@c.FormIdVersion id="${indexinfo?if_exists.id?if_exists}" version="${indexInfo?if_exists.version?default(0)}"/>
	        <@c.FormInput name="indexCode" label="指标编码" value="${indexinfo?if_exists.indexCode?if_exists}" helper="1-10位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events="{blur :function(){$Utils.validInputSpeChar(this)}}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":10,"remote":"${ContextPath}/index/indexmanager/uniqueCheck.d?id=${indexinfo?if_exists.id?if_exists}"}  messages={"remote":"指标编码不能重复，请确认！"} />
	        <@c.FormInput name="indexName" label="指标名称" value="${indexinfo?if_exists.indexName?if_exists}" helper="1-60位字符" events="" validation={"required":true,"minlength":1,"maxlength":60} />
	       <#-- <@c.FormInput name="indexCodeName" label="指标值字段名" value="${indexinfo?if_exists.indexCodeName?if_exists}" helper="1-32位字符" events="" validation={"required":true,"minlength":1,"maxlength":32} />
			 <@c.FormItem id="" label="统计频度" required=true style="width:1400px">
					<#list defDocList as defDocList>
						<input type="checkbox" name="statfreq"  validation={"required":true,"minlength":1} class="form-box" value="${defDocList?if_exists.doc_code?if_exists}" <#if tags?if_exists?contains("${defDocList?if_exists.doc_code?if_exists}")>checked</#if>>${defDocList?if_exists.doc_name?if_exists}</input>&nbsp;&nbsp;
					</#list>
			</@c.FormItem>
			<@c.FormItem id="" label="统计频度" required=true >
        			<div style="margin-top:5px;">
        				<#list defDocList as defDocList>
        					<input type="checkbox"  name="statfreq" validation={"required":true,"minlength":1} value="${defDocList?if_exists.doc_code?if_exists}"   <#if tags?if_exists?contains("${defDocList?if_exists.doc_code?if_exists}")>checked</#if>>${defDocList?if_exists.doc_name?if_exists}</input>&nbsp;&nbsp;
        				</#list>
					</div>
			</@c.FormItem>       	
	        <@c.FormInput name="indexColumnName" label="指标编码字段名" value="${indexinfo?if_exists.indexColumnName?if_exists}" helper="" events="" validation={"required":true} />
	        <@c.Hidden name="indexTableId" value="${indexinfo?if_exists.indexTableId?if_exists}"/>
	        <@c.FormRef id="indexTableName" label="指标表名称" name="indexTableName" placeholder="" validation={"required":true} value="${indexinfo?if_exists.indexTableName?if_exists}" showValue="${indexinfo?if_exists.indexTableName?if_exists}" url="${ContextPath}/index/indexmanager/findIdxList.d?type_id="+type_id />
	        <@c.FormRef id="comedepart" label="归属部门" name="comedepart" placeholder="" value="${indexinfo?if_exists.comedepart?if_exists}" showValue="${indexinfo?if_exists.comedepart?if_exists}" url="${ContextPath}/index/indexmanager/orgList.d" />
	        <@c.FormInput name="datasource" label="数据来源" value="${indexinfo?if_exists.datasource?if_exists}" helper="" events="" validation={} />
	        <@c.FormInput name="peicision" label="精度" value="${indexinfo?if_exists.peicision?if_exists}" helper="" events="" validation={"minlength":1,"maxlength":10} />
	        <@c.FormInput name="meterunit" label="计量单位" value="${indexinfo?if_exists.meterunit?if_exists}" helper="" events="" validation={"minlength":1,"maxlength":10} />
	        <@c.FormCheckBox name="onlyflexiablequery"   label="适用灵活查询" value=indexinfo?if_exists.onlyflexiablequery?default("N") checkValue="Y" style="margin-top:5px;" helper="此指标灵活查询功能可使用"/>
	       
	        <@c.FormText class="ckeditor"colspan=2 name="techbore" label="技术口径描述" >${indexinfo?if_exists.techbore?if_exists}</@c.FormText>
	        <@c.FormText class="ckeditor" colspan=2 name="remark" label="备注" >${indexinfo?if_exists.remark?if_exists}</@c.FormText>
	        -->
	        <@c.FormText class="ckeditor" colspan=2 name="businessbore" label="业务口径描述" >${indexinfo?if_exists.businessbore?if_exists}</@c.FormText>
		</@c.Form>
   	</@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" click="saveform()" >保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>

<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader style="background:#f8f9fa;border-bottom: 1px solid #ddd;">
        <h3 class="box-title"><#if param??&& param.id??>修改参数<#else>添加参数</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody >
	    <@c.Form id="paramForm" class="validate" action="${ContextPath}/param/param/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#param_libContent"}>
	        <@c.FormIdVersion id="${param?if_exists.id?if_exists}" version="${param?if_exists.version?default(0)}"/>
	        <@c.FormInput name="displayName" label="参数显示名称" value="${param?if_exists.displayName?if_exists}" helper="1-64位字符"validation={"required":true,"minlength":1,"maxlength":64}/>
	        <@c.FormInput name="name" label="参数名称" value="${param?if_exists.name?if_exists}" helper="1-64位字符,需与报表接收参数一致。" validation={"required":true,"minlength":1,"maxlength":64}/>
	          
	        <@c.FormComboBox name="type" label="参数类型"  validation={"required":true} value="${param?if_exists.type?if_exists}">
		      	 <#list data_type as doc>
		       		<@c.Option value="${doc.doc_name?if_exists}">${doc.doc_name?if_exists}</@c.Option>
		        </#list>
	        </@c.FormComboBox>
	        <!-- 参数展现类型(参数展现类型(text:文本输入，select 下拉单选，mul-select下拉多选，  ref:单选参照，mul-ref:多选参照，date:日期，date-time:时间)) -->
	       <@c.FormComboBox name="viewType" label="显示类型"  validation={"required":true} value="${param?if_exists.viewType?if_exists}">
		      	 <#list comp_type as doc>
		       		<@c.Option value="${doc.doc_code?if_exists}">${doc.doc_name?if_exists}</@c.Option>
		        </#list>
	        </@c.FormComboBox>
	        <@c.FormComboBox name="dataFromType" label="数据来源类型"  validation={"required":true} value="${param?if_exists.dataFromType?if_exists}" events="{change:function(){onDataSourceTypeSelectChange();}}">
		      	 <#list data_source_type as doc>
		       		<@c.Option value="${doc.doc_code?if_exists}">${doc.doc_name?if_exists}</@c.Option>
		        </#list>
	        </@c.FormComboBox>
			<@c.FormComboBox itemId="ds_doclist_div"  itemStyle="display:none"  id="ds_doclist"  name="dataFrom" label="数据字典"   value="${param?if_exists.dataFrom?if_exists}">
		      	 <#list doclists as doc>
		       		<@c.Option value="${doc.dic_code?if_exists}">${doc.dic_name?if_exists}</@c.Option>
		        </#list>
	        </@c.FormComboBox>
		    <@c.FormInput itemId="default_value_div" name="defaultValue" label="默认值" value="${param?if_exists.defaultValue?if_exists}"
		    	validation={"required":true,"minlength":1,"maxlength":100} helper="1-100位字符,仅支持汉字、字母、数字组合"
		    />
	        <@c.FormInput name="cubeTextFormat" label="cube参数格式"  helper="例如:[时间参数][日期参数][@value@],在生成条件时，格式化参数传递给Cognos报表。" 
	        	value="${param?if_exists.cubeTextFormat?if_exists}"/>
      	 	<@c.FormComboBox itemId="colspan" name="colspan" label="所占列" value="${param?if_exists.colspan?if_exists}">
					<@c.Option value="1">1</@c.Option>
					<@c.Option value="2">2</@c.Option>
					<@c.Option value="3">3</@c.Option>
					<@c.Option value="4">4</@c.Option>
			</@c.FormComboBox>   
			<!--是否必输-->
			 <@c.FormCheckBox name="mustput" label="必输标志" value=param?if_exists.mustput?default("N") checkValue="Y" style="margin-top:5px;" helper="参数是否必须输入标志"/>
	        <@c.Hidden name="pid" value="${pid?if_exists}" />
	  	</@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button icon="fa fa-save" type="primary"  action=[c.saveform("#paramForm")]>保存</@c.Button>
		 <@c.Button type="canel" icon="glyphicon glyphicon-off"   action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
<@c.Script src="script/integration/param" onload="onDataSourceTypeSelectChange()" />

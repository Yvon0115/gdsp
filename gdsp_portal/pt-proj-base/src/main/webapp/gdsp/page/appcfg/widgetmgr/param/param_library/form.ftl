<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if param??&& param.id??>修改参数<#else>添加参数</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody >
	    <@c.Form id="paramForm" class="validate" action="${ContextPath}/portal/params_libsub/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#param_libContent"}>
	        <@c.FormIdVersion id="${param?if_exists.id?if_exists}" version="${param?if_exists.version?default(0)}"/>
	          <@c.FormInput name="name" label="参数名称" value="${param?if_exists.name?if_exists}"/>
	         <@c.FormInput name="code" label="参数Key" value="${param?if_exists.code?if_exists}" helper="需与报表接收参数一致。" validation={"required":true}/>
	          <!-- 手工，SQL， 文件 -->
	        <@c.FormItem id="type" label="参数类型"   >
	        	 <div class="inputbox-group">
	        	 <#if data_type??>
	        	 
						<#list data_type as doc>
							<label>
								<input type="radio" name="type" id="type" <#if param?if_exists.type?if_exists ?? && param?if_exists.type?if_exists == doc.doc_code?if_exists>checked</#if>  value="${doc.doc_code?if_exists}"  > ${doc.doc_name?if_exists}
							</label>
						</#list>
						</#if>
	                </div>
	        </@c.FormItem>
	        
	        <!-- 参数展现类型(参数展现类型(text:文本输入，select 下拉单选，mul-select下拉多选，  ref:单选参照，mul-ref:多选参照，date:日期，date-time:时间)) -->
	       <@c.FormComboBox name="view_type" label="显示类型"  validation={"required":true} value="${param?if_exists.view_type?if_exists}">
		      	 <#list comp_type as doc>
		       		<@c.Option value="${doc.doc_code?if_exists}">${doc.doc_name?if_exists}</@c.Option>
		        </#list>
	        </@c.FormComboBox>
	        
	        <@c.FormItem id="data_source_type" label="数据源类型"   >
		        	 <div class="inputbox-group">
						<#list data_source_type as doc>
							<label>
								<input type="radio" name="data_source_type" onChange="onDataSourceTypeSelectChange()" id="data_source_type" <#if param?if_exists.data_source_type?if_exists ?? && param?if_exists.data_source_type?if_exists == doc.doc_code?if_exists>checked</#if>  value="${doc.doc_code?if_exists}"  > ${doc.doc_name?if_exists}
							</label>
						</#list>
	                </div>
	        </@c.FormItem>
	        
	   		<!-- 数据源(手工:json , SQL: sql  文件: 文件全路径) -->
	       <@c.FormText itemId="data_source_div" id="data_source" name="data_source" label="数据源内容"  style="display:block" rows=3 >${param?if_exists.data_source?if_exists}</@c.FormText>
	
			<@c.FormComboBox itemId="ds_doclist_div"  itemStyle="display:none"  id="ds_doclist"  name="ds_doclist" label="数据字典"   value="${param?if_exists.ds_doclist?if_exists}">
		      	 <#list doclists as doc>
		       		<@c.Option value="${doc.type_code?if_exists}">${doc.type_name?if_exists}</@c.Option>
		        </#list>
	        </@c.FormComboBox>
	        <!--数据权限控制-->
	        <@c.FormComboBox itemId="ds_data_power_div" itemStyle="display:none"   id="ds_data_power"  name="ds_data_power" label="数据权限"   value="${param?if_exists.ds_data_power?if_exists}">
		      	 <#list data_powers as doc>
		       		<@c.Option value="${doc.doc_code?if_exists}">${doc.doc_name?if_exists}</@c.Option>
		        </#list>
	        </@c.FormComboBox>
	        <@c.FormComboBox itemId="ds_sys_datefun_div"  itemStyle="display:none"   id="ds_sys_datefun"  name="ds_sys_datefun" label="系统函数"  value="${param?if_exists.ds_sys_datefun?if_exists}">
		      	 <#list sys_datefun as doc>
		       		<@c.Option value="${doc.doc_code?if_exists}">${doc.doc_name?if_exists}</@c.Option>
		        </#list>
	        </@c.FormComboBox>
	        <@c.FormComboBox  itemId="default_value_div"  name="default_value" label="默认值"  value="${param?if_exists.default_value?if_exists}">
		      	 <#list default_value as doc>
		       		<@c.Option value="${doc.doc_code?if_exists}">${doc.doc_name?if_exists}</@c.Option>
		        </#list>
	        </@c.FormComboBox>
	        <@c.FormInput name="cube_text_format" label="cube参数格式"  helper="例如:[时间参数][日期参数][@value@],在生成条件时，格式化参数传递给Cognos报表。" 
	        	value="${param?if_exists.cube_text_format?if_exists}"/>
	        	
      	 	<@c.FormComboBox itemId="colspan" name="colspan" label="所占列" value="${param?if_exists.colspan?if_exists}">
					<@c.Option value="1">1</@c.Option>
					<@c.Option value="2">2</@c.Option>
					<@c.Option value="3">3</@c.Option>
					<@c.Option value="4">4</@c.Option>
			</@c.FormComboBox>   
			<!--是否必输-->
			 <@c.FormCheckBox name="mustinput" label="必输标志" value=param?if_exists.mustinput?default("N") checkValue="Y" style="margin-top:5px;" helper="参数是否必须输入标志"/>
	        <@c.Hidden name="pid" value="${pid?if_exists}" />
	  	</@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button icon="fa fa-save" type="primary"  action=[c.saveform("#paramForm")]>保存</@c.Button>
		 <@c.Button type="canel" icon="glyphicon glyphicon-off"   action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
<@c.Script src="script/appcfg/resParam/resParam" onload="onDataSourceTypeSelectChange()"/>

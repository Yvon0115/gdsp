<#import "/gdsp/tags/castle.ftl" as c>
	<@c.Box>
		<@c.BoxHeader style="background:#f8f9fa;border-bottom: 1px solid #ddd;">
			<h3 class="box-title">修改</h3>
		</@c.BoxHeader>
		<@c.BoxBody   style="width:100%;min-height:450px;">
			<@c.Form id="widgetForm" class="validate" action="${ContextPath}/framework/reportentry/save.d" method="post" after={ "switchtab": "#mainPanel","dataloader":"#widgetName" }>
				<@c.FormIdVersion id="${widget?if_exists.id?if_exists}" version="${widget?if_exists.version?default(0)}" />
				<@c.FormInput name="report_name"  label="报表名称" value="${widget?if_exists.report_name?if_exists}" readonly=true/>
				<@c.FormInput name="report_path" label="报表路径"  value="${widget?if_exists.report_path?if_exists}" readonly=true/>
				<@c.FormItem editorId="report_type" label="报表类型" >
	                <div class="inputbox-group">
						<#list report_type as doc>
							<label>
								<input type="radio"   <#if widget?if_exists.report_type?if_exists==doc.doc_code?if_exists>checked</#if> value="${doc.doc_code?if_exists}" id="report_type" name="report_type"> ${doc.doc_name?if_exists}[ ${doc.doc_desc?if_exists}]
							</label>
						</#list>
	                </div>
				</@c.FormItem>
				<@c.FormItem editorId="param_type" label="报表参数类型" >
                <div class="inputbox-group">
					<#list param_type as doc>
							<label>
								<input type="radio"   <#if widget?if_exists.param_type?if_exists==doc.doc_code?if_exists>checked</#if> value="${doc.doc_code?if_exists}" id="param_type" name="param_type"> ${doc.doc_name?if_exists}[ ${doc.doc_desc?if_exists}]
							</label>
					</#list>
                </div>
				</@c.FormItem>
			<@c.FormInput  name="param_template_path" label="模板路径" value="${widget?if_exists.param_template_path?if_exists}" />
			<!--cube报表标志-->
			 <@c.FormCheckBox name="cube_flag" label="Cube报表标志" value=widget?if_exists.cube_flag?default("N") checkValue="Y" style="margin-top:5px;" helper="Cube报表标志"/>
 				
			 
			</@c.Form>
		</@c.BoxBody>
		<@c.BoxFooter class="text-center">
			<@c.Button type="primary"  icon="fa fa-save"    action=[c.saveform( "#widgetForm")]>保存</@c.Button>
			<@c.Button type="canel" icon="glyphicon glyphicon-off"   action=[c.opentab( "#mainPanel")]>取消</@c.Button>
		</@c.BoxFooter>
	</@c.Box>
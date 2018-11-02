<#import "/gdsp/tags/castle.ftl" as c>
	<@c.Box>
		<@c.BoxHeader style="background:#f8f9fa;border-bottom: 1px solid #ddd;">
			<h3 class="box-title">修改</h3>
		</@c.BoxHeader>
		<@c.BoxBody   style="width:100%;min-height:430px;">
			<@c.Form id="widgetForm" class="validate" action="${ContextPath}/framework/reportentry/save.d" method="post" after={ "switchtab": "#mainPanel","dataloader":"#widgetName" }>
				<@c.FormIdVersion id="${widget?if_exists.id?if_exists}" version="${widget?if_exists.version?default(0)}" />
				<@c.FormInput name="report_name"  label="报表名称" value="${widget?if_exists.report_name?if_exists}" readonly=true/>
				<@c.FormInput name="report_path" label="报表路径"  value="${widget?if_exists.report_path?if_exists}" readonly=true/>
				<@c.FormRef  id="queryTemplate" name="param_template_path"   label="查询模板" value="${widget?if_exists.param_template_path?if_exists}" showValue="${widget?if_exists.param_template_path?if_exists}"  url="${ContextPath}/framework/reportentry/loadQueryTemplate.d"  />
				
				<@c.Hidden name="report_type" value="${widget?if_exists.report_type}" />
							
			</@c.Form>
		</@c.BoxBody>
		<@c.BoxFooter class="text-center">
			<@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform( "#widgetForm")]>保存</@c.Button>
			<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab( "#mainPanel")]>取消</@c.Button>
		</@c.BoxFooter>
	</@c.Box>
	

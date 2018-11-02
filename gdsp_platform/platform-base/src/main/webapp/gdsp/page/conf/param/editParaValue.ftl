<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>修改参数值</modal-title>
<div class="modal-body autoscroll">
	<@c.Form id="passwordForm" class="validate" action="${ContextPath}/conf/param/setParamValue.d" method="post" after={"pageload":{}}>
	   <@c.FormIdVersion id="${param?if_exists.id?if_exists}" version="${param?if_exists.version?default(0)}"/>
	   <@c.FormInput name="parcode" label="参数编码" value="${param?if_exists.parcode?if_exists}" readonly=true />
	   <@c.FormInput name="parname" label="参数名称" value="${param?if_exists.parname?if_exists}" readonly=true/>	   
		<#if param.valuetype??&&param.valuetype==2>
			<@c.FormComboBox  name="parvalue" label="参数值"  value="${param?if_exists.parvalue?if_exists}">
			    <@c.Option value="true">是</@c.Option>
			    <@c.Option value="false">否</@c.Option>
			</@c.FormComboBox>
		<#else>
			<@c.FormInput name="parvalue" label="参数值" value="${param?if_exists.parvalue?if_exists}"/>	
		</#if>
	   <@c.FormText name="memo" label="说明"  readonly=true>${param?if_exists.memo?if_exists}</@c.FormText>
  	</@c.Form>
</div>
<div class="modal-footer">
	<@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#passwordForm")]>保存</@c.Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.dismiss()]>取消</@c.Button>
</div>

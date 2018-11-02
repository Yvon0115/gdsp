<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	<@c.BoxHeader class="border header-bg">
      <h3 class="box-title"><#if editType??&&editType=="add">添加维值<#else>修改维值</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
		 <@c.Form  id="dimValueForm" class="validate" action="${ContextPath}/indexanddim/dim/saveDimValue.d?dimId=${dimId}" method="post" after={"switchtab":"#detailPanel","dataloader":"#dimValueListContent"}>
		 <@c.FormInput name="dimvalue" label="维值名称" value="${dimValueVO?if_exists.dimvalue?if_exists}" />
		 <@c.Hidden name="id" value="${dimValueVO?if_exists.id?if_exists}"/>
    	<@c.FormInput name="dimvaluememo" label="维度字段名称" value="${dimValueVO?if_exists.dimvaluememo?if_exists}"  />
		 </@c.Form>
    </@c.BoxBody>
     <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#dimValueForm")] >保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#detailPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
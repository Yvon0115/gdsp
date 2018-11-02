<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader style="background:#f8f9fa;border-bottom: 1px solid #ddd;">
        <h3 class="box-title"><#if historyChangeVO??&& historyChangeVO.id??>修改<#else>添加</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="historyForm" class="validate" action="${ContextPath}/framework/history/save.d" method="post" after={"switchtab":"#mainCHPanel","dataloader":"#histoyChange"}>
        <@c.FormIdVersion id="${historyChangeVO?if_exists.id?if_exists}" version="${historyChangeVO?if_exists.version?default(0)}"/>
        <@c.FormInput name="title" label="标题" value="${historyChangeVO?if_exists.title?if_exists}" validation={"required":true,"minlength":1,"maxlength":128}/>
        <#if historyChangeVO??&& historyChangeVO.id??>
        	<@c.FormComboBox  name="opType" label="操作类型" disabled=true value="${historyChangeVO?if_exists.opType?if_exists}">
            	<@c.Option value="变更">变更</@c.Option>
            	<@c.Option value="创建">创建</@c.Option>
        	</@c.FormComboBox>
        	<@c.FormDate name="changeTime" id="changeTime"  label="变更日期" readonly=true disabled=true format="" value="${historyChangeVO?if_exists.changeTime?if_exists}" validation={"required":true}/>
        	<@c.FormInput name="changeName" id="changeName" label="变更申请人" readonly=true value="${historyChangeVO?if_exists.changeName?if_exists}" validation={"required":true,"minlength":1,"maxlength":256}/>
        <#else>
        	<#if opType??&&opType=='1'>
        		<@c.FormComboBox  name="opType" label="操作类型" disabled=true value="${historyChangeVO?if_exists.opType?if_exists}">
            		<@c.Option value="变更">变更</@c.Option>
            		<@c.Option value="创建">创建</@c.Option>
        		</@c.FormComboBox>
        		<#else>
        		<@c.FormComboBox  name="opType" label="操作类型"  value="${historyChangeVO?if_exists.opType?if_exists}">
            		<@c.Option value="变更">变更</@c.Option>
            		<@c.Option value="创建">创建</@c.Option>
        		</@c.FormComboBox>
        	</#if>
        	<@c.FormDate name="changeTime" id="changeTime"  label="变更日期" format="" value="${historyChangeVO?if_exists.changeTime?if_exists}" validation={"required":true}/>
        	<@c.FormInput name="changeName" id="changeName" label="变更申请人" value="${historyChangeVO?if_exists.changeName?if_exists}" validation={"required":true,"minlength":1,"maxlength":256}/>
        </#if>
          <@c.FormText name="comments" label="描述" validation={"minlength":1,"maxlength":260} rows=7 >${historyChangeVO?if_exists.comments?if_exists}</@c.FormText>
    	<@c.Hidden name="link_id" value="${link_id?if_exists}" />
    	<@c.Hidden name="report_name" value="${report_name?if_exists}" />
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#historyForm")]> 保存</@c.Button>
        <@c.Button type="canel"  icon="glyphicon glyphicon-off" action=[c.opentab("#mainCHPanel")]> 取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>

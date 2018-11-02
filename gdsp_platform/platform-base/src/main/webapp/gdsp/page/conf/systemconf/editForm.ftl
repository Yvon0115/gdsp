<#--系统配置编辑页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="sysConfJS" src="script/func/systemConf" onload="InitPage()" /> 
<@c.Box>
 	<@c.BoxHeader class="border header-bg" >
        <h3 class="box-title">编辑系统设置</h3>
    </@c.BoxHeader>
	<@c.BoxBody>
		<@c.Form id="systemConfForm" class="validate" action="${ContextPath}/func/systemconf/saveSystemConf.d" method="post" after={"switchtab":"#systemConfMainPanel","pageload":{}}>
			<@c.FormIdVersion id="${systemConf?if_exists.id?if_exists}" version="${systemConf?if_exists.version?default(0)}" />
        	<@c.FormItem id="sysHomeRadio" label="系统首页:">
        		<@c.Hidden id="sysHomeStateHidden" name="sysHomeState" value="${systemConf?if_exists.sysHomeState?if_exists}"/>
        		<div style="margin-top:5px;">
					<input type="radio" id="enable"  name="isEnable" value="Y" onclick="setSysHomeState(this.value);"<#if systemConf?if_exists.sysHomeState?if_exists=='Y'>checked</#if> />&nbsp;启用&nbsp;&nbsp;&nbsp;
					<input type="radio" id="disable"  name="isEnable" value="N" onclick="setSysHomeState(this.value);"<#if systemConf?if_exists.sysHomeState?if_exists!='Y'>checked</#if> />&nbsp;不启用&nbsp;
				</div>
			</@c.FormItem>
			<@c.FormInput id="sysHomeUrlEdit" name="sysHomeUrl" label="URL地址:"  value="${systemConf?if_exists.sysHomeUrl?if_exists}" helper="请输入URL地址" validation={"required":true}/>
			<@c.FormItem id="reportIntsCheckbox" label="报表集成:">
				<@c.Hidden id="reportIntsHidden" name="reportInts" value="${systemConf?if_exists.reportInts?if_exists}"/>
        		<div id="reportIntsCheckboxs" style="margin-top:5px;">
        			<#list reportTypeList as reportType>
        				<input type="checkbox"  id="reportIntsCheckbox"  name="reportIntsCheckbox" value="${reportType?if_exists.doc_code?if_exists}"  onclick="setReportInts(this.value,checked);"<#if reportType?if_exists.checked?if_exists=='checked'>checked</#if> />&nbsp;${reportType?if_exists.doc_name?if_exists}&nbsp;&nbsp;&nbsp;
        			</#list>
				</div>
			</@c.FormItem>
		</@c.Form>
	</@c.BoxBody>
	<@c.BoxFooter class="text-center">
		<@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#systemConfForm")]>保存</@c.Button>
		<@c.Button type="cancel" icon="glyphicon glyphicon-off" action=[c.opentab("#systemConfMainPanel")]>取消</@c.Button>
	</@c.BoxFooter>
</@c.Box>

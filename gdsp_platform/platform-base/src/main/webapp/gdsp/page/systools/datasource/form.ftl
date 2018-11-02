<#-- 数据源新增/修改表单 -->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/datasource/datasource" id="datasource" onload="validateTypeEditForm('#detailPanel')"/>
<#-- 产品类别 -->
<@c.Hidden  name="prodType" value=""/>
<#-- 
<#assign a= comboTypes!"123">
<#list comboTypes as a>${a}</#list>
-->
<#-- 数据源类型 -->
<@c.Hidden  name="dateSourceType" value="${comboTypes[1]}"/>
<#list comboTypes as combo>
	<#if combo_index == 1>
		<#assign dsType=combo><#t>
	</#if>
</#list>
<@c.Box>
    <@c.BoxHeader class="border header-bg" >
        <h3 class="box-title"><#if dateSourceVO??&& dateSourceVO.id??>修改<#else>添加</#if></h3>
        <#if comboTypes[1] == "DBType" || comboTypes[1] == "BigDataDBType">
        	<@c.Button type="primary" class="pull-right" icon="glyphicon glyphicon-link" click="testConn();">测试连接</@c.Button>
        </#if>
    </@c.BoxHeader>
    <@c.BoxBody>
    	<#-- 表单内容 -->
        <@c.Form id="datasourceForm" class="validate" action="${ContextPath}/systools/ds/save.d" cols=2 method="post"  
        		after={"switchtab":"#mainPanel","dataloader":"#datasourceContent"}>
	    	<@c.FormIdVersion id="${dateSourceVO?if_exists.id?if_exists}" version="${dateSourceVO?if_exists.version?default(0)}"/>
	    	<#if dateSourceVO??&& dateSourceVO.id??><#-- 修改 -->
	    		<@c.FormInput  id="name" name="name" label="数据源名称" value="${dateSourceVO?if_exists.name?if_exists}" 
	    				validation={"required":true,"minlength":1,"maxlength":25} helper="数据源名称长度为1-25" />
				<@c.FormComboBoxDict id="prod_type" name="type" label="数据源类型" type=comboTypes!["DatasourceType","0"] value="${dateSourceVO?if_exists.type?if_exists}"
						placeholder="数据源类型" disabled=true validation={"required":true} helper="数据源产品种类" events="{change:function(){typeChange('#detailPanel',this)}}"/>
				<@c.FormComboBoxDict id="classify" name="classify" label="数据源分类" type=["DatasourceClassify"] value="${dateSourceVO?if_exists.classify?if_exists}"
				 		placeholder="数据源分类" validation={"required":true} helper="数据源使用范围" />
				<#if dsType?? && (dsType == "DBType" || dsType == "BigDataDBType")>
					<@c.FormComboBox name="product_version" label="产品版本号" placeholder="产品版本号" value="${dateSourceVO?if_exists.product_version?if_exists}" 
	        				events="{change: function(){proVersionChange()}}" helper="数据源产品版本号">
						<#if products??>
							<#list products as pro>
								<#if pro_index == 0>
									<@c.Option selected =true value="${pro.ds_version?if_exists}">${pro.ds_version?if_exists}</@c.Option>
								<#else>
									<@c.Option value="${pro.ds_version?if_exists}">${pro.ds_version?if_exists}</@c.Option>
								</#if>
							</#list>
						</#if>
					</@c.FormComboBox>
					<@c.FormInput name="driver" label="JDBC驱动" value="${dateSourceVO?if_exists.driver?if_exists}" placeholder="JDBC驱动" readonly=true  helper="数据源的驱动名称"/>
				</#if>
	        <#else><#-- 新增 -->
	        	<@c.FormInput  id="name" name="name" label="数据源名称" value="${dateSourceVO?if_exists.name?if_exists}" helper="数据源名称长度为1-25"
	    				validation={"required":true,"minlength":1,"maxlength":25,"remote":"${ContextPath}/systools/ds/synchroCheck.d"} messages={"remote":"数据源名称不能重复，请确认！"} />
	        	<@c.FormComboBoxDict id="prod_type" name="type" label="数据源类型"  type=comboTypes!["DatasourceType","0"] value="${dateSourceVO?if_exists.type!datasourceType!''}" 
	        			placeholder="数据源类型" validation={"required":true} helper="数据源产品种类" events="{change: function(){typeChange('#detailPanel',this)}}"/>
	        	<@c.FormComboBoxDict id="classify" name="classify" label="数据源分类" type=["DatasourceClassify"] value="${dateSourceVO?if_exists.classify!'common'}"
				 		placeholder="数据源分类" validation={"required":true}  helper="数据源使用范围" />
				<#if dsType?? && (dsType == "DBType" || dsType == "BigDataDBType")>
					<@c.FormComboBox name="product_version" label="产品版本号" placeholder="产品版本号" value="${dateSourceVO?if_exists.product_version?if_exists}" helper="数据源产品版本号"
	        				events="{change: function(){proVersionChange()}}">
						<#if products??>
							<#list products as pro>
								<#if pro_index == 0>
									<#assign dirver>${pro.qualifiedClassName?if_exists}</#assign>
									<@c.Option selected =true value="${pro.ds_version?if_exists}">${pro.ds_version?if_exists}</@c.Option>
								<#else>
									<@c.Option value="${pro.ds_version?if_exists}">${pro.ds_version?if_exists}</@c.Option>
								</#if>
							</#list>
						</#if>
					</@c.FormComboBox>
					<@c.FormInput name="driver" label="JDBC驱动" placeholder="JDBC驱动" readonly=true value="${dirver?if_exists}" helper="数据源的驱动名称"/>
				</#if>
	        </#if>
		<@c.FormItem colspan=2>
			<hr />
		</@c.FormItem>
			<#--  ?id=${dateSourceVO?if_exists.id?if_exists}&type=${dateSourceVO?if_exists.type?if_exists} -->
	        <#-- <#if dateSourceVO??&& dateSourceVO.id??>
	        	<@c.FormInput id="code" name="code" label="数据源编码" value="${dateSourceVO?if_exists.code?if_exists}"   
	        			validation={"required":true,"minlength":1,"maxlength":25,"remote":"${ContextPath}/systools/ds/synchroCheck.d"} 
	        			messages={"remote":"编码不能重复，请确认！"} helper="数据源编码长度为1-25"/>
	        <#else>
	        	<@c.FormInput id="code" name="code" label="数据源编码" value="${dateSourceVO?if_exists.code?if_exists}"  
	        			validation={"required":true,"minlength":1,"maxlength":25,"remote":"${ContextPath}/systools/ds/synchroCheck.d"} 
	        			messages={"remote":"编码不能重复，请确认！"} helper="数据源编码长度为1-25"/>
	        </#if>
	        -->
	        <#--> <@c.FormCheckBox name="isDefault" label="设置为默认数据源" value="${dateSourceVO?if_exists.isDefault?if_exists}" 
	        		checkValue="${dateSourceVO?if_exists.isDefault?if_exists}" style="margin-top:5px;" /></#-->
		        
	        <@c.FormInput id="username" name="username" label="用户名"   value="${dateSourceVO?if_exists.username?if_exists}" validation={"minlength":1,"maxlength":20} class="userInfo" helper="1-20个字符,访问数据源时提供的用户名"/>
	        <@c.FormInput id="password" name="password" label="密码"     type="password" value="${dateSourceVO?if_exists.password?if_exists}" validation={"remote":"${ContextPath}/systools/ds/passwordCheck.d?id=${dateSourceVO?if_exists.id?if_exists}"} messages={"remote":"请输入0-32位长度的字符"}  helper="0-32个字符,访问数据源时提供的密码"/>
	        <@c.FormInput id="ip"       name="ip"       label="IP地址"   value="${dateSourceVO?if_exists.ip?if_exists}"       events="{blur:blurEvents}" validation={"required":true,"minlength":1,"maxlength":15}   helper="1-15个字符,获取数据源信息的地址"/>
	        <@c.FormInput id="port"     name="port"     label="端口号"   value="${dateSourceVO?if_exists.port?if_exists}"     events="{blur:blurEvents}" validation={"required":true,"minlength":1,"maxlength":5}  helper="1-5个字符,获取数据源信息的端口"/>
	        <@c.FormComboBox name="authentication_model" label="认证模式" value="${dateSourceVO?if_exists.authentication_model?if_exists}" helper="大数据平台安全认证模式" 
	        events="{change:function(){modelChange('#detailPanel')}}" validation={"required":true}>
	        	<#list modelList as model>
	        		<#if "${model_index}" == "0">
	        			<@c.Option value="${model_index}" selected=true>${model}</@c.Option>
	        		<#else>
	        			<@c.Option value="${model_index}">${model}</@c.Option>
	        		</#if>
	        	</#list>
			</@c.FormComboBox>
	        <@c.FormInput id="url"      	name="url"      	label="URL"         value="${dateSourceVO?if_exists.url?if_exists}"      		validation={"required":true} helper="访问数据源的URL"/>
	        <@c.FormInput id="keytab_path"  name="keytabPath"   label="秘钥路径"       value="${dateSourceVO?if_exists.keytabPath?if_exists}"      validation={"required":true} helper="用于访问大数据平台的用户的秘钥文件绝对路径"/>
	        <@c.FormInput id="krb_path"     name="krbConfPath"  label="krb路径"      value="${dateSourceVO?if_exists.krbConfPath?if_exists}"      validation={"required":true} helper="大数据平台的kerberos的配置文件绝对路径"/>
	        <@c.FormInput id="path"     	name="path"     	label="同步路径" 		value="${dateSourceVO?if_exists.path?if_exists}"      		validation={"required":true} helper="报表的同步路径(smartBI为同步的路径的ID)"/>
	        <@c.FormInput id="permissionurl"name="permissionurl"label="默认权限标志"  	value="${dateSourceVO?if_exists.permissionurl?if_exists}"  	helper="填写/*[permission('read')]即可，表示只读权限，不需要可以为空"/>
	        <@c.FormInput id="span"     	name="span"     	label="第三方认证登录域名"value="${dateSourceVO?if_exists.span?if_exists}"  			validation={"required":true} helper="报表第三方认证域名"/>
	        <@c.FormInput id="px_url"   	name="px_url"   	label="页面前缀URL" 	value="${dateSourceVO?if_exists.px_url?if_exists}" 			validation={"required":true} helper="同步网络资源的URL"/>
	        <@c.FormText  id="comments" 	name="comments" 	label="描述"  	validation={"minlength":1,"maxlength":128} rows=3 >${dateSourceVO?if_exists.comments?if_exists}</@c.FormText>   			
        </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" click="savedForm();" >保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>

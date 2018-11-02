<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if user??&& user.id??>修改用户<#else>添加用户</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="userForm" class="validate" cols=2 action="${ContextPath}/grant/user/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#usersContent"}>
        <@c.FormIdVersion id="${user?if_exists.id?if_exists}" version="${user?if_exists.version?default(0)}"/>
        <#if editType??&&editType=="edit">
        <@c.FormInput name="account" label="账号" value="${user?if_exists.account?if_exists}" readonly=true />
         <@c.Hidden name="origin" value="${user?if_exists.origin?if_exists}"/>
        <#else>
         <@c.Hidden name="origin" value=0/>
        <@c.FormInput name="account" label="账号" value="${user?if_exists.account?if_exists}" helper="1-20位字符" validation={"required":true,"minlength":1,"maxlength":20,"remote":"${ContextPath}/grant/user/synchroCheck.d"} messages={"remote":"账号不能重复，请确认！"}/>        
        </#if>
        <@c.FormInput name="username" label="姓名" value="${user?if_exists.username?if_exists}" helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events="{blur :function(){$Utils.validInputSpeChar(this)}}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":60}/>
        <#if user??&& user.id??>
        <#else>
        <@c.FormInput name="user_password" type="password" label="密码" value="${user?if_exists.user_password?if_exists}" validation={"required":true,"remote":"${ContextPath}/grant/user/pwdBlankCheck.d"}/>  
        <@c.FormInput name="re_password" type="password" label="确认密码" value="${user?if_exists.user_password?if_exists}" validation={"required":true,"equalTo":"#user_password"}/>  
        </#if>
        <@c.FormComboBox  name="sex" label="性别"  value="${user?if_exists.sex?if_exists}">
            <#if user?if_exists.sex??>
            <@c.Option value="0">男</@c.Option>
            <@c.Option value="1">女</@c.Option>
            <#else>
             <@c.Option value="0" selected=true>男</@c.Option>
            <@c.Option value="1">女</@c.Option>
            </#if>
        </@c.FormComboBox>
        <@c.FormInput name="mobile" label="手机" value="${user?if_exists.mobile?if_exists}" helper="11位数字" maxlength="11" validation={"mobile":true} />
        <@c.FormInput name="tel" label="固定电话" value="${user?if_exists.tel?if_exists}" helper="7-20位字符，仅支持数字，可用‘+、-、()’分隔" validation={"phone":true}/>
        <@c.FormInput name="email" label="邮箱" value="${user?if_exists.email?if_exists}" validation={"email":true}/>    
        <#if editType??&&editType=="edit">
        <@c.Hidden name="pk_org" value="${user?if_exists.pk_org?if_exists}"/>
        <@c.Hidden name="islocked" value="${user?if_exists.islocked?if_exists}" />
        <@c.Hidden name="isdisabled" value="${user?if_exists.isdisabled?if_exists}" />
        <@c.Hidden name="isreset" value="${user?if_exists.isreset?if_exists}"/>
        <@c.FormInput name="orgname" value="${user?if_exists.orgname?if_exists}" label="机构名称" readonly=true/>
        <#else>
        <@c.Hidden name="pk_org" value=pk_org/>       
        <@c.FormInput name="orgname" value="${orgname?if_exists}" label="机构名称" readonly=true/>
        </#if>
        <#if isLimit??&&isLimit=="Y">
        	<@c.FormCheckBox name="onlyadmin" label="只有管理权限" value=user?if_exists.onlyadmin?default("N") checkValue="Y" style="vertical-align:middle;"  helper="管理用户是否可以使用业务菜单功能"/>
        </#if>
        <!-- <@c.FormCheckBox name="islocked" label="锁定" value=user?if_exists.islocked?default("N") checkValue="Y" style="margin-top:5px;" disabled=true/>
        <@c.FormCheckBox name="isdisabled" label="停用" value=user?if_exists.isdisabled?default("N") checkValue="Y" style="margin-top:5px;"  disabled=true/>-->
        <@c.FormText name="memo"  validation={"minlength":1,"maxlength":50} label="描述" itemStyle="margin-top:15px;">${user?if_exists.memo?if_exists}</@c.FormText>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#userForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>

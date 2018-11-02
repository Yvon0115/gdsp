<#import "/gdsp/tags/castle.ftl" as c>
<#assign login_status>!<#noparse>
<#if row.login_status??&& row.login_status=="Y">登录成功<#else>登录失败</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["\t","账号","用户名","登录时间","ip地址","登录状态"] keys=["\t","login_account","username","login_time","ip_addr",login_status] ellipsis={"login_account":"50px","username":"84px","ip_addr":"100px"} data=LoginLogPages?if_exists.content![] />
<@c.PageData page=LoginLogPages!"" />